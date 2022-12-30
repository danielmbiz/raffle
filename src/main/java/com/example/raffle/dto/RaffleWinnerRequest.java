package com.example.raffle.dto;

public class RaffleWinnerRequest {

    private Long raffleItemId;
    private Long raffleAwardId;

    public RaffleWinnerRequest(Long raffleItemId, Long raffleAwardId) {
        this.raffleItemId = raffleItemId;
        this.raffleAwardId = raffleAwardId;
    }

    public Long getRaffleItemId() {
        return raffleItemId;
    }

    public Long getRaffleAwardId() {
        return raffleAwardId;
    }

}
