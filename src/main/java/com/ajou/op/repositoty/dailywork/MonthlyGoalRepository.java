package com.ajou.op.repositoty.dailywork;

import com.ajou.op.domain.dailywork.MonthlyGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import com.ajou.op.domain.user.User;

public interface MonthlyGoalRepository extends JpaRepository<MonthlyGoal, Long> {
    // 특정 사용자의 특정 월의 목표들을 조회
    List<MonthlyGoal> findByUserAndWorkDay(User user, LocalDate workDay);
}
