package com.ajou.op.request.dailywork.montlyGoals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MonthlyGoalCreateRequest {

    private LocalDate workDay;
    private String goals;

    @Builder
    public MonthlyGoalCreateRequest(LocalDate workDay, String goals) {
        this.workDay = workDay;
        this.goals = goals;
    }
}
