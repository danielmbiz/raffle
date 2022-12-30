package com.example.raffle.service;

import com.example.raffle.dto.RaffleItemRequest;
import com.example.raffle.dto.RaffleItemResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Client;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.model.enums.TypeRaffle;
import com.example.raffle.repository.ClientRepository;
import com.example.raffle.repository.RaffleItemRepository;
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
public class RaffleItemServiceTest {
    public final static LocalDate DATE = LocalDate.parse("2022-12-28");
    public final static Client CLIENT = new Client(1L, "Daniel", "05386026941", "daniel@daniel.com", "55 48 99999-9999", "88840000");
    public final static Raffle RAFFLE = new Raffle(1L, "Rifa", DATE, TypeRaffle.ALL, 50, 10.00);
    public final static RaffleItem RAFFLE_ITEM = new RaffleItem(null, RAFFLE, CLIENT, 50);
    public final static Client CLIENT_EMPTY = new Client();
    public final static Raffle RAFFLE_EMPTY = new Raffle();
    public final static RaffleItem INVALID_RAFFLE_ITEM = new RaffleItem(null, RAFFLE_EMPTY, CLIENT_EMPTY, 0);

    @InjectMocks
    private RaffleItemService service;
    @Mock
    private RaffleItemRepository repository;
    @Mock
    private RaffleRepository raffleRepository;
    @Mock
    private ClientRepository clientRepository;

    @Test
    public void createRaffleItem_WithValidData_ReturnsRaffleItemResponse() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.of(RAFFLE));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(CLIENT));
        when(repository.save(any())).thenReturn(RAFFLE_ITEM);

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        var sut = service.save(dto);

        assertNotNull(sut);
        assertEquals(RaffleItemResponse.class, sut.getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.getTicket());
    }

    @Test
    public void createRaffleItem_WithZeroOrNullTicket_ReturnsValidationException() {
        RaffleItem raffleItemTicketZero = new RaffleItem(null, RAFFLE, CLIENT, 0);
        RaffleItem raffleItemTicketNull = new RaffleItem(null, RAFFLE, CLIENT, null);

        var dtoZero = new RaffleItemRequest(
                raffleItemTicketZero.getRaffle().getId(),
                raffleItemTicketZero.getClient().getId(),
                raffleItemTicketZero.getTicket());

        assertThatThrownBy(() -> service.save(dtoZero)).isInstanceOf(ValidationException.class);

        var dtoNull = new RaffleItemRequest(
                raffleItemTicketNull.getRaffle().getId(),
                raffleItemTicketNull.getClient().getId(),
                raffleItemTicketNull.getTicket());

        assertThatThrownBy(() -> service.save(dtoNull)).isInstanceOf(ValidationException.class);
    }
    @Test
    public void createRaffleItem_WithNullRaffle_ReturnsValidationException() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void createRaffleItem_WithNullClient_ReturnsValidationException() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.ofNullable(RAFFLE));
        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void createRaffleItem_IsNull_ValidationException() {
        var dto = new RaffleItemRequest(
                INVALID_RAFFLE_ITEM.getRaffle().getId(),
                INVALID_RAFFLE_ITEM.getClient().getId(),
                INVALID_RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void findByIdRaffleItem_WithValidData_ReturnsRaffleItemReponse() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_ITEM));

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var sut = service.findById(1L);

        assertNotNull(sut);
        assertEquals(RaffleItemResponse.class, sut.getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.getTicket());
    }

    @Test
    public void findByIdRaffleItem_WithInvalidData_ReturnsResourceNotFound() {
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(""));

        try {
            service.findById(anyLong());
        } catch (ResourceNotFoundException e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void findByAllRaffleItem_WithValidData_ReturnsListRaffleItemResponse() {
        List<RaffleItem> list = new ArrayList<>();
        list.add(RAFFLE_ITEM);
        when(repository.findAll()).thenReturn(list);

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var sut = service.findAll();

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(RaffleItemResponse.class, sut.get(0).getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.get(0).getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.get(0).getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.get(0).getTicket());
    }

    @Test
    public void findByRaffleRaffleItem_WithValidData_ReturnsListRaffleItemResponse() {
        List<RaffleItem> list = new ArrayList<>();
        list.add(RAFFLE_ITEM);
        when(repository.findByRaffle(RAFFLE)).thenReturn(list);

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var sut = service.findByRaffle(RAFFLE);

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(RaffleItemResponse.class, sut.get(0).getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.get(0).getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.get(0).getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.get(0).getTicket());
    }

    @Test
    public void updateRaffleItem_WithValidData_ReturnsRaffleItemResponse() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_ITEM));
        when(repository.save(any())).thenReturn(RAFFLE_ITEM);

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());
        var sut = service.update(1L, dto);

        assertNotNull(sut);
        assertEquals(RaffleItemResponse.class, sut.getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.getTicket());

    }

    @Test
    public void updateRaffleItem_DataIntegraty_RuntimeException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(RAFFLE_ITEM));
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void updateRaffleItem_WithInvaliddData_ReturnsValidationException() {
        when(repository.findById(999L)).thenReturn(Optional.of(RAFFLE_ITEM));
        when(repository.save(any())).thenReturn(RAFFLE_ITEM);
        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void deleteRaffleItem_WithValidData_doesNotThrowsException() {
        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deleteRaffleItem_DataIntegraty_DatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(anyLong())).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void deleteRaffleItem_DataIntegraty_EmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
