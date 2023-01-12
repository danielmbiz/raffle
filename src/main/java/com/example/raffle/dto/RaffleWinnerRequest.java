package com.example.raffle.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleWinnerRequest {

    @NotNull(message = "Item da Rifa é obrigatório")
    @Min(value = 1, message = "Item da Rifa é obrigatório")
    private Long raffleItemId;
    @NotNull(message = "Prêmio da Rifa é obrigatório")
    @Min(value = 1, message = "Prêmio da Rifa é obrigatório")
    private Long raffleAwardId;

}
