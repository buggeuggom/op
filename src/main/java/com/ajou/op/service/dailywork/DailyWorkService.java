package com.ajou.op.service.dailywork;


import com.ajou.op.domain.DelayHistory;
import com.ajou.op.domain.dailywork.DailyWork;
import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.DelayHistoryRepository;
import com.ajou.op.repositoty.UserRepository;
import com.ajou.op.repositoty.dailywork.DailyWorkRepository;
import com.ajou.op.repositoty.dailywork.MonthlyGoalRepository;
import com.ajou.op.repositoty.dailywork.ProjectRepository;
import com.ajou.op.repositoty.dailywork.RoutineJobRepository;
import com.ajou.op.request.dailywork.DailyWorkCreateRequest;
import com.ajou.op.response.dailywork.*;
import com.ajou.op.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
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
    private final DelayHistoryRepository delayHistoryRepository;


    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkFormAdmin(LocalDate day, String email, User user) {
        User writer = userRepository.findByEmail(email).orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));
        if (!(user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.LEADER))) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        return getDailyWorkForm(day, writer, 6);
    }

    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkJSONFormAdmin(String email, User user) {
        User writer = userRepository.findByEmail(email).orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));
        if (!(user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.LEADER))) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        LocalDate startedDay = LocalDate.of(LocalDate.now().getYear(), 3, 1);
        long between = ChronoUnit.DAYS.between(startedDay, LocalDate.now());

        return getDailyWorkForm(startedDay, writer, between);
    }



    @Transactional(readOnly = true)//TODO: 수정이 많이 필요(나중에 각각으로 나누고 가져오기만 하거나 각각 가져옴게 하기)
    public List<DailyWorkFromResponse> getDailyWorkForm(LocalDate request, User user, long duration) {

        List<DailyWorkFromResponse> collected = Stream.iterate(0, i -> i + 1)
                .limit(duration)
                .map(i -> {
                    LocalDate targetDate = request.plusDays(i);

                    List<MonthlyGoalResponse> monthlyGoals = monthlyGoalRepository.findByUserAndWorkDay(user, getFirstDayOfMonth(targetDate)).stream()
                            .map(req -> MonthlyGoalResponse.builder()
                                    .id(req.getId())
                                    .goals(req.getGoals())
                                    .build())
                            .toList();

                    List<ProjectResponse> projects = projectRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                                    user,
                                    targetDate,
                                    targetDate
                            ).stream().map(en -> ProjectResponse.builder()
                                    .id(en.getId())
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    List<RoutineJobResponse> routineJobs = routineJobRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(user, targetDate, targetDate).stream()
                            .filter(routineJob -> routineJob.isWorkingDay(targetDate))
                            .map(en -> RoutineJobResponse.builder()
                                    .id(en.getId())
                                    .goals(en.getGoals())
                                    .build())
                            .toList();

                    List<DelayHistoryResponse> delayHistories = delayHistoryRepository.findAllByUserAndDescriptedAt(
                                    user,
                                    targetDate
                            ).stream().map(en -> DelayHistoryResponse.builder()
                                    .id(en.getId())
                                    .description(en.getDescription())
                                    .build())
                            .toList();

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
                            .delayHistories(delayHistories)
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

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        dailyWorkRepository.delete(entity);
    }


    public void updateDailyWork(Long id, DailyWorkCreateRequest request, User user) {

        DailyWork entity = dailyWorkRepository.findById(id).orElseThrow(
                () -> new OpApplicationException(ErrorCode.MONTHLY_GOAL_NOT_FOUND)
        );

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeWork(request.getWork());
    }

    private LocalDate getFirstDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(1);
    }
}
