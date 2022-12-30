package com.example.raffle.dto;

public class RaffleItemRequest {
    private Long raffleId;
    private Long clientId;
    private Integer ticket;

    public RaffleItemRequest(Long raffleId, Long clientId, Integer ticket) {
        this.raffleId = raffleId;
        this.clientId = clientId;
        this.ticket = ticket;
    }

    public Long getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(Long raffleId) {
        this.raffleId = raffleId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
}
