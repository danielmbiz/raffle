package com.example.raffle.service;

import com.example.raffle.dto.RaffleWinnerResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleAward;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.model.RaffleWinner;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.RaffleAwardRepository;
import com.example.raffle.repository.RaffleItemRepository;
import com.example.raffle.repository.RaffleRepository;
import com.example.raffle.repository.RaffleWinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
            var raffle = raffleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                    "Rifa não encontrada Id: " + id));
            validStatusRaffle(raffle.getStatus());
            var raffleAward = raffleAwardRepository.findByRaffle(raffle);
            setWinner(raffle, raffleAward);
            raffle.setStatus(StatusRaffle.CLOSE);
            raffleRepository.save(raffle);
            return repository.findByRaffle(raffle)
                    .stream()
                    .map(RaffleWinnerResponse::of)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro não definido");
        }
    }

    private void validStatusRaffle(StatusRaffle status) {
        if (status == StatusRaffle.CLOSE) {
            throw new ValidationException("Rifa está com status Fechado, não é possível sortear novamente");
        }
    }

    private void setWinner(Raffle raffle, List<RaffleAward> raffleAward) {
        Random generator = new Random();
        for (int i = 1; i <= raffleAward.size(); i++) {
            var bValid = true;
            Optional<RaffleItem> raffleItemWinner = Optional.empty();
            while (bValid) {
                int winner = generator.nextInt(raffle.getTickets()) + 1;
                raffleItemWinner = raffleItemRepository.findByRaffleAndTicket(raffle, winner);
                if ((raffleItemWinner.isPresent()) || (raffle.getType() == TypeRaffle.ALL)) {
                    var raffleItem = repository.findByRaffleWinner(raffleItemWinner.get().getId());
                    if (raffleItem.isEmpty()) {
                        bValid = false;
                    }
                }
            }
            if (raffleItemWinner.isPresent()) {
                repository.save(RaffleWinner.of(raffleAward.get(i - 1), raffleItemWinner.get()));
            }
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

    @Transactional
    public void delete(Long raffleId) {
        try {
            var raffle = raffleRepository.findById(raffleId).orElseThrow(() -> new ResourceNotFoundException(
                    "Rifa não encontrada Id: " + raffleId + " (Err. Raffle Award Service: 07)"));
            var raffleWinnerIds = repository.findByRaffleWinnerId(raffle);
            repository.deleteByIdIn(raffleWinnerIds);
            raffle.setStatus(StatusRaffle.OPEN);
            raffleRepository.save(raffle);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Prêmio da Rifa não encontrado ID: " + raffleId + " (Err. Raffle Service: 05)");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Raffle Award Service: 06) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
