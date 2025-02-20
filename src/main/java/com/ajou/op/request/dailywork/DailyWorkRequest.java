package com.ajou.op.request.dailywork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyWorkRequest {

    private Long id;
    private String work;
    private LocalDate workDay;

    @Builder
    public DailyWorkRequest(Long id, String work, LocalDate workDay) {
        this.id = id;
        this.work = work;
        this.workDay = workDay;
    }
}
