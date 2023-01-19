package com.example.raffle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.raffle.dto.RaffleDTO;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.enums.StatusRaffle;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.RaffleRepository;

@ExtendWith(MockitoExtension.class)
public class RaffleServiceTest {

    public final static LocalDate DATE = LocalDate.parse("2022-12-28");
    public final static Raffle RAFFLE = Raffle.builder()
            .description("Rifa")
            .dateAward(DATE)
            .type(TypeRaffle.SOLD)
            .tickets(50)
            .price(10.0)
            .status(StatusRaffle.OPEN)
            .build();
    public final static Raffle INVALID_RAFFLE = Raffle.builder()
            .description("")
            .dateAward(null)
            .type(null)
            .tickets(0)
            .price(0.0)
            .status(null)
            .build();

    @InjectMocks
    private RaffleService service;
    @Mock
    private RaffleRepository repository;

    @Test
    public void createRaffle_WithValidData_ReturnsRaffle() {
        when(repository.save(any())).thenReturn(RAFFLE);
        var dto = RaffleDTO.of(RAFFLE);

        var sut = service.save(dto);

        assertNotNull(sut);
        assertEquals(RaffleDTO.class, sut.getClass());
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getDescription(), sut.getDescription());
        assertEquals(dto.getDateAward(), sut.getDateAward());
        assertEquals(dto.getType(), sut.getType());
        assertEquals(dto.getTickets(), sut.getTickets());
        assertEquals(dto.getPrice(), sut.getPrice());
    }

    @Test
    public void createRaffle_DataIntegraty_RuntimeException() {
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = RaffleDTO.of(INVALID_RAFFLE);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createRaffle_DataIntegraty_DataIntegrityViolation() {
        when(repository.save(any())).thenThrow(DataIntegrityViolationException.class);
        var dto = RaffleDTO.of(INVALID_RAFFLE);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void createRaffle_EmailDataIntegraty_DataIntegrityViolationExceptionReturnDatabaseException() {
        when(repository.save(any())).thenReturn(RAFFLE);

        try {
            RAFFLE.setId(1L);
            var dto = RaffleDTO.of(RAFFLE);
            service.save(dto);
        } catch (DataIntegrityViolationException e) {
            assertEquals(DatabaseException.class, e.getClass());
        }
    }

    @Test
    public void findByIdRaffle_WithValidData_ReturnsRaffle() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        var dto = RaffleDTO.of(RAFFLE);

        var sut = service.findById(anyLong());

        assertNotNull(sut);
        assertEquals(RaffleDTO.class, sut.getClass());
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getDescription(), sut.getDescription());
        assertEquals(dto.getDateAward(), sut.getDateAward());
        assertEquals(dto.getType(), sut.getType());
        assertEquals(dto.getTickets(), sut.getTickets());
        assertEquals(dto.getPrice(), sut.getPrice());
    }

    @Test
    public void findByIdRaffle_WithInvalidData_ReturnsResourceNotFound() {
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(""));

        try {
            service.findById(anyLong());
        } catch (ResourceNotFoundException e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void findByAllRaffle_WithValidData_ReturnsListRaffle() {
        List<Raffle> list = new ArrayList<>();
        list.add(RAFFLE);
        var dto = RaffleDTO.of(RAFFLE);
        when(repository.findAll()).thenReturn(list);

        List<RaffleDTO> sut = service.findAll();

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(RaffleDTO.class, sut.get(0).getClass());
        assertEquals(dto.getId(), sut.get(0).getId());
        assertEquals(dto.getDescription(), sut.get(0).getDescription());
        assertEquals(dto.getDateAward(), sut.get(0).getDateAward());
        assertEquals(dto.getType(), sut.get(0).getType());
        assertEquals(dto.getTickets(), sut.get(0).getTickets());
        assertEquals(dto.getPrice(), sut.get(0).getPrice());
    }

    @Test
    public void updateRaffle_WithValidData_ReturnsRaffle() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        when(repository.save(any())).thenReturn(RAFFLE);

        var dto = RaffleDTO.of(RAFFLE);

        RaffleDTO sut = service.update(1L, dto);

        assertNotNull(sut);
        assertEquals(RaffleDTO.class, sut.getClass());
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getDescription(), sut.getDescription());
        assertEquals(dto.getDateAward(), sut.getDateAward());
        assertEquals(dto.getType(), sut.getType());
        assertEquals(dto.getTickets(), sut.getTickets());
        assertEquals(dto.getPrice(), sut.getPrice());
    }

    @Test
    public void updateRaffle_DataIntegraty_RuntimeException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = RaffleDTO.of(RAFFLE);

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void deleteRaffle_WithValidData_doesNotThrowsException() {
        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deleteRaffle_DataIntegraty_DatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(anyLong())).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void deleteRaffle_DataIntegraty_EmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

}
