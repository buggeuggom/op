package com.ajou.op.repositoty;

import com.ajou.op.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Integer> {

    Optional<Part> findByName(String name);

    List<Part> findAllByOrderByName();
}
