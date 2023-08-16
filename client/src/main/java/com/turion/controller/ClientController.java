package com.turion.controller;

import com.turion.dto.ClientDTO;
import com.turion.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msc/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.addClient(clientDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
