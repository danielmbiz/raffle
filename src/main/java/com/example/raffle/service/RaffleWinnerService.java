package com.example.raffle.service;

import com.example.raffle.dto.RaffleWinnerResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.RaffleWinner;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.RaffleAwardRepository;
import com.example.raffle.repository.RaffleItemRepository;
import com.example.raffle.repository.RaffleRepository;
import com.example.raffle.repository.RaffleWinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RaffleWinnerService {

    @Autowired
    private RaffleWinnerRepository repository;
    @Autowired
    private RaffleItemRepository raffleItemRepository;
    @Autowired
    private RaffleAwardRepository raffleAwardRepository;
    @Autowired
    private RaffleRepository raffleRepository;

    public List<RaffleWinnerResponse> sortition(Long id) {
        try {
            Random generator = new Random();
            var raffle = raffleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                    "Rifa não encontrada Id: " + id));
            var raffleAward = raffleAwardRepository.findByRaffle(raffle);

            if (raffle.getType() == TypeRaffle.ALL) {
                for (int i = 1; i <= raffleAward.size(); i++) {
                    int winner = generator.nextInt(raffleAward.size()) + 1;
                    var raffleItemWinner = raffleItemRepository.findByRaffleAndTicket(raffle, winner).orElseThrow(() -> new ResourceNotFoundException(
                            "Item de Rifa Vencedor não encontrado Id: " + id));
                    repository.save(RaffleWinner.of(raffleAward.get(i - 1), raffleItemWinner));
                }
            }

            return repository.findByRaffle(raffle)
                    .stream()
                    .map(RaffleWinnerResponse::of)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro não definido");
        }
    }

    public List<RaffleWinnerResponse> findByRaffle(Long id) {
        var raffle = raffleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Rifa não encontrada Id: " + id + " (Err. Raffle Award Service: 07)"));
        return repository.findByRaffle(raffle)
                .stream()
                .map(RaffleWinnerResponse::of)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Prêmio da Rifa não encontrado ID: " + id + " (Err. Raffle Service: 05)");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Raffle Award Service: 06) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
