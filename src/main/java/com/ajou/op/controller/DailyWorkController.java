package com.ajou.op.controller;

import com.ajou.op.domain.user.User;
import com.ajou.op.request.dailywork.*;
import com.ajou.op.response.dailywork.DailyWorkFromResponse;
import com.ajou.op.service.dailywork.DailyWorkService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-works")
public class DailyWorkController {

    private final DailyWorkService dailyworkService;

    @GetMapping
    public List<DailyWorkFromResponse> getDailyWorkForm(@ModelAttribute GetDailyWorkRequest request, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        return dailyworkService.getDailyWorkForm(request.getDay(), user);
    }

    @PostMapping("/daily-works")
    public void saveDailyWork(@RequestBody List<DailyWorkRequest> requests, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.saveDailyWorks(requests, user);
    }

    @PostMapping("/projects")
    public void saveProjects(@RequestBody List<ProjectRequest> requests, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.saveProjects(requests, user);
    }

    @PostMapping("/routine-jobs")
    public void saveRoutineJobs(@RequestBody List<RoutineJobRequest> requests, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.saveRoutineJobs(requests, user);
    }


    @PostMapping("/monthly-goals")
    public void saveMonthlyGoals(@RequestBody List<MonthlyGoalRequest> requests, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.saveMonthlyGoals(requests, user);
    }

}
