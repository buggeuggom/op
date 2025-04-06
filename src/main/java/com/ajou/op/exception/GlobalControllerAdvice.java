package com.ajou.op.exception;

import com.ajou.op.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ajou.op.exception.ErrorCode.INVALID_EMAIL_OR_PASSWORD;
import static com.ajou.op.exception.ErrorCode.SERVERERROR;


@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception) {

        var errorResponse = ErrorResponse.builder()
                .resultCode(INVALID_EMAIL_OR_PASSWORD.getStatus().name())
                .message(INVALID_EMAIL_OR_PASSWORD.getMessage())
                .build();

        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OpApplicationException.class)
    public ResponseEntity<ErrorResponse> novelApplicationExceptionHandler(OpApplicationException e) {

        var body = ErrorResponse.builder()
                .resultCode(e.getErrorCode().name())
                .message(e.getMessage())
                .build();

        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {

        log.error(e.getMessage(), e);
        return ResponseEntity.status(SERVERERROR.getStatus())
                .body(ErrorResponse.builder()
                        .resultCode(SERVERERROR.name())
                        .message(SERVERERROR.getMessage()));
    }
}
