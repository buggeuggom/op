package com.ajou.op.response.dailywork;


import lombok.Builder;
import lombok.Getter;

@Getter
public class DelayHistoryResponse {

    private Long id;
    private String description;

    @Builder
    private DelayHistoryResponse(String description, Long id) {
        this.description = description;
        this.id = id;
    }
}
