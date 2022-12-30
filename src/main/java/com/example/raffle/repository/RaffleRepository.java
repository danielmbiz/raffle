package com.example.raffle.repository;

import com.example.raffle.model.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleRepository extends JpaRepository<Raffle, Long> {
}
