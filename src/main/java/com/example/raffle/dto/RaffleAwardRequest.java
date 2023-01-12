package com.example.raffle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleAwardRequest {
    @NotNull(message = "Rifa é obrigatório")
    @Min(value = 1, message = "Rifa é obrigatório")
    private Long raffleId;
    @NotBlank(message = "Descrição é obrigatório")
    private String description;
    private Double cost;
}
