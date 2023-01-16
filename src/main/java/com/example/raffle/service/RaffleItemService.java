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
            var raffle = raffleRepository.findById(request.getRaffleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rifa não encontrada Id: " + request.getRaffleId()));
            var client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado Id: " + request.getRaffleId()));
            validTicketExists(raffle.getTickets(), request.getTicket());
            validTciketSold(raffle, request.getTicket());
            var raffleItem = repository.save(RaffleItem.of(request, raffle, client));
            return RaffleItemResponse.of(raffleItem);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("(Err. RaffleItem Service: 01) " + e.getMessage());
        } catch (ValidationException e) {
            throw new ValidationException("(Err. RaffleItem Service: 02) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("(Err. RaffleItem Service: 03) Erro não definido");
        }
    }

    private void validTicketExists(Integer tickets, Integer ticket) {
        if (tickets < ticket) {
            throw new ResourceNotFoundException("Número da rifa não existe, número: " + ticket);
        }
    }

    private void validTciketSold(Raffle raffle, Integer ticket) {
        var raffleItemValid = repository.findByRaffleAndTicket(raffle, ticket);
        if (raffleItemValid.isPresent()) {
            throw new ValidationException("Número de rifa já vendido, número: "+ticket);
        }
    }

    private void validTicketNotZero(Integer ticket) {
        if ((ticket == null) || (ticket <= 0)) {
            throw new ValidationException("Número não pode ser ZERO");
        }
    }

    public RaffleItemResponse findById(Long id) {
        var raffleItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Rafflee Item não encontrado Id: " + id));
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
                "Rifa não encontrada Id: " + id));
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
                    "Rafflee não encontrado ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. RaffleItem Service: 04) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
