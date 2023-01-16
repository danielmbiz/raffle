package com.example.raffle.service;

import com.example.raffle.dto.RaffleAwardRequest;
import com.example.raffle.dto.RaffleAwardResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.RaffleAward;
import com.example.raffle.repository.RaffleAwardRepository;
import com.example.raffle.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaffleAwardService {
    @Autowired
    private RaffleAwardRepository repository;

    @Autowired
    private RaffleRepository raffleRepository;

    public RaffleAwardResponse save(RaffleAwardRequest request) {
        try {
            var raffle = raffleRepository.findById(request.getRaffleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rifa não encontrado Id: " + request.getRaffleId()));
            var raffleAward = repository.save(RaffleAward.of(request, raffle));
            return RaffleAwardResponse.of(raffleAward);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("(Err. RaffleAward Service: 01) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("(Err. RaffleAward Service: 02) Erro não definido");
        }
    }

    public RaffleAwardResponse findById(Long id) {
        var raffleAward = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Prêmio da Rifa não encontrado Id: " + id));
        return RaffleAwardResponse.of(raffleAward);
    }

    public List<RaffleAwardResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(RaffleAwardResponse::of)
                .collect(Collectors.toList());
    }

    public List<RaffleAwardResponse> findByRaffle(Long idRaffle) {
        var raffle = raffleRepository.findById(idRaffle)
                .orElseThrow(() -> new ResourceNotFoundException("Rifa não encontrado Id: " + idRaffle));
        return repository.findByRaffle(raffle)
                .stream()
                .map(RaffleAwardResponse::of)
                .collect(Collectors.toList());
    }

    public RaffleAwardResponse update(Long id, RaffleAwardRequest obj) {
        try {
            var raffleAwardResponse = findById(id);
            var raffleAward = RaffleAward.of(obj, raffleAwardResponse.getRaffle());
            raffleAward.setId(id);
            return RaffleAwardResponse.of(repository.save(raffleAward));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("(Err. RaffleAward Service: 03) Erro não definido");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Prêmio da Rifa não encontrado ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. RaffleAward Service: 04) " +  e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
