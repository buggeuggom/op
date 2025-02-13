package com.ajou.op.repositoty;

import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
