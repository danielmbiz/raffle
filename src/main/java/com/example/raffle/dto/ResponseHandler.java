package com.example.raffle.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(HttpStatus httpStatus, Integer code, Object responseObject) {

        var response = ResponseDTO.builder()
                .status(httpStatus.getReasonPhrase())
                .code(code)
                .messages(new ArrayList<>())
                .result(responseObject)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }
}
