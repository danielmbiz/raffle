package com.example.raffle.util;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.example.raffle.model.Client;

public class QueryBuilder {
	public static Example<Client> makeQuery(Client client) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
		return Example.of(client, exampleMatcher);
	}
}
