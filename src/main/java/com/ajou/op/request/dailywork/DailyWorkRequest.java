package com.ajou.op.request.dailywork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyWorkRequest {

    private String work;
    private LocalDate workDay;

    @Builder
    public DailyWorkRequest(String work, LocalDate workDay) {
        this.work = work;
        this.workDay = workDay;
    }
}
