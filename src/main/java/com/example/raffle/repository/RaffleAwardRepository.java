package com.example.raffle.repository;

import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleAward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaffleAwardRepository extends JpaRepository<RaffleAward, Long> {

    List<RaffleAward> findByRaffle(Raffle raffle);

}
