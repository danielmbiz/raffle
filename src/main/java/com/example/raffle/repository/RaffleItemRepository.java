package com.example.raffle.repository;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RaffleItemRepository extends JpaRepository<RaffleItem, Long> {
    List<RaffleItem> findByRaffle(Raffle raffle);
    Optional<RaffleItem> findByRaffleAndTicket(Raffle raffle, Integer ticket);
}
