package com.ajou.op.controller.dailywork;

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

    @GetMapping()
    public List<DailyWorkFromResponse> getDailyWorkForm(@ModelAttribute DailyWorkGetRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        return dailyworkService.getDailyWorkForm(request.getDay(), user, 6);
    }

    @GetMapping("/admin")
    public List<DailyWorkFromResponse> getDailyWorkFormAdmin(@ModelAttribute DailyWorkGetRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        return dailyworkService.getDailyWorkFormAdmin(request.getDay(), request.getEmail(), user);
    }

    @GetMapping("/admin/json")
    public List<DailyWorkFromResponse> getDailyWorkJSONFormAdmin(@ModelAttribute DailyWorkGetRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        return dailyworkService.getDailyWorkJSONFormAdmin(request.getEmail(), user);
    }


    @PostMapping("/daily-works")
    public void saveDailyWork(@RequestBody List<DailyWorkCreateRequest> requests, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.saveDailyWorks(requests, user);
    }

    @PutMapping("/daily-works/{id}")
    public void updateDailyWork(@PathVariable Long id, @RequestBody DailyWorkCreateRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.updateDailyWork(id, request, user);
    }


    @DeleteMapping("/daily-works/{id}")
    public void deleteDailyWork(@PathVariable Long id, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        dailyworkService.deleteDailyWork(id, user);
    }

}
