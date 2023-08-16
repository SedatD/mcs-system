package com.turion.controller;

import com.turion.dto.SatelliteDTO;
import com.turion.service.SatelliteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msc/satellite")
public class SatelliteController {

    private final SatelliteService satelliteService;

    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<SatelliteDTO> addSatellite(@RequestBody SatelliteDTO satelliteDTO) {
        return ResponseEntity.ok(satelliteService.addSatellite(satelliteDTO));
    }

    @GetMapping
    public ResponseEntity<List<SatelliteDTO>> getAllSatellites() {
        return ResponseEntity.ok(satelliteService.getAllSatellites());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SatelliteDTO> getSatelliteById(@PathVariable Integer id) {
        return ResponseEntity.ok(satelliteService.getSatelliteById(id));
    }
}
