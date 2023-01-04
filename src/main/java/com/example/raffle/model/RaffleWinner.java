package com.example.raffle.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class RaffleWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "raffle_item_id", referencedColumnName = "id", nullable = false)
    private RaffleItem raffleItem;
    @OneToOne
    @JoinColumn(name = "raffle_award_id", referencedColumnName = "id", nullable = false)
    private RaffleAward raffleAward;

    public static RaffleWinner of(RaffleAward raffleAward, RaffleItem raffleItem) {
        return RaffleWinner.builder()
                .raffleAward(raffleAward)
                .raffleItem(raffleItem)
                .build();
    }
}
