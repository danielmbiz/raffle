package com.example.raffle.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.raffle.dto.PageInfoDTO;
import com.example.raffle.dto.ResponseHandler;
import com.example.raffle.dto.ResponsePageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.raffle.dto.ClientDTO;
import com.example.raffle.service.ClientService;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {

	@Autowired
	private ClientService service;

	@PostMapping
	public ResponseEntity<ClientDTO> save(@RequestBody @Valid ClientDTO request) {
		var client = service.save(request);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(client.getId())
				.toUri();
		return ResponseEntity.created(uri).body(client);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		var client = service.findById(id);
		return ResponseEntity.ok().body(client);

	}

	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String city,
			Pageable pageable) {
		var response = service.findAll(name, city, pageable);
		var pageInfo = PageInfoDTO.builder()
				.current(response.getNumber())
				.last(response.getTotalPages())
				.size(response.getSize())
				.count(response.getTotalElements())
				.build();
		var listResponse = response
				.stream()
				.collect(Collectors.toList());
		return ResponsePageHandler.responseBuilder(
				HttpStatus.OK,
				200,
				listResponse,
				pageInfo);
	}

	@GetMapping(value = "filter")
	public ResponseEntity<?> findCustom(
			@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String city) {
		return ResponseHandler.responseBuilder(
				HttpStatus.OK,
				200,
				service.findCustom(id, name, city));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody @Valid ClientDTO obj) {
		var clientDto = service.update(id, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(clientDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(clientDto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
