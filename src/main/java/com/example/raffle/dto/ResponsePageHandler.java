package com.example.raffle.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class ResponsePageHandler {
    public static ResponseEntity<Object> responseBuilder(HttpStatus httpStatus, Integer code, Object responseObject, PageInfoDTO pageInfo) {

        var response = ResponsePageDTO.builder()
                .status(httpStatus.getReasonPhrase())
                .code(code)
                .messages(new ArrayList<>())
                .result(responseObject)
                .pageInfo(pageInfo)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }
}
