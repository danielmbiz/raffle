package com.example.raffle.service;

import com.example.raffle.dto.RaffleItemRequest;
import com.example.raffle.dto.RaffleItemResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.repository.ClientRepository;
import com.example.raffle.repository.RaffleItemRepository;
import com.example.raffle.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaffleItemService {

    @Autowired
    private RaffleItemRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RaffleRepository raffleRepository;

    public RaffleItemResponse save(RaffleItemRequest request) {
        try {
            validTicketNotZero(request.getTicket());
            validEmpty(request.getRaffleId(), "Rifa não informada");

            var raffle = raffleRepository.findById(request.getRaffleId())
                    .orElseThrow(() -> new ValidationException("Rifa não encontrada Id: " + request.getRaffleId()));
            validEmpty(request.getClientId(), "Cliente não informado");

            var client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ValidationException("Cliente não encontrado Id: " + request.getRaffleId()));
            validTicketExists(raffle.getTickets(), request.getTicket());
            validTciketSold(raffle, request.getTicket());
            var raffleItem = repository.save(RaffleItem.of(request, raffle, client));
            return RaffleItemResponse.of(raffleItem);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Erro não definido");
        }
    }

    private void validTicketExists(Integer tickets, Integer ticket) {
        if ((tickets < ticket) || (ticket == 0)) {
            throw new ValidationException("Número da rifa não existe, número: " + ticket);
        }
    }

    private void validTciketSold(Raffle raffle, Integer ticket) {
        var raffleItemValid = repository.findByRaffleAndTicket(raffle, ticket);
        if (raffleItemValid.isPresent()) {
            throw new ValidationException("Número de rifa já vendido, número: "+ticket);
        }
    }

    private void validTicketNotZero(Integer ticket) {
        if ((ticket == null) || (ticket == 0)) {
            throw new ValidationException("Número não pode ser ZERO");
        }
    }

    private void validEmpty(Long valid, String message) {
        if ((valid == null) || (valid == 0)) {
            throw new ValidationException(message);
        }
    }

    public RaffleItemResponse findById(Long id) {
        var raffleItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Rafflee Item não encontrado Id: " + id + " (Err. Raffle Item Service: 02)"));
        return RaffleItemResponse.of(raffleItem);
    }

    public List<RaffleItemResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(RaffleItemResponse::of)
                .collect(Collectors.toList());
    }

    public List<RaffleItemResponse> findByRaffle(Long id) {
        var raffle = raffleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Rifa não encontrada Id: " + id + " (Err. Raffle Item Service: 07)"));
        return repository.findByRaffle(raffle)
                .stream()
                .map(RaffleItemResponse::of)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Rafflee não encontrado ID: " + id + " (Err. Raffle Service: 05)");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Raffle Service: 06) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
