package com.example.raffle.model;

import com.example.raffle.dto.RaffleAwardRequest;
import com.example.raffle.dto.RaffleAwardResponse;
import com.example.raffle.dto.RaffleItemRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class RaffleAward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;
    @NotNull
    @NotBlank(message = "Descrição é obrigatório")
    private String description;
    private Double cost;
    @OneToOne(mappedBy = "raffleAward")
    private RaffleWinner raffleWinner;

    public RaffleAward() {
    }

    public RaffleAward(Long id, Raffle raffle, String description, Double cost) {
        this.id = id;
        this.raffle = raffle;
        this.description = description;
        this.cost = cost;
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

    public RaffleWinner getRaffleWinner() {
        return raffleWinner;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public static RaffleAward of(RaffleAwardRequest request, Raffle raffle) {
        var raffleAward = new RaffleAward();
        raffleAward.setRaffle(raffle);
        raffleAward.setDescription(request.getDescription());
        return raffleAward;
    }
}
