package com.example.raffle.dto;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleAward;

public class RaffleAwardResponse {

    private Long id;
    private Raffle raffle;
    private String description;
    private Double cost;

    public RaffleAwardResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Raffle getRaffle() {
        return raffle;
    }

    public void setRaffle(Raffle raffle) {
        this.raffle = raffle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public static RaffleAwardResponse of(RaffleAward request) {
        var raffleAwardResponse = new RaffleAwardResponse();
        raffleAwardResponse.setId(request.getId());
        raffleAwardResponse.setRaffle(request.getRaffle());
        raffleAwardResponse.setDescription(request.getDescription());
        return raffleAwardResponse;
    }

}
