package com.ajou.op.request.dailywork;

import com.ajou.op.domain.dailywork.routine.DayOfWeekBit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class RoutineJobRequest {


    private LocalDate startedAt;
    private LocalDate endedAt;
    private String goals;
    private Set<DayOfWeekBit> workingDays;


    public RoutineJobRequest(LocalDate startedAt, LocalDate endedAt, String goals, Set<DayOfWeekBit> workingDays) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.goals = goals;
        this.workingDays =  workingDays;
    }
}
