package com.example.raffle.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StandardError {
	private String status;
	private Integer code;
	private List<String> message;
	private List<String> result;

}