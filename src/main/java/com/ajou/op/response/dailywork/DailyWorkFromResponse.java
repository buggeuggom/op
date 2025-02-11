package com.ajou.op.response.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DailyWorkFromResponse {

    private LocalDate day; //년월일

    private String dayOfWeek; //요일

    private List<MonthlyGoalResponse> monthlyGoals;

    private List<ProjectResponse> projects;

    private List<RoutineJobResponse> routineJobs;

    private List<DailyWorkResponse> dailyWorks;
}
