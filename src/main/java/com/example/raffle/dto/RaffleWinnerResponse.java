package com.example.raffle.dto;

import com.example.raffle.model.RaffleAward;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.model.RaffleWinner;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RaffleWinnerResponse {
    private Long id;
    private RaffleItem raffleItem;
    private RaffleAward raffleAward;

    public static RaffleWinnerResponse of(RaffleWinner request) {
        return RaffleWinnerResponse.builder()
                .id(request.getId())
                .raffleAward(request.getRaffleAward())
                .raffleItem(request.getRaffleItem())
                .build();
    }
}
