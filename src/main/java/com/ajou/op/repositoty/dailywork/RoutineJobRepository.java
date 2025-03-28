package com.ajou.op.repositoty.dailywork;

import com.ajou.op.domain.dailywork.routine.RoutineJob;
import com.ajou.op.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoutineJobRepository extends JpaRepository<RoutineJob, Long> {

    // 특정 사용자의 특정 날짜가 포함된 루틴 작업들을 조회
    List<RoutineJob> findByUserAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
        User user, 
        LocalDate date, 
        LocalDate sameDate
    );
}
