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
            validEmpty(request.getRaffleId(), "Rifa não informada");
            var raffle = raffleRepository.findById(request.getRaffleId())
                    .orElseThrow(() -> new ValidationException("Rifa não encontrado Id: " + request.getRaffleId()));
            var raffleAward = repository.save(RaffleAward.of(request, raffle));
            return RaffleAwardResponse.of(raffleAward);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro não definido");
        }
    }

    private void validEmpty(Long valid, String message) {
        if ((valid == null) || (valid == 0)) {
            throw new ValidationException(message);
        }
    }

    public RaffleAwardResponse findById(Long id) {
        var raffleAward = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Prêmio da Rifa não encontrado Id: " + id + " (Err. Raffle Award Service: 02)"));
        return RaffleAwardResponse.of(raffleAward);
    }

    public List<RaffleAwardResponse> findAll() {
        return repository.findAll()
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
        } catch (RuntimeException e) {
            throw new ValidationException("(Err. Raffle Award Service: 03) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro não definido");
        }
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
