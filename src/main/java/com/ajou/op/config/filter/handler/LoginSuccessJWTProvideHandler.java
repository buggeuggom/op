package com.ajou.op.config.filter.handler;

import com.ajou.op.domain.user.User;
import com.ajou.op.response.LoginResponse;
import com.ajou.op.utils.ClassUtils;
import com.ajou.op.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String secretKey;
    private final Long expiredTimeMs;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = ClassUtils.getSafeUserBySafeCast(authentication);
        String email = user.getEmail();
        String name = user.getName();

        String token = JwtUtils.generateAccessToken(email, secretKey, expiredTimeMs);

        log.info("로그인 성공. JWT 발급. email: {}" ,email);

        LoginResponse loginResponse = LoginResponse.builder()
                .token("Bearer " + token)
                .name(name)
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(SC_OK);
        objectMapper.writeValue(response.getWriter(), loginResponse);
    }
}
