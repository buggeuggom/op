package com.ajou.op.repositoty;

import com.ajou.op.domain.Part;
import com.ajou.op.domain.user.User;
import com.ajou.op.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndName(String email, String name);



    List<User> findAllByRoleNotOrderByName(UserRole role);
    List<User> findAllByRoleAndPartOrderByName(UserRole role, Part part);

}
