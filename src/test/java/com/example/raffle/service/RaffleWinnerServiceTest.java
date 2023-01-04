package com.example.raffle.service;

import com.example.raffle.dto.RaffleWinnerResponse;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.model.*;
import com.example.raffle.model.enums.StatusRaffle;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaffleWinnerServiceTest {
    public final static LocalDate DATE = LocalDate.parse("2022-12-28");
    public final static Raffle RAFFLE = Raffle.builder()
            .id(1L)
            .description("Rifa")
            .dateAward(DATE)
            .type(TypeRaffle.SOLD)
            .tickets(50)
            .price(10.0)
            .status(StatusRaffle.OPEN)
            .build();
    public final static Client CLIENT = Client.builder()
            .id(1L)
            .name("Daniel")
            .cpf("05386026941")
            .email("daniel@gmail.com")
            .cel("55 48 9 9999-9999")
            .postCode("88840000")
            .build();
    public final static RaffleItem RAFFLE_ITEM = new RaffleItem(1L, RAFFLE, CLIENT, 50, null);
    public final static RaffleAward RAFFLE_AWARD = RaffleAward.builder()
            .id(1L)
            .raffle(RAFFLE)
            .description("Prêmio de R$500")
            .cost(0.0)
            .build();
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
        List<Long> list = new ArrayList<>();
        when(raffleRepository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        when(repository.findByRaffleWinnerId(any())).thenReturn(list);
        when(raffleRepository.save(any())).thenReturn(Optional.of(RAFFLE));

        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

}
