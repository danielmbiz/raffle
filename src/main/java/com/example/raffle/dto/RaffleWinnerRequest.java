package com.example.raffle.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleWinnerRequest {

    private Long raffleItemId;
    private Long raffleAwardId;

}
