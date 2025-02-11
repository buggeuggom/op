package com.ajou.op.repositoty.dailywork;

import com.ajou.op.domain.dailywork.DailyWork;
import com.ajou.op.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DailyWorkRepository extends JpaRepository<DailyWork, Long> {
    // 특정 사용자의 특정 날짜의 일일 작업들을 조회
    List<DailyWork> findByUserAndWorkDay(User user, LocalDate workDay);
}
