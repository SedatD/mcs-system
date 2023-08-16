package com.turion.service;

import com.turion.dto.RequestDTO;
import com.turion.resources.entity.Client;
import com.turion.resources.entity.Request;
import com.turion.resources.entity.Satellite;
import com.turion.resources.repo.ClientRepo;
import com.turion.resources.repo.RequestRepo;
import com.turion.resources.repo.SatelliteRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepo requestRepo;
    private final ClientRepo clientRepo;
    private final SatelliteRepo satelliteRepo;

    public RequestService(RequestRepo requestRepo, ClientRepo clientRepo, SatelliteRepo satelliteRepo) {
        this.requestRepo = requestRepo;
        this.clientRepo = clientRepo;
        this.satelliteRepo = satelliteRepo;
    }

    public RequestDTO addRequest(RequestDTO requestDTO) {
        Client client = clientRepo.findById(requestDTO.getClientId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        Satellite satellite = satelliteRepo.findById(requestDTO.getSatelliteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Satellite not found"));

        Request request = new Request();
        request.setClient(client);
        request.setSatellite(satellite);
        request.setActive(true);

        Request save = requestRepo.save(request);

        RequestDTO response = new RequestDTO();
        response.setId(save.getId());
        response.setClientId(client.getId());
        response.setSatelliteId(satellite.getId());
        response.setActive(save.isActive());

        return response;
    }

    public List<RequestDTO> getAllRequests() {
        return requestRepo.findAll().stream().map(r -> {
            RequestDTO dto = new RequestDTO();
            dto.setId(r.getId());
            dto.setClientId(r.getClient().getId());
            dto.setSatelliteId(r.getSatellite().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public RequestDTO getRequestById(Integer id) {
        Request request = requestRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));

        RequestDTO response = new RequestDTO();
        response.setId(request.getId());
        response.setClientId(request.getId());
        response.setSatelliteId(request.getId());

        return response;
    }
}
