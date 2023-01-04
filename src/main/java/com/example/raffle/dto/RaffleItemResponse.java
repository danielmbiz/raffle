package com.example.raffle.dto;

import com.example.raffle.model.Client;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RaffleItemResponse {
    private Long id;
    private Raffle raffle;
    private Client client;
    private Integer ticket;

    public static RaffleItemResponse of(RaffleItem request) {
        return RaffleItemResponse.builder()
                .id(request.getId())
                .raffle(request.getRaffle())
                .client(request.getClient())
                .ticket(request.getTicket())
                .build();
    }
}
