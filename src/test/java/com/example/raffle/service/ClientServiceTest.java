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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.raffle.client.ViaCepClient;
import com.example.raffle.dto.ClientDTO;
import com.example.raffle.dto.ViaCepDTO;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.FeignException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Client;
import com.example.raffle.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    public final static ViaCepDTO VIA_CEP_DTO = new ViaCepDTO("88840-000", "", "", "", "Urussanga","SC", "4219002", "", "48", "8373");
    public final static Client CLIENT = Client.builder()
            .name("Daniel")
            .cpf("03332916033")
            .email("daniel@gmail.com")
            .cel("55 48 9 9999-9999")
            .postCode("88840000")
            .build();

    public final static Client INVALID_CLIENT = Client.builder()
            .name("")
            .cpf("03332916033")
            .email("")
            .cel("")
            .postCode("")
            .build();

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
    public void createClient_InvalidCpf_ValidationException() {
        Client clientInvalidCpf = Client.builder()
                .name("")
                .cpf("03332916038")
                .email("")
                .cel("")
                .postCode("")
                .build();

        var dto = ClientDTO.of(clientInvalidCpf);

        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(ValidationException.class);
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
        when(repository.findAll(PageRequest.of(0,2)).getContent()).thenReturn(list);

        Page<ClientDTO> sut = service.findAll("daniel","urussanga",PageRequest.of(0,2));

        assertThat(sut.getContent()).asList().isNotEmpty();
        assertThat(sut.getContent()).asList().hasSize(1);
        assertEquals(ClientDTO.class, sut.getContent().get(0).getClass());
        assertEquals(dto.getId(), sut.getContent().get(0).getId());
        assertEquals(dto.getName(), sut.getContent().get(0).getName());
        assertEquals(dto.getEmail(), sut.getContent().get(0).getEmail());

    }

    @Test
    public void updateClient_WithValidData_ReturnsClient() {
        when(viaCepClient.findCepByCep("88840000")).thenReturn(VIA_CEP_DTO);
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
        when(viaCepClient.findCepByCep("88840000")).thenReturn(VIA_CEP_DTO);
        when(repository.findById(anyLong())).thenReturn(Optional.of(CLIENT));
        when(repository.save(any())).thenThrow(RuntimeException.class);
        var dto = ClientDTO.of(CLIENT);

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void updateClient_DataIntegraty_FeignException() {
        when(repository.findById(1L)).thenReturn(Optional.of(CLIENT));
        when(viaCepClient.findCepByCep("88840000")).thenThrow(feign.FeignException.class);
        var dto = ClientDTO.of(CLIENT);

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(FeignException.class);
    }

    @Test
    public void updateClient_InvalidCpf_ValidationException() {
        Client clientInvalidCpf = Client.builder()
                .name("")
                .cpf("03332916038")
                .email("")
                .cel("")
                .postCode("")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(clientInvalidCpf));
        var dto = ClientDTO.of(clientInvalidCpf);

        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void updateClient_EmailDataIntegraty_DataIntegrityViolationExceptionReturnDatabaseException() {
        when(viaCepClient.findCepByCep("88840000")).thenReturn(VIA_CEP_DTO);
        when(repository.findById(anyLong())).thenReturn(Optional.of(CLIENT));
        when(repository.save(CLIENT)).thenReturn(CLIENT);

        var dto = ClientDTO.of(CLIENT);
        assertThatThrownBy(() -> service.update(anyLong(), dto)).isInstanceOf(DatabaseException.class);
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
