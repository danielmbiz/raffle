package com.example.raffle.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ListError {
    private String status;
    private Integer code;
    private final List<ErrorObject> messages;
    private List<String> result;
}