package com.example.raffle.controller;

import com.example.raffle.dto.RaffleItemRequest;
import com.example.raffle.dto.RaffleItemResponse;
import com.example.raffle.service.RaffleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/raffles/items")
public class RaffleItemController {
    @Autowired
    private RaffleItemService service;

    @PostMapping
    public ResponseEntity<RaffleItemResponse> save(@RequestBody @Valid RaffleItemRequest request) {
        var raffleItemResponse = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(raffleItemResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).body(raffleItemResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RaffleItemResponse> findById(@PathVariable Long id) {
        var raffleItemResponse = service.findById(id);
        return ResponseEntity.ok().body(raffleItemResponse);

    }

    @GetMapping
    public ResponseEntity<List<RaffleItemResponse>> findAll() {
        var list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/raffle/{id}")
    public ResponseEntity<List<RaffleItemResponse>> findByRaffle(@PathVariable @Valid Long id) {
        var raffleItemResponse = service.findByRaffle(id);
        return ResponseEntity.ok().body(raffleItemResponse);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
