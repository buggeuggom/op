package com.ajou.op.repositoty;

import com.ajou.op.domain.MonitoringAbnormality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringAbnormalityRepository extends JpaRepository<MonitoringAbnormality, Long> {

    List<MonitoringAbnormality> findAllByOrderByCreatedAtDesc();
}
