package com.ajou.op.request.dailywork.montlyGoals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChangeMonthlyGoalRequest {

    private String goals;

    @Builder
    public ChangeMonthlyGoalRequest(String goals) {
        this.goals = goals;
    }
}
