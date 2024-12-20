package com.ajou.op.controller;


import com.ajou.op.dto.UserDto;
import com.ajou.op.request.UserSignupRequest;
import com.ajou.op.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
