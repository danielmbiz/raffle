package com.example.raffle.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleItemRequest {
    private Long raffleId;
    private Long clientId;
    @NotNull(message = "Número da rifa é obrigatório")
    private Integer ticket;

}
