package com.example.raffle.service;

import com.example.raffle.dto.ClientDTO;
import com.example.raffle.dto.RaffleAwardRequest;
import com.example.raffle.dto.RaffleWinnerResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.model.*;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.RaffleAwardRepository;
import com.example.raffle.repository.RaffleItemRepository;
import com.example.raffle.repository.RaffleRepository;
import com.example.raffle.repository.RaffleWinnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaffleWinnerServiceTest {
    public final static LocalDate DATE = LocalDate.parse("2022-12-28");
    public final static Raffle RAFFLE = new Raffle(1L, "Rifa", DATE, TypeRaffle.ALL, 50, 10.00);
    public final static Client CLIENT = new Client(1L, "Daniel", "05386026941", "daniel@daniel.com", "55 48 99999-9999", "88840000");
    public final static RaffleItem RAFFLE_ITEM = new RaffleItem(1L, RAFFLE, CLIENT, 50);
    public final static RaffleAward RAFFLE_AWARD = new RaffleAward(1L, RAFFLE, "Prêmio de R$500", 0.0);
    public final static RaffleWinner RAFFLE_WINNER = new RaffleWinner(1L, RAFFLE_ITEM, RAFFLE_AWARD);

    @InjectMocks
    private RaffleWinnerService service;
    @Mock
    private RaffleWinnerRepository repository;
    @Mock
    private RaffleItemRepository raffleItemRepository;
    @Mock
    private RaffleAwardRepository raffleAwardRepository;
    @Mock
    private RaffleRepository raffleRepository;


    @Test
    public void sortitionRaffleWinne_WithValidData_ReturnsRaffleWinnerReponse() {
        List<RaffleAward> list = new ArrayList<>();
        list.add(RAFFLE_AWARD);
        List<RaffleWinner> listWin = new ArrayList<>();
        listWin.add(RAFFLE_WINNER);
        when(raffleRepository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        when(raffleItemRepository.findByRaffleAndTicket(any(), anyInt())).thenReturn(Optional.of(RAFFLE_ITEM));
        when(raffleAwardRepository.findByRaffle(any())).thenReturn(list);
        when(repository.findByRaffle(any())).thenReturn(listWin);

        var raffleWinnerResponse = RaffleWinnerResponse.of(RAFFLE_WINNER);
        var sut = service.sortition(1L);

        assertNotNull(sut);
        assertEquals(RaffleWinnerResponse.class, sut.get(0).getClass());
        assertEquals(raffleWinnerResponse.getId(), sut.get(0).getId());
        assertEquals(raffleWinnerResponse.getRaffleItem(), sut.get(0).getRaffleItem());
        assertEquals(raffleWinnerResponse.getRaffleAward(), sut.get(0).getRaffleAward());
    }

    @Test
    public void sortitionRaffleWinne_DataIntegraty_EmptyRaffleAndAward_ResourceNotFound() {
        assertThatThrownBy(() -> service.sortition(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void deleteRaffleWinner_WithValidData_doesNotThrowsException() {
        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deleteRaffleWinner_DataIntegraty_DatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(anyLong())).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void deleteRaffleWinner_DataIntegraty_EmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
