package com.example.raffle.model;

import com.example.raffle.dto.RaffleAwardRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class RaffleAward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;
    @NotBlank(message = "Descrição é obrigatório")
    private String description;
    private Double cost;
    @JsonIgnore
    @OneToOne(mappedBy = "raffleAward")
    private RaffleWinner raffleWinner;

    public static RaffleAward of(RaffleAwardRequest request, Raffle raffle) {
        return RaffleAward.builder()
                .description(request.getDescription())
                .raffle(raffle)
                .cost(request.getCost())
                .build();
    }
}
