package com.ajou.op.service;


import com.ajou.op.domain.Part;
import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import com.ajou.op.dto.security.UserDetailsImpl;
import com.ajou.op.dto.UserDto;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.PartRepository;
import com.ajou.op.repositoty.UserRepository;
import com.ajou.op.request.UserChangePasswordRequest;
import com.ajou.op.request.UserSignupRequest;
import com.ajou.op.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ajou.op.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PartRepository partRepository;

    public UserDto signup(UserSignupRequest request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(it -> {
            throw new OpApplicationException(DUPLICATED_EMAIL);
        });

        Part part = partRepository.findByName(request.getPartName())
                .orElseThrow(() -> new OpApplicationException(PART_NOT_FOUND));

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .part(part)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userData = userRepository.findByEmail(email)
                .orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));

        return new UserDetailsImpl(userData);

    }

    public List<UserResponse> findAllByUserRole(User user) {

        if(user.getRole().equals(UserRole.ADMIN)) {

            return userRepository.findAllByRoleOrderByName(UserRole.WORKER).stream()
                    .map(en -> UserResponse.builder()
                            .email(en.getEmail())
                            .name(en.getName())
                            .build())
                    .toList();
        }

        Part part = user.getPart();
        return  userRepository.findAllByRoleAndPartOrderByName(UserRole.WORKER, part).stream()
                .map(en -> UserResponse.builder()
                        .email(en.getEmail())
                        .name(en.getName())
                        .build())
                .toList();
    }

    public void findByEmailAndName(UserChangePasswordRequest request) {

         var user = userRepository.findByEmailAndName(request.getEmail(), request.getName())
                .orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));
    }

    public void changePassword(UserChangePasswordRequest request) {

        var user = userRepository.findByEmailAndName(request.getEmail(), request.getName())
                .orElseThrow(() -> new OpApplicationException(USER_NOT_FOUND));

        user.changePassword(passwordEncoder.encode(request.getPassword()));
    }

}