package com.ajou.op.service.dailywork;


import com.ajou.op.domain.dailywork.DailyWork;
import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.UserRepository;
import com.ajou.op.repositoty.dailywork.DailyWorkRepository;
import com.ajou.op.repositoty.dailywork.MonthlyGoalRepository;
import com.ajou.op.repositoty.dailywork.ProjectRepository;
import com.ajou.op.repositoty.dailywork.RoutineJobRepository;
import com.ajou.op.request.dailywork.DailyWorkCreateRequest;
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

import static com.ajou.op.exception.ErrorCode.INVALID_PERMISSION;
import static com.ajou.op.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyWorkService {

    private final ProjectRepository projectRepository;
    private final MonthlyGoalRepository monthlyGoalRepository;
    private final RoutineJobRepository routineJobRepository;
    private final DailyWorkRepository dailyWorkRepository;
    private final UserRepository userRepository;



    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkFormAdmin(LocalDate day, String email, User user) {
        User writer = userRepository.findByEmail(email).orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));
        if(!user.getRole().equals(UserRole.ADMIN)){
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        return getDailyWorkForm(day, writer);
    }

    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkForm(LocalDate request, User user) {

        List<DailyWorkFromResponse> collected = Stream.iterate(0, i -> i + 1)
                .limit(6)
                .map(i -> {
                    LocalDate targetDate = request.plusDays(i);

                    List<MonthlyGoalResponse> monthlyGoals = monthlyGoalRepository.findByUserAndWorkDay(user, getFirstDayOfMonth(targetDate)).stream()
                            .map(req -> MonthlyGoalResponse.builder()
                                    .id(req.getId())
                                    .goals(req.getGoals())
                                    .build())
                            .toList();

                    // Projects 조회 (시작일과 종료일 사이에 targetDate가 있는 것들)
                    List<ProjectResponse> projects = projectRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                                    user,
                                    targetDate,
                                    targetDate
                            ).stream().map(en -> ProjectResponse.builder()
                                    .id(en.getId())
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    // RoutineJobs 조회 (시작일과 종료일 사이에 targetDate 가 있는 것들)
                    List<RoutineJobResponse> routineJobs = routineJobRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                                    user,
                                    targetDate,
                                    targetDate
                            ).stream().map(en -> RoutineJobResponse.builder()
                                    .id(en.getId())
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    // DailyWorks 조회
                    List<DailyWorkResponse> dailyWorks = dailyWorkRepository.findByUserAndWorkDay(
                                    user,
                                    targetDate)
                            .stream().map(en -> DailyWorkResponse.builder()
                                    .id(en.getId())
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

    public void saveDailyWorks(List<DailyWorkCreateRequest> requests, User user) {

        List<DailyWork> dailyWorks = requests.stream().map((req) -> DailyWork.builder()
                        .work(req.getWork())
                        .workDay(req.getWorkDay())
                        .user(user)
                        .build())
                .toList();

        dailyWorkRepository.saveAll(dailyWorks);
    }

    public void deleteDailyWork(Long id, User user) {

        DailyWork entity = dailyWorkRepository.findById(id).orElseThrow(
                () -> new OpApplicationException(ErrorCode.MONTHLY_GOAL_NOT_FOUND)
        );

        if(!entity.getUser().equals(user)){
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        dailyWorkRepository.delete(entity);
    }


    public void updateDailyWork(Long id, DailyWorkCreateRequest request, User user) {

        DailyWork entity = dailyWorkRepository.findById(id).orElseThrow(
                () -> new OpApplicationException(ErrorCode.MONTHLY_GOAL_NOT_FOUND)
        );

        if(!entity.getUser().equals(user)){
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeWork(request.getWork());
    }

    private LocalDate getFirstDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(1);
    }
}
