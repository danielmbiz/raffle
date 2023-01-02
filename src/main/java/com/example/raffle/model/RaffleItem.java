package com.example.raffle.model;

import com.example.raffle.dto.RaffleItemRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class RaffleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @NotNull
    private Integer ticket;
    @JsonIgnore
    @OneToOne(mappedBy = "raffleItem")
    private RaffleWinner raffleWinner;

    public RaffleItem() {
    }

    public RaffleItem(Long id, Raffle raffle, Client client, Integer ticket) {
        this.id = id;
        this.raffle = raffle;
        this.client = client;
        this.ticket = ticket;
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

    public RaffleWinner getRaffleWinner() {
        return raffleWinner;
    }

    public void setRaffleWinner(RaffleWinner raffleWinner) {
        this.raffleWinner = raffleWinner;
    }

    public static RaffleItem of(RaffleItemRequest request, Raffle raffle, Client client) {
        var raffleItem = new RaffleItem();
        raffleItem.setRaffle(raffle);
        raffleItem.setClient(client);
        raffleItem.setTicket(request.getTicket());
        return raffleItem;
    }
}
