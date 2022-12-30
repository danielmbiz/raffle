package com.example.raffle.dto;

import com.example.raffle.model.Client;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;

public class RaffleItemResponse {

    private Long id;
    private Raffle raffle;
    private Client client;
    private Integer ticket;

    public RaffleItemResponse() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public static RaffleItemResponse of(RaffleItem request) {
        var raffleItemResponse = new RaffleItemResponse();
        raffleItemResponse.setId(request.getId());
        raffleItemResponse.setRaffle(request.getRaffle());
        raffleItemResponse.setClient(request.getClient());
        raffleItemResponse.setTicket(request.getTicket());
        return raffleItemResponse;
    }
}
