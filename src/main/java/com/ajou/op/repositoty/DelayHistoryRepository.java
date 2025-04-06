package com.ajou.op.repositoty;

import com.ajou.op.domain.DelayHistory;
import com.ajou.op.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DelayHistoryRepository extends JpaRepository<DelayHistory, Long> {

    List<DelayHistory> findAllByUserAndDescriptedAt(User user, LocalDate descriptedAt);
}
