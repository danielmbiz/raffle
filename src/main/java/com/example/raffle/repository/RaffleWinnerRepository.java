package com.example.raffle.repository;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface RaffleWinnerRepository extends JpaRepository<RaffleWinner, Long> {

    @Query("SELECT w FROM RaffleWinner w WHERE w.raffleItem.raffle = :raffle")
    Collection<RaffleWinner> findByRaffle(Raffle raffle);

}
