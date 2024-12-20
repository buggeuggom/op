package com.ajou.op.controller;


import com.ajou.op.domain.user.User;
import com.ajou.op.request.WorkRequest;
import com.ajou.op.response.WorkResponse;
import com.ajou.op.service.WorkService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @GetMapping()
    public List<WorkResponse> findAll(){
        return workService.findAll();
    }

    @PostMapping()
    public void post(@RequestBody WorkRequest request, Authentication authentication){


        User user = ClassUtils.getSafeUserBySafeCast(authentication);
        workService.save(request, user);
    }
}
