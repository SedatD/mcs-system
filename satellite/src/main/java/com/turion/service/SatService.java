package com.turion.service;

import com.turion.dto.SatDTO;
import com.turion.resources.entity.SatData;
import com.turion.resources.repo.SatRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SatService {

    private static final Logger log = LoggerFactory.getLogger(SatService.class);

    // Astronomy Picture of the Day by NASA
    private final static String APOD = "https://ppsstudios.com/rapod.php?showMenu=1";

    private final SatRepo satRepo;

    public SatService(SatRepo satRepo) {
        this.satRepo = satRepo;
    }

    /**
     * captures a random image
     * every time satellite receives a request
     */
    public boolean imageCapture(SatDTO dto) {
        log.info("sat received image capture");
        SatData satData = new SatData();
        satData.setRequestId(dto.getRequestId());
        satData.setData(captureImage());

        satRepo.save(satData);

        return true;
    }

    /**
     * fetch and clear images on satellite
     * @return all locally stored images on satellite
     */
    public List<SatDTO> downloadImages() {
        log.info("sat received download images");
        boolean flag = true;
        try {
            return satRepo.findAll().stream().map(s -> {
                SatDTO response = new SatDTO();
                response.setRequestId(s.getRequestId());
                response.setData(s.getData());
                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            flag = false;
            throw new RuntimeException("Image download failed : " + e);
        } finally {
            // this is deleting all locally stored images
            // after sending them to the mcs
            if (flag)
                satRepo.deleteAll();
        }
    }

    private byte[] captureImage() {
        try {
            ByteArrayOutputStream bis = new ByteArrayOutputStream();
            InputStream is = getRandomImageUrl().openStream();
            byte[] bytebuff = new byte[4096];
            int n;

            while ((n = is.read(bytebuff)) > 0) {
                bis.write(bytebuff, 0, n);
            }
            return bis.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("can not capture image: " + e);
        }
    }

    private URL getRandomImageUrl() throws IOException {
        Document doc = Jsoup.connect(APOD).userAgent("Mozilla").get();
        Element imageElement = doc.select("img").first();
        String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
//        String srcValue = imageElement.attr("src");  // exact content value of the attribute
        return new URL(absoluteUrl);
    }

    // testing purposes - not a production code!
//    public static void main(String[] args) throws IOException {
//        byte[] data = captureImage();
//
//        ByteArrayInputStream inStreambj = new ByteArrayInputStream(data);
//        BufferedImage newImage = ImageIO.read(inStreambj);
//        ImageIO.write(newImage, "jpg", new File("outputImage.jpg"));
//    }

}
