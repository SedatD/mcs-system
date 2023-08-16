package com.turion.controller;

import com.turion.dto.SatDTO;
import com.turion.service.SatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sat")
public class SatController {

    private final SatService satService;

    public SatController(SatService satService) {
        this.satService = satService;
    }

    @PostMapping(value = "/imageCapture")
    public ResponseEntity<Boolean> imageCapture(@RequestBody SatDTO satDTO) {
        return ResponseEntity.ok(satService.imageCapture(satDTO));
    }

    @GetMapping(value = "/downloadImages")
    public ResponseEntity<List<SatDTO>> downloadImages() {
        return ResponseEntity.ok(satService.downloadImages());
    }
}
