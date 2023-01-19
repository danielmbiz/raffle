package com.example.raffle.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageInfoDTO {
    private Integer current;
    private Integer last;
    private Integer size;
    private Long count;
}
