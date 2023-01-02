package com.example.raffle.controller;

import com.example.raffle.dto.RaffleWinnerResponse;
import com.example.raffle.service.RaffleWinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/raffles/winners")
public class RaffleWinnerController {
    @Autowired
    private RaffleWinnerService service;

    @PostMapping(value = "/{id}")
    public ResponseEntity<List<RaffleWinnerResponse>> sortition(@PathVariable Long id) {
        var list = service.sortition(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/raffle/{id}")
    public ResponseEntity<List<RaffleWinnerResponse>> findByRaffle(@PathVariable Long id) {
        var list = service.findByRaffle(id);
        return ResponseEntity.ok().body(list);

    }

    @DeleteMapping(value = "/raffle/{raffleId}")
    public ResponseEntity<Void> delete(@PathVariable Long raffleId) {
        service.delete(raffleId);
        return ResponseEntity.noContent().build();
    }
}
