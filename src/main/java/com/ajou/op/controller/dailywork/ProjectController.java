package com.ajou.op.controller.dailywork;

import com.ajou.op.domain.user.User;
import com.ajou.op.request.dailywork.ProjectRequest;
import com.ajou.op.service.dailywork.ProjectService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-works/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public void saveProjects(@RequestBody List<ProjectRequest> requests, Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        projectService.saveProjects(requests, user);
    }

    @DeleteMapping("/{id}")
    public void deleteMonthlyGoals(@PathVariable Long id,Authentication authentication) {
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        projectService.deleteProject(id, user);
    }

}
