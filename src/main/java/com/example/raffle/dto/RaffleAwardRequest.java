package com.example.raffle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaffleAwardRequest {
    private Long raffleId;
    @NotBlank(message = "Descrição é obrigatório")
    private String description;
    private Double cost;
}
