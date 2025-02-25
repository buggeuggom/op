package com.ajou.op.controller.dailywork;

import com.ajou.op.domain.user.User;
import com.ajou.op.request.dailywork.montlyGoals.MonthlyGoalChangeRequest;
import com.ajou.op.request.dailywork.montlyGoals.MonthlyGoalCreateRequest;
import com.ajou.op.service.dailywork.MonthlyGoalService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-works/monthly-goals")
public class MonthlyGoalController {

    private final MonthlyGoalService monthlyGoalService;

    @PostMapping()
    public void saveMonthlyGoals(@RequestBody List<MonthlyGoalCreateRequest> requests, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        monthlyGoalService.saveMonthlyGoals(requests, user);
    }


    @PutMapping("/{id}")
    public void updateMonthlyGoal(@PathVariable Long id, @RequestBody MonthlyGoalChangeRequest requests, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        monthlyGoalService.updateMonthlyGoals(id, requests, user);
    }

    @DeleteMapping("/{id}")
    public void deleteMonthlyGoals(@PathVariable Long id, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        monthlyGoalService.deletesMonthlyGoal(id, user);
    }

}
