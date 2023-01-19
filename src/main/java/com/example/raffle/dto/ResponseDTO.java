package com.example.raffle.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseDTO {
    private String status;
    private Integer code;
    private List<String> messages;
    private Object result;
}
