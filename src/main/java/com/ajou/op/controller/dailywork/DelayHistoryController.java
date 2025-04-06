package com.ajou.op.controller.dailywork;


import com.ajou.op.domain.user.User;
import com.ajou.op.request.dailywork.DelayHistoryCreateRequest;
import com.ajou.op.request.dailywork.DelayHistoryUpdateRequest;
import com.ajou.op.service.dailywork.DelayHistoryService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delay")
public class DelayHistoryController {

    private final DelayHistoryService delayHistoryService;

    @PostMapping()
    public void create(@RequestBody DelayHistoryCreateRequest request, Authentication authentication){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        delayHistoryService.create(request, user);
    }

    @PutMapping("/{id}")
    public void updateAll(@RequestBody DelayHistoryUpdateRequest request, Authentication authentication, @PathVariable Long id){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        delayHistoryService.update(id,request, user);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable Long id){
        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        delayHistoryService.delete(id, user);
    }
}



