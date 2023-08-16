package com.turion.service;

import com.turion.dto.SatDTO;
import com.turion.resources.entity.Image;
import com.turion.resources.entity.Request;
import com.turion.resources.repo.ImageRepo;
import com.turion.resources.repo.RequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TalkingToSatellite {

    private static final Logger log = LoggerFactory.getLogger(TalkingToSatellite.class);

    private static final String SAT_URL = "http://satellite:8082/sat";
    private static final String SAT_URL_IMAGE_CAPTURE = SAT_URL + "/imageCapture";
    private static final String SAT_URL_DOWNLOAD_IMAGES = SAT_URL + "/downloadImages";

    private final ImageRepo imageRepo;
    private final RequestRepo requestRepo;

    private boolean isSatelliteAvailable = true;
    private int counter = 0;

    public TalkingToSatellite(ImageRepo imageRepo, RequestRepo requestRepo) {
        this.imageRepo = imageRepo;
        this.requestRepo = requestRepo;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("trying to talk satellite");

        if (isSatelliteAvailable) {
            // this will check db if there is any pending imaging request
            // and trigger satellite to capture image if there is any
            processImagingRequest();

            // this will trigger satellite to pull all images stored on local
            // and this will be executed once in every 3 times that satellite came online
            counter++;
            if (counter % 3 == 0) {
                counter = 0;
                pullImagesFromSatellite();
            }
        }

        isSatelliteAvailable = !isSatelliteAvailable;
    }

    private void processImagingRequest() {
        requestRepo.findAll().stream()
                .filter(Request::isActive)
                .findFirst()
                .ifPresent(request -> {
                    SatDTO satDTO = new SatDTO();
                    satDTO.setRequestId(request.getId());

                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                    HttpEntity<SatDTO> entity = new HttpEntity<>(satDTO, headers);

                    Boolean result = false;
                    try {
                        result = restTemplate.exchange(
                                        getUri(SAT_URL_IMAGE_CAPTURE),
                                        HttpMethod.POST,
                                        entity,
                                        Boolean.class)
                                .getBody();
                    } catch (Exception e) {
                        log.error("can't get response from satellite for processImagingRequest : " + e);
                    }

                    if (Boolean.TRUE.equals(result)) {
                        request.setActive(false);
                        requestRepo.save(request);
                    }
                });
    }

    private void pullImagesFromSatellite() {
        RestTemplate restTemplate = new RestTemplate();
        List<SatDTO> result = new ArrayList<>();
        try {
            result = Arrays.stream(Objects.requireNonNull(restTemplate.exchange(
                            getUri(SAT_URL_DOWNLOAD_IMAGES),
                            HttpMethod.GET,
                            null,
                            SatDTO[].class)
                    .getBody())).toList();
        } catch (Exception e) {
            log.error("can't get response from satellite for pullImagesFromSatellite : " + e);
        }

        List<Image> imageList = result.stream().map(s -> {
            Image image = new Image();
            image.setRequest(
                    requestRepo.findById(s.getRequestId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request id not found")));
            image.setData(s.getData());
            return image;
        }).collect(Collectors.toList());

        imageRepo.saveAll(imageList);
    }

    private URI getUri(String satUrlImageCapture) {
        return UriComponentsBuilder.fromHttpUrl(satUrlImageCapture).build().encode().toUri();
    }

}
