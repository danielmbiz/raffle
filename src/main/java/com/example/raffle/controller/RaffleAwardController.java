package com.example.raffle.controller;

import com.example.raffle.dto.RaffleAwardRequest;
import com.example.raffle.dto.RaffleAwardResponse;
import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.service.RaffleAwardService;
import com.example.raffle.service.RaffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/raffles/awards")
public class RaffleAwardController {
    @Autowired
    private RaffleAwardService service;

    @PostMapping
    public ResponseEntity<RaffleAwardResponse> save(@RequestBody RaffleAwardRequest request) {
        var raffleAwardResponse = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(raffleAwardResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).body(raffleAwardResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RaffleAwardResponse> findById(@PathVariable Long id) {
        var raffleAwardResponse = service.findById(id);
        return ResponseEntity.ok().body(raffleAwardResponse);

    }

    @GetMapping
    public ResponseEntity<List<RaffleAwardResponse>> findAll() {
        var list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RaffleAwardResponse> update(@PathVariable Long id, @RequestBody RaffleAwardRequest obj) {
        var raffleAwardResponse = service.update(id, obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{id}")
                .buildAndExpand(raffleAwardResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).body(raffleAwardResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
