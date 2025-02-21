package com.ajou.op.controller.dailywork;

import com.ajou.op.domain.user.User;
import com.ajou.op.request.dailywork.RoutineJobRequest;
import com.ajou.op.service.dailywork.RoutineJobService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-works/routine-jobs")
public class RoutinJobController {

    private final RoutineJobService routineJobService;

    @PostMapping
    public void saveRoutineJobs(@RequestBody List<RoutineJobRequest> requests, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        routineJobService.saveRoutineJobs(requests, user);
    }


    @PutMapping("/{id}")
    public void updateRoutineJob(@PathVariable Long id, @RequestBody RoutineJobRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        routineJobService.updateRoutineJobs(id, request, user);
    }

    @DeleteMapping("/{id}")
    public void deleteRoutineJob(@PathVariable Long id, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        routineJobService.deleteRoutineJob(id, user);
    }

}
