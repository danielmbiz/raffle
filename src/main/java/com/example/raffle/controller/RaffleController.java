package com.example.raffle.controller;

import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.service.RaffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/raffles")
public class RaffleController {
    @Autowired
    private RaffleService service;

    @PostMapping
    public ResponseEntity<RaffleDTO> save(@RequestBody RaffleDTO request) {
        var raffleDTO = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(raffleDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(raffleDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RaffleDTO> findById(@PathVariable Long id) {
        var raffleDTO = service.findById(id);
        return ResponseEntity.ok().body(raffleDTO);

    }

    @GetMapping
    public ResponseEntity<List<RaffleDTO>> findAll() {
        var list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RaffleDTO> update(@PathVariable Long id, @RequestBody RaffleDTO obj) {
        var raffleDTO = service.update(id, obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{id}")
                .buildAndExpand(raffleDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(raffleDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
