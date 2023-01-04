package com.example.raffle.dto;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleAward;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RaffleAwardResponse {
    private Long id;
    private Raffle raffle;
    private String description;
    private Double cost;

    public static RaffleAwardResponse of(RaffleAward request) {
        return RaffleAwardResponse.builder()
                .id(request.getId())
                .raffle(request.getRaffle())
                .description(request.getDescription())
                .cost(request.getCost())
                .build();
    }
}
