package com.ajou.op.request.dailywork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MonthlyGoalRequest {

    private LocalDate workDay;
    private String goals;

    @Builder
    public MonthlyGoalRequest(LocalDate workDay, String goals) {
        this.workDay = workDay;
        this.goals = goals;
    }
}
