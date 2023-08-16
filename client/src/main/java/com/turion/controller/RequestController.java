package com.turion.controller;

import com.turion.dto.RequestDTO;
import com.turion.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msc/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<RequestDTO> addRequest(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.addRequest(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestDTO> getRequestById(@PathVariable Integer id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }
}
