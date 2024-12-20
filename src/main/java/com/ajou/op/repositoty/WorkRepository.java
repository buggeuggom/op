package com.ajou.op.repositoty;

import com.ajou.op.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByOrderByCreatedAtDesc();
}
