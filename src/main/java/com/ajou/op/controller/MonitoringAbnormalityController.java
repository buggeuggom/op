package com.ajou.op.controller;


import com.ajou.op.domain.user.User;
import com.ajou.op.request.MonitoringAbnormalityRequest;
import com.ajou.op.service.MonitoringAbnormalityService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoringabnormalities")
@RequiredArgsConstructor
public class MonitoringAbnormalityController { //TODO: 이사님 요청 없으면 무시
    private final MonitoringAbnormalityService monitoringAbnormalityService;

//    @GetMapping
//    public List<MonitoringAbnormalityResponse> findAll(){
//        return monitoringAbnormalityService.findAll().stream()
//                .map(MonitoringAbnormalityResponse::fromDto)
//                .toList();
//    }

    @PostMapping
    public void save(@RequestBody MonitoringAbnormalityRequest request, Authentication authentication){

        User user = ClassUtils.getSafeUserBySafeCast(authentication);
        monitoringAbnormalityService.save(request, user);
    }
}
