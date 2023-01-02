package com.example.raffle.repository;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RaffleWinnerRepository extends JpaRepository<RaffleWinner, Long> {

    @Query("SELECT w FROM RaffleWinner w WHERE w.raffleItem.raffle = :raffle")
    Collection<RaffleWinner> findByRaffle(Raffle raffle);

    @Query("SELECT w FROM RaffleWinner w WHERE w.raffleItem.id = :raffleItem")
    Optional<RaffleWinner> findByRaffleWinner(Long raffleItem);

    @Query("SELECT id FROM RaffleWinner w WHERE w.raffleItem.raffle = :raffle")
    List<Long> findByRaffleWinnerId(Raffle raffle);

    void deleteByIdIn(List<Long> ids);

}
