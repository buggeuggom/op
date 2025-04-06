package com.ajou.op.request.dailywork;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelayHistoryUpdateRequest {

    private String description;

    @Builder
    public DelayHistoryUpdateRequest(String description) {
        this.description = description;
    }
}
