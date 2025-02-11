package com.ajou.op.service.dailywork;


import com.ajou.op.domain.dailywork.DailyWork;
import com.ajou.op.domain.dailywork.MonthlyGoal;
import com.ajou.op.domain.dailywork.Project;
import com.ajou.op.domain.dailywork.RoutineJob;
import com.ajou.op.domain.user.User;
import com.ajou.op.repositoty.dailywork.DailyWorkRepository;
import com.ajou.op.repositoty.dailywork.MonthlyGoalRepository;
import com.ajou.op.repositoty.dailywork.ProjectRepository;
import com.ajou.op.repositoty.dailywork.RoutineJobRepository;
import com.ajou.op.request.dailywork.DailyWorkRequest;
import com.ajou.op.request.dailywork.MonthlyGoalRequest;
import com.ajou.op.request.dailywork.ProjectRequest;
import com.ajou.op.request.dailywork.RoutineJobRequest;
import com.ajou.op.response.dailywork.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyWorkService {

    private final ProjectRepository projectRepository;
    private final MonthlyGoalRepository monthlyGoalRepository;
    private final RoutineJobRepository routineJobRepository;
    private final DailyWorkRepository dailyWorkRepository;


    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkForm(LocalDate request, User user) {

        List<MonthlyGoalResponse> monthlyGoals = monthlyGoalRepository.findByUserAndWorkDay(user, getFirstDayOfMonth(request)).stream()
                .map(req -> MonthlyGoalResponse.builder()
                        .goals(req.getGoals())
                        .build())
                .toList();


        List<DailyWorkFromResponse> collected = Stream.iterate(0, i -> i + 1)
                .limit(6)
                .map(i -> {
                    LocalDate targetDate = request.plusDays(i);

                    // Projects 조회 (시작일과 종료일 사이에 targetDate가 있는 것들)
                    List<ProjectResponse> projects = projectRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                                    user,
                                    targetDate,
                                    targetDate
                            ).stream().map(en -> ProjectResponse.builder()
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    // RoutineJobs 조회 (시작일과 종료일 사이에 targetDate가 있는 것들)
                    List<RoutineJobResponse> routineJobs = routineJobRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                                    user,
                                    targetDate,
                                    targetDate
                            ).stream().map(en -> RoutineJobResponse.builder()
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    // DailyWorks 조회
                    List<DailyWorkResponse> dailyWorks = dailyWorkRepository.findByUserAndWorkDay(
                                    user,
                                    targetDate)
                            .stream().map(en -> DailyWorkResponse.builder()
                                    .work(en.getWork())
                                    .build())
                            .toList();

                    return DailyWorkFromResponse.builder()
                            .day(targetDate)
                            .dayOfWeek(targetDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))
                            .monthlyGoals(monthlyGoals)
                            .projects(projects)
                            .routineJobs(routineJobs)
                            .dailyWorks(dailyWorks)
                            .build();
                })
                .collect(Collectors.toList());

        return collected;
    }

    public void saveMonthlyGoals(List<MonthlyGoalRequest> requests, User user) {
        List<MonthlyGoal> monthlyGoals = requests.stream().map((request) -> MonthlyGoal
                        .builder()
                        .user(user)
                        .goals(request.getGoals())
                        .workDay(getFirstDayOfMonth(request.getWorkDay()))
                        .build())
                .toList();

        monthlyGoalRepository.saveAll(monthlyGoals);
    }

    public void saveRoutineJobs(List<RoutineJobRequest> requests, User user) {
        List<RoutineJob> routinJobs = requests.stream().map((req) -> RoutineJob.builder()
                        .user(user)
                        .goals(req.getGoals())
                        .startedAt(req.getStartedAt())
                        .endedAt(req.getEndedAt())
                        .build())
                .toList();

        routineJobRepository.saveAll(routinJobs);
    }

    public void saveProjects(List<ProjectRequest> requests, User user) {
        List<Project> projects = requests.stream().map((req) -> Project.builder()
                        .user(user)
                        .goals(req.getGoals())
                        .startedAt(req.getStartedAt())
                        .endedAt(req.getEndedAt())
                        .build())
                .toList();

        projectRepository.saveAll(projects);
    }

    public void saveDailyWorks(List<DailyWorkRequest> requests, User user) {

        List<DailyWork> dailyWorks = requests.stream().map((req) -> DailyWork.builder()
                        .work(req.getWork())
                        .workDay(req.getWorkDay())
                        .user(user)
                        .build())
                .toList();

        dailyWorkRepository.saveAll(dailyWorks);
    }


    private LocalDate getFirstDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(1);
    }
}
