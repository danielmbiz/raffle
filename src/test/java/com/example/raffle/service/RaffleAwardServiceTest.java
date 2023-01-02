package com.example.raffle.service;

import com.example.raffle.dto.RaffleAwardRequest;
import com.example.raffle.dto.RaffleAwardResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleAward;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.RaffleAwardRepository;
import com.example.raffle.repository.RaffleRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaffleAwardServiceTest {
    public final static LocalDate DATE = LocalDate.parse("2022-12-28");
    public final static Raffle RAFFLE = new Raffle(1L, "Rifa", DATE, TypeRaffle.ALL, 50, 10.00, StatusRaffle.OPEN);
    public final static RaffleAward RAFFLE_AWARD = new RaffleAward(1L, RAFFLE, "Prêmio de R$500", 0.0);

    @InjectMocks
    private RaffleAwardService service;
    @Mock
    private RaffleAwardRepository repository;
    @Mock
    private RaffleRepository raffleRepository;

    @Test
    public void createRaffleAward_WithValidData_ReturnsRaffleAwardResponse() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.of(RAFFLE));
        when(repository.save(any())).thenReturn(RAFFLE_AWARD);

        var raffleAwardResponse = RaffleAwardResponse.of(RAFFLE_AWARD);
        var dto = new RaffleAwardRequest(
                RAFFLE_AWARD.getRaffle().getId(),
                RAFFLE_AWARD.getDescription(),
                RAFFLE_AWARD.getCost());

        var sut = service.save(dto);

        assertNotNull(sut);
        assertEquals(RaffleAwardResponse.class, sut.getClass());
        assertEquals(raffleAwardResponse.getId(), sut.getId());
        assertEquals(raffleAwardResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleAwardResponse.getDescription(), sut.getDescription());
        assertEquals(raffleAwardResponse.getCost(), sut.getCost());
    }

    @Test
    public void createRaffleAward_WithNullRaffle_ReturnsValidationException() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        var dto = new RaffleAwardRequest(
                RAFFLE_AWARD.getRaffle().getId(),
                RAFFLE_AWARD.getDescription(),
                RAFFLE_AWARD.getCost());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void createRaffleAward_IsNull_ValidationException() {
        var invalidRaffleAward = new RaffleAward(null, RAFFLE, null, 0.0);
        var dto = new RaffleAwardRequest(
                invalidRaffleAward.getRaffle().getId(),
                invalidRaffleAward.getDescription(),
                invalidRaffleAward.getCost());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void findByIdRaffleAward_WithValidData_ReturnsRaffleAwardReponse() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_AWARD));

        var raffleAwardResponse = RaffleAwardResponse.of(RAFFLE_AWARD);
        var sut = service.findById(1L);

        assertNotNull(sut);
        assertEquals(RaffleAwardResponse.class, sut.getClass());
        assertEquals(raffleAwardResponse.getId(), sut.getId());
        assertEquals(raffleAwardResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleAwardResponse.getDescription(), sut.getDescription());
        assertEquals(raffleAwardResponse.getCost(), sut.getCost());
    }

    @Test
    public void findByIdRaffleAward_WithInvalidData_ReturnsResourceNotFound() {
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(""));

        try {
            service.findById(anyLong());
        } catch (ResourceNotFoundException e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void findByAllRaffleAward_WithValidData_ReturnsListRaffleAwardResponse() {
        List<RaffleAward> list = new ArrayList<>();
        list.add(RAFFLE_AWARD);
        when(repository.findAll()).thenReturn(list);

        var raffleAwardResponse = RaffleAwardResponse.of(RAFFLE_AWARD);
        var sut = service.findAll();

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(RaffleAwardResponse.class, sut.get(0).getClass());
        assertEquals(raffleAwardResponse.getId(), sut.get(0).getId());
        assertEquals(raffleAwardResponse.getRaffle(), sut.get(0).getRaffle());
        assertEquals(raffleAwardResponse.getDescription(), sut.get(0).getDescription());
        assertEquals(raffleAwardResponse.getCost(), sut.get(0).getCost());
    }

    @Test
    public void updateRaffleAward_WithValidData_ReturnsRaffleAwardResponse() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_AWARD));
        when(repository.save(any())).thenReturn(RAFFLE_AWARD);

        var raffleAwardResponse = RaffleAwardResponse.of(RAFFLE_AWARD);
        var dto = new RaffleAwardRequest(
                RAFFLE_AWARD.getRaffle().getId(),
                RAFFLE_AWARD.getDescription(),
                RAFFLE_AWARD.getCost());

        var sut = service.update(1L, dto);

        assertNotNull(sut);
        assertEquals(RaffleAwardResponse.class, sut.getClass());
        assertEquals(raffleAwardResponse.getId(), sut.getId());
        assertEquals(raffleAwardResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleAwardResponse.getDescription(), sut.getDescription());
        assertEquals(raffleAwardResponse.getCost(), sut.getCost());

    }

    @Test
    public void updateRaffleAward_DataIntegraty_RuntimeException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_AWARD));
        when(repository.save(any())).thenThrow(RuntimeException.class);

        var dto = new RaffleAwardRequest(
                RAFFLE_AWARD.getRaffle().getId(),
                RAFFLE_AWARD.getDescription(),
                RAFFLE_AWARD.getCost());

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void updateRaffleAward_WithInvaliddData_ReturnsValidationException() {
        when(repository.findById(999L)).thenReturn(Optional.of(RAFFLE_AWARD));
        when(repository.save(any())).thenReturn(RAFFLE_AWARD);

        var dto = new RaffleAwardRequest(
                RAFFLE_AWARD.getRaffle().getId(),
                RAFFLE_AWARD.getDescription(),
                RAFFLE_AWARD.getCost());

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void deleteRaffleAward_WithValidData_doesNotThrowsException() {
        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deleteRaffleAward_DataIntegraty_DatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(anyLong())).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void deleteRaffleAward_DataIntegraty_EmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
