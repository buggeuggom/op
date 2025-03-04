package com.ajou.op.controller;


import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import com.ajou.op.dto.UserDto;
import com.ajou.op.request.UserSignupRequest;
import com.ajou.op.response.user.UserResponse;
import com.ajou.op.service.UserService;
import com.ajou.op.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupRequest request) {

        UserDto signup = userService.signup(request);

        return signup.getName();
    }

    @GetMapping
    public List<UserResponse> getUsers(Authentication authentication) {

        User user = ClassUtils.getSafeUserBySafeCast(authentication);

        return userService.findAllByUserRole(user);
    }
}
