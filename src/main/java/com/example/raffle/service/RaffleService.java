package com.example.raffle.service;

import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.model.Raffle;
import com.example.raffle.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaffleService {

    @Autowired
    private RaffleRepository repository;

    public RaffleDTO save(RaffleDTO request) {
        try {
            var raffle = repository.save(Raffle.of(request));
            return RaffleDTO.of(raffle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("(Err. Raffle Service: 01) Erro não definido");
        }
    }

    public RaffleDTO findById(Long id) {
        var raffle = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Rifa não encontrada Id: " + id));
        return RaffleDTO.of(raffle);
    }

    public List<RaffleDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(RaffleDTO::of)
                .collect(Collectors.toList());
    }

    public RaffleDTO update(Long id, RaffleDTO obj) {
        try {
            findById(id);
            var raffle = Raffle.of(obj);
            raffle.setId(id);
            return RaffleDTO.of(repository.save(raffle));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("(Err. Raffle Service: 02) Erro não definido");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Rifa não encontrada ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Raffle Service: 03) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
