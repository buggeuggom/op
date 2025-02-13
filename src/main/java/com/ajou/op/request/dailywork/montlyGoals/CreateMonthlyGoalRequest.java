package com.ajou.op.request.dailywork.montlyGoals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateMonthlyGoalRequest {

    private LocalDate workDay;
    private String goals;

    @Builder
    public CreateMonthlyGoalRequest(LocalDate workDay, String goals) {
        this.workDay = workDay;
        this.goals = goals;
    }
}
