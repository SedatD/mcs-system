package com.turion.service;

import com.turion.dto.SatelliteDTO;
import com.turion.resources.entity.Satellite;
import com.turion.resources.repo.SatelliteRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SatelliteService {

    private final SatelliteRepo satelliteRepo;

    public SatelliteService(SatelliteRepo satelliteRepo) {
        this.satelliteRepo = satelliteRepo;
    }

    public SatelliteDTO addSatellite(SatelliteDTO satelliteDTO) {
        Satellite satellite = new Satellite();
        satellite.setName(satelliteDTO.getName());

        Satellite save = satelliteRepo.save(satellite);

        SatelliteDTO response = new SatelliteDTO();
        response.setId(save.getId());
        response.setName(save.getName());

        return response;
    }

    public List<SatelliteDTO> getAllSatellites() {
        return satelliteRepo.findAll().stream().map(s -> {
            SatelliteDTO dto = new SatelliteDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public SatelliteDTO getSatelliteById(Integer id) {
        Satellite satellite = satelliteRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Satellite not found"));

        SatelliteDTO response = new SatelliteDTO();
        response.setId(satellite.getId());
        response.setName(satellite.getName());

        return response;
    }

}
