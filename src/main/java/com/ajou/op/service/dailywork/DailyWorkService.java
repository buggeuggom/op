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
        if(!(user.getRole().equals(UserRole.ADMIN)||user.getRole().equals(UserRole.LEADER))) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        return getDailyWorkForm(day, writer);
    }

    @Transactional(readOnly = true)
    public List<DailyWorkFromResponse> getDailyWorkForm(LocalDate request, User user) {

        //척날 부터 토요일 까지를 가져온다.

        // MonthlyGoal 첫날의 달이랑 끝날 까지의 데이티의 달 가져오기
        //Project 시작 종료일
        //시작일 이후에 시작하는 것 중에 종료일보다 작은것 + 종료일보다 작은 것 중에 시작일보다 큰것
        //Daily work -> 첫 날 부터 끝날 까지 가져오기



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


                    // RoutineJobs 조회 - 시작일/종료일과 요일 조건을 모두 만족하는 것들만 필터링
                    List<RoutineJobResponse> routineJobs = routineJobRepository.findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(user, targetDate, targetDate).stream()
                            .filter(routineJob -> routineJob.isWorkingDay(targetDate)) // 해당 요일에 해당하는 루틴만 필터링
                            .map(en -> RoutineJobResponse.builder()
                                    .id(en.getId())
                                    .goals(en.getGoals())
                                    .workingDays(en.getWorkingDays()) // 요일 정보도 함께 전달
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
