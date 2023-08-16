package com.turion.service;

import com.turion.dto.ClientDTO;
import com.turion.resources.entity.Client;
import com.turion.resources.entity.Satellite;
import com.turion.resources.repo.ClientRepo;
import com.turion.resources.repo.SatelliteRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final SatelliteRepo satelliteRepo;

    public ClientService(ClientRepo clientRepo, SatelliteRepo satelliteRepo) {
        this.clientRepo = clientRepo;
        this.satelliteRepo = satelliteRepo;
    }

    public ClientDTO addClient(ClientDTO clientDTO) {
        Satellite satellite = satelliteRepo
                .findById(clientDTO.getSatelliteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Satellite not found"));

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setSatellite(satellite);

        Client save = clientRepo.save(client);

        ClientDTO response = new ClientDTO();
        response.setId(save.getId());
        response.setName(save.getName());
        response.setSatelliteId(save.getSatellite().getId());

        return response;
    }

    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll().stream().map(c -> {
            ClientDTO dto = new ClientDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());
            dto.setSatelliteId(c.getSatellite().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
