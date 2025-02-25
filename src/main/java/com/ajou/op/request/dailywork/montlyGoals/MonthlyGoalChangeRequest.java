package com.ajou.op.request.dailywork.montlyGoals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyGoalChangeRequest {

    private String goals;

    @Builder
    public MonthlyGoalChangeRequest(String goals) {
        this.goals = goals;
    }
}
