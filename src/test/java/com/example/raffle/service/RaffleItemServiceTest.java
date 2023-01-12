package com.example.raffle.service;

import com.example.raffle.dto.RaffleItemRequest;
import com.example.raffle.dto.RaffleItemResponse;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Client;
import com.example.raffle.model.Raffle;
import com.example.raffle.model.RaffleItem;
import com.example.raffle.model.enums.StatusRaffle;
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
    public final static Client CLIENT = Client.builder()
            .id(1L)
            .name("Daniel")
            .cpf("05386026941")
            .email("daniel@gmail.com")
            .cel("55 48 9 9999-9999")
            .postCode("88840000")
            .build();
    public final static Raffle RAFFLE = Raffle.builder()
            .id(1L)
            .description("Rifa")
            .dateAward(DATE)
            .type(TypeRaffle.SOLD)
            .tickets(50)
            .price(10.0)
            .status(StatusRaffle.OPEN)
            .build();
    public final static RaffleItem RAFFLE_ITEM = new RaffleItem(null, RAFFLE, CLIENT, 50, null);
    public final static Client CLIENT_EMPTY = new Client();
    public final static Raffle RAFFLE_EMPTY = new Raffle();
    public final static RaffleItem INVALID_RAFFLE_ITEM = new RaffleItem(null, RAFFLE_EMPTY, CLIENT_EMPTY, 0, null);

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
        RaffleItem raffleItemTicketZero = new RaffleItem(null, RAFFLE, CLIENT, 0, null);
        RaffleItem raffleItemTicketNull = new RaffleItem(null, RAFFLE, CLIENT, null, null);

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
    public void createRaffleItem_WithNullRaffle_ReturnsResourceNotFoundException() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void createRaffleItem_WithNullClient_ReturnsResourceNotFoundException() {
        when(raffleRepository.findById(1L)).thenReturn(Optional.ofNullable(RAFFLE));
        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        var dto = new RaffleItemRequest(
                RAFFLE_ITEM.getRaffle().getId(),
                RAFFLE_ITEM.getClient().getId(),
                RAFFLE_ITEM.getTicket());

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void createRaffleItem_IsNull_ReturnsValidationException() {
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
        when(raffleRepository.findById(anyLong())).thenReturn(Optional.of(RAFFLE));
        when(repository.findByRaffle(RAFFLE)).thenReturn(list);

        var raffleItemResponse = RaffleItemResponse.of(RAFFLE_ITEM);
        var sut = service.findByRaffle(1l);

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(RaffleItemResponse.class, sut.get(0).getClass());
        assertEquals(raffleItemResponse.getRaffle(), sut.get(0).getRaffle());
        assertEquals(raffleItemResponse.getClient(), sut.get(0).getClient());
        assertEquals(raffleItemResponse.getTicket(), sut.get(0).getTicket());
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
