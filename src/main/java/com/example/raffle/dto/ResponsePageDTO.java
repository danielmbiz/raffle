package com.example.raffle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.Name;

import java.util.List;

@Getter
@Builder
public class ResponsePageDTO {
    private String status;
    private Integer code;
    private List<String> messages;
    private Object result;
    @JsonProperty("page-info")
    private PageInfoDTO pageInfo;

}
