package com.ajou.op.request;

import com.ajou.op.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {

    private String email;
    private String name;
    
    private String password;


    @Builder
    public UserChangePasswordRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
