package com.example.raffle.client;

import com.example.raffle.dto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCepClient", contextId = "viaCepClient", url = "http://viacep.com.br")
public interface ViaCepClient {
    @GetMapping("/ws/{cep}/json/")
    ViaCepDTO findCepByCep(@PathVariable String cep);
}
