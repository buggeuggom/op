package com.ajou.op.dto;

import com.ajou.op.domain.user.User;
import lombok.Getter;

@Getter
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String password;

    private UserDto(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static UserDto from(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword()
        );
    }
}