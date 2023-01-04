package com.example.raffle.model;

import com.example.raffle.dto.RaffleItemRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class RaffleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "raffle_id", nullable = false)
    private Raffle raffle;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @NotNull(message = "Número da rifa é obrigatório")
    private Integer ticket;
    @JsonIgnore
    @OneToOne(mappedBy = "raffleItem")
    private RaffleWinner raffleWinner;

    public static RaffleItem of(RaffleItemRequest request, Raffle raffle, Client client) {
        return RaffleItem.builder()
                .raffle(raffle)
                .client(client)
                .ticket(request.getTicket())
                .build();
    }
}
