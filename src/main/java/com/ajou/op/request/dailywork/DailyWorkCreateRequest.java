package com.ajou.op.request.dailywork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyWorkCreateRequest {

    private String work;
    private LocalDate workDay;

    @Builder
    public DailyWorkCreateRequest(String work, LocalDate workDay) {
        this.work = work;
        this.workDay = workDay;
    }
}
