package com.example.raffle.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleItemRequest {
    @NotNull(message = "Rifa é obrigatório")
    @Min(value = 1, message = "Rifa é obrigatório")
    private Long raffleId;
    @NotNull(message = "Cliente é obrigatório")
    @Min(value = 1, message = "Cliente é obrigatório")
    private Long clientId;
    @NotNull(message = "Número da rifa é obrigatório")
    private Integer ticket;

}
