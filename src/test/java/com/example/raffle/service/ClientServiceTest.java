package com.example.raffle.service;

import com.example.raffle.client.ViaCepClient;
import com.example.raffle.dto.ClientDTO;
import com.example.raffle.dto.ViaCepDTO;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.FeignException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Client;
import com.example.raffle.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

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
public class ClientServiceTest {

    public final static ViaCepDTO VIA_CEP_DTO = new ViaCepDTO("88840-000", "", "", "", "Urussanga","SC", "4219002", "", "48", "8373");
    public final static Client CLIENT = new Client(null, "Daniel", "05386026941", "daniel@daniel.com", "55 48 99999-9999", "88840000");
    public final static Client INVALID_CLIENT = new Client(null, "", null, "", "", "");

    @InjectMocks
    private ClientService service;
    @Mock
    private ViaCepClient viaCepClient;
    @Mock
    private ClientRepository repository;

    @Test
    public void createClient_WithValidData_ReturnsClient() {
        when(viaCepClient.findCepByCep("88840000")).thenReturn(VIA_CEP_DTO);
        when(repository.save(any())).thenReturn(CLIENT);

        var dto = ClientDTO.of(CLIENT);
        var sut = service.save(dto);

        assertNotNull(sut);
        assertEquals(ClientDTO.class, sut.getClass());
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getName(), sut.getName());
        assertEquals(dto.getEmail(), sut.getEmail());
    }

    @Test
    public void createClient_DataIntegraty_RuntimeException() {
        when(viaCepClient.findCepByCep("")).thenReturn(VIA_CEP_DTO);
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = ClientDTO.of(INVALID_CLIENT);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createClient_DataIntegraty_DataIntegrityViolation() {
        when(viaCepClient.findCepByCep("")).thenReturn(VIA_CEP_DTO);
        when(repository.save(any())).thenThrow(DataIntegrityViolationException.class);
        var dto = ClientDTO.of(INVALID_CLIENT);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void createClient_DataIntegraty_FeignException() {
        when(viaCepClient.findCepByCep("")).thenThrow(feign.FeignException.class);

        var dto = ClientDTO.of(INVALID_CLIENT);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(FeignException.class);
    }

    @Test
    public void createClient_EmailDataIntegraty_DataIntegrityViolationExceptionReturnDatabaseException() {
        when(viaCepClient.findCepByCep("88840000")).thenReturn(VIA_CEP_DTO);
        when(repository.save(any())).thenReturn(CLIENT);

        try {
            CLIENT.setId(1L);
            var dto = ClientDTO.of(CLIENT);
            service.save(dto);
        } catch (DataIntegrityViolationException e) {
            assertEquals(DatabaseException.class, e.getClass());
        }
    }

    @Test
    public void findByIdClient_WithValidData_ReturnsClient() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(CLIENT));
        var dto = ClientDTO.of(CLIENT);

        var sut = service.findById(anyLong());

        assertEquals(ClientDTO.class, sut.getClass());
        assertNotNull(sut);
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getName(), sut.getName());
        assertEquals(dto.getEmail(), sut.getEmail());
    }

    @Test
    public void findByIdClient_WithInvalidData_ReturnsResourceNotFound() {
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(""));

        try {
            service.findById(anyLong());
        } catch (ResourceNotFoundException e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void findByAllClient_WithValidData_ReturnsListClient() {
        List<Client> list = new ArrayList<>();
        list.add(CLIENT);
        var dto = ClientDTO.of(CLIENT);
        when(repository.findAll()).thenReturn(list);

        List<ClientDTO> sut = service.findAll();

        assertThat(sut).asList().isNotEmpty();
        assertThat(sut).asList().hasSize(1);
        assertEquals(ClientDTO.class, sut.get(0).getClass());
        assertEquals(dto.getId(), sut.get(0).getId());
        assertEquals(dto.getName(), sut.get(0).getName());
        assertEquals(dto.getEmail(), sut.get(0).getEmail());

    }

    @Test
    public void updateClient_WithValidData_ReturnsClient() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(CLIENT));
        when(repository.save(any())).thenReturn(CLIENT);

        var dto = ClientDTO.of(CLIENT);

        ClientDTO sut = service.update(1L, dto);
        assertNotNull(sut);
        assertEquals(ClientDTO.class, sut.getClass());
        assertEquals(dto.getId(), sut.getId());
        assertEquals(dto.getName(), sut.getName());
        assertEquals(dto.getEmail(), sut.getEmail());
    }

    @Test
    public void updateClient_DataIntegraty_RuntimeException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(CLIENT));
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = ClientDTO.of(CLIENT);

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void deleteClient_WithValidData_doesNotThrowsException() {
        assertThatCode(() -> service.delete(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deleteClient_DataIntegraty_DatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(anyLong())).isInstanceOf(DatabaseException.class);
    }

    @Test
    public void deleteClient_DataIntegraty_EmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

}
