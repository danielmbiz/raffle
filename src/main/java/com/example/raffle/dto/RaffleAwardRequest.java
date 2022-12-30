package com.example.raffle.dto;

public class RaffleAwardRequest {

    private Long raffleId;
    private String description;
    private Double cost;

    public RaffleAwardRequest(Long raffleId, String description, Double cost) {
        this.raffleId = raffleId;
        this.description = description;
        this.cost = cost;
    }

    public Long getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(Long raffleId) {
        this.raffleId = raffleId;
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
}
