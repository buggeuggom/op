package com.ajou.op.repositoty.dailywork;

import com.ajou.op.domain.dailywork.MonthlyGoal;
import com.ajou.op.domain.dailywork.Project;
import com.ajou.op.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 특정 사용자의 특정 날짜가 포함된 프로젝트들을 조회
    List<Project> findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
        User user, 
        LocalDate date, 
        LocalDate sameDate
    );
}
