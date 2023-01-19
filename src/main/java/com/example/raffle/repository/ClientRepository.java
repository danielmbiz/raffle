package com.example.raffle.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.raffle.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	@Override
	<S extends Client> Page<S> findAll(Example<S> example, Pageable pageable);
}
