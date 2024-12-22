package com.ajou.op.service;


import com.ajou.op.domain.user.User;
import com.ajou.op.dto.security.UserDetailsImpl;
import com.ajou.op.dto.UserDto;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.UserRepository;
import com.ajou.op.request.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ajou.op.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(UserSignupRequest request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(it -> {
            throw new OpApplicationException(DUPLICATED_EMAIL);
        });

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userData = userRepository.findByEmail(email)
                .orElseThrow(()->new OpApplicationException(USER_NOT_FOUND));

        return new UserDetailsImpl(userData);

    }
}