package com.example.raffle.dto;

import com.example.raffle.model.RaffleAward;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.model.RaffleWinner;

public class RaffleWinnerResponse {
    private Long id;
    private RaffleItem raffleItem;
    private RaffleAward raffleAward;

    public RaffleWinnerResponse() {
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public static RaffleWinnerResponse of(RaffleWinner request) {
        var raffleWinnerResponse = new RaffleWinnerResponse();
        raffleWinnerResponse.setId(request.getId());
        raffleWinnerResponse.setRaffleAward(request.getRaffleAward());
        raffleWinnerResponse.setRaffleItem(request.getRaffleItem());
        return raffleWinnerResponse;
    }

}
