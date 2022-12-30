package com.example.raffle.controller;

import com.example.raffle.dto.ClientDTO;
import com.example.raffle.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping(value = "/clients")
public class ClienteController {

    @Autowired
    private ClientService service;

    @PostMapping
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO request) {
        var employee = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();
        return ResponseEntity.created(uri).body(employee);

    }
}
