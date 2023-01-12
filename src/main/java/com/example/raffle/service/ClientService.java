package com.example.raffle.service;

import com.example.raffle.client.ViaCepClient;
import com.example.raffle.dto.ClientDTO;
import com.example.raffle.exception.DatabaseException;
import com.example.raffle.exception.FeignException;
import com.example.raffle.exception.ResourceNotFoundException;
import com.example.raffle.exception.ValidationException;
import com.example.raffle.model.Client;
import com.example.raffle.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.raffle.util.Validation.isValidCpf;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ViaCepClient viaCepClient;

    public ClientDTO save(ClientDTO request) {
        try {
            validCpf(request.getCpf());
            var viaCep = viaCepClient.findCepByCep(request.getPostCode());
            validPostCode(viaCep.getCep());
            request.setPostCode(viaCep.getCep());
            request.setCity(viaCep.getLocalidade());
            request.setState(viaCep.getUf());
            request.setIbgeCity(viaCep.getIbge());
            var client = repository.save(Client.of(request));
            return ClientDTO.of(client);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Client Service: 01) " + e.getMessage());
        } catch (feign.FeignException e) {
            throw new FeignException("(Err. Client Service: 02) " + e.getMessage());
        } catch (ValidationException e) {
            throw new ValidationException("(Err. Client Service: 03) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("(Err. Client Service: 04) Erro não definido");
        }
    }

    private void validCpf(String cpf) {
        if (!isValidCpf(cpf))
            throw new ValidationException("CPF inválido");
    }

    private void validPostCode(String cep) {
        if (cep == null) {
            throw new FeignException("CEP não encontrado");
        }
    }

    public ClientDTO findById(Long id) {
        var client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Cliente não encontrado Id: " + id));
        return ClientDTO.of(client);
    }

    public List<ClientDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ClientDTO::of)
                .collect(Collectors.toList());
    }

    public ClientDTO update(Long id, ClientDTO obj) {
        try {
            findById(id);
            var client = Client.of(obj);
            validCpf(client.getCpf());
            var viaCep = viaCepClient.findCepByCep(client.getPostCode());
            validPostCode(viaCep.getCep());
            client.setPostCode(viaCep.getCep());
            client.setCity(viaCep.getLocalidade());
            client.setState(viaCep.getUf());
            client.setIbgeCity(viaCep.getIbge());
            client.setId(id);
            return ClientDTO.of(repository.save(client));
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Client Service: 05) " + e.getMessage());
        } catch (feign.FeignException e) {
            throw new FeignException("(Err. Client Service: 06) " + e.getMessage());
        } catch (ValidationException e) {
            throw new ValidationException("(Err. Client Service: 07) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("(Err. Client Service: 08) " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Cliente não encontrado ID: " + id + " (Err. Client Service: 05)");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("(Err. Client Service: 06) " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
