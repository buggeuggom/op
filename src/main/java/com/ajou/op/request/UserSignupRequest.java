package com.ajou.op.request;

import com.ajou.op.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.ajou.op.domain.user.UserRole.*;

@Getter
@Setter
public class UserSignupRequest {

    private String email;
    private String name;
    private String password;
    private UserRole role = OP;

    @Builder
    public UserSignupRequest(String email, String name, String password, UserRole role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = OP;
    }
}