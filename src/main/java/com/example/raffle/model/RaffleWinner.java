package com.example.raffle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class RaffleWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "raffle_item_id", referencedColumnName = "id", nullable = false)
    private RaffleItem raffleItem;
    @OneToOne
    @JoinColumn(name = "raffle_award_id", referencedColumnName = "id", nullable = false)
    private RaffleAward raffleAward;

    public RaffleWinner() {
    }

    public RaffleWinner(Long id, RaffleItem raffleItem, RaffleAward raffleAward) {
        this.id = id;
        this.raffleItem = raffleItem;
        this.raffleAward = raffleAward;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RaffleItem getRaffleItem() {
        return raffleItem;
    }

    public void setRaffleItem(RaffleItem raffleItem) {
        this.raffleItem = raffleItem;
    }

    public RaffleAward getRaffleAward() {
        return raffleAward;
    }

    public void setRaffleAward(RaffleAward raffleAward) {
        this.raffleAward = raffleAward;
    }

    public static RaffleWinner of(RaffleAward raffleAward, RaffleItem raffleItem) {
        var raffleWinner = new RaffleWinner();
        raffleWinner.setRaffleAward(raffleAward);
        raffleWinner.setRaffleItem(raffleItem);
        return raffleWinner;
    }
}
