package com.ajou.op.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //401
    INVALID_EMAIL_OR_PASSWORD(UNAUTHORIZED, "Invalid email or password"),
    INVALID_PERMISSION(UNAUTHORIZED, "User has invalid permission"),


    //404
    USER_NOT_FOUND(NOT_FOUND, "User not founded"),


    //409
   DUPLICATED_EMAIL(CONFLICT, "Duplicated email"),

    //500
    SERVERERROR(INTERNAL_SERVER_ERROR, "Internal Server Error"),
    ;

    private final HttpStatus status;
    private final String message;
}

