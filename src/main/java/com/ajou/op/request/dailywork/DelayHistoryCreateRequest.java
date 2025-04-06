package com.ajou.op.request.dailywork;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DelayHistoryCreateRequest {

    private String description;
    private LocalDate descriptedAt;

    @Builder
    public DelayHistoryCreateRequest(String description, LocalDate descriptedAt) {
        this.description = description;
        this.descriptedAt = descriptedAt;
    }
}
