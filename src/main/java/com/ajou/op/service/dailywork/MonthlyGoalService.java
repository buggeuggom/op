package com.ajou.op.service.dailywork;

import com.ajou.op.domain.dailywork.MonthlyGoal;
import com.ajou.op.domain.user.User;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.dailywork.MonthlyGoalRepository;
import com.ajou.op.request.dailywork.montlyGoals.ChangeMonthlyGoalRequest;
import com.ajou.op.request.dailywork.montlyGoals.CreateMonthlyGoalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.ajou.op.exception.ErrorCode.INVALID_PERMISSION;

@Service
@Transactional
@RequiredArgsConstructor
public class MonthlyGoalService {

    private final MonthlyGoalRepository monthlyGoalRepository;

    /**
     Creates
     */
    public void saveMonthlyGoals(List<CreateMonthlyGoalRequest> requests, User user) {
        List<MonthlyGoal> monthlyGoals = requests.stream().map((request) -> MonthlyGoal
                        .builder()
                        .user(user)
                        .goals(request.getGoals())
                        .workDay(getFirstDayOfMonth(request.getWorkDay()))
                        .build())
                .toList();

        monthlyGoalRepository.saveAll(monthlyGoals);
    }

    public void updateMonthlyGoals(Long id, ChangeMonthlyGoalRequest requests, User user) {
        MonthlyGoal entity = monthlyGoalRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(ErrorCode.MONTHLY_GOAL_NOT_FOUND));

        if(!entity.getUser().equals(user)){
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeGoals(requests.getGoals());
    }

    /**
     Delete
     */
    public void deletesMonthlyGoal(Long id, User user) {
        MonthlyGoal entity = monthlyGoalRepository.findById(id).orElseThrow(
                () -> new OpApplicationException(ErrorCode.MONTHLY_GOAL_NOT_FOUND)
        );

        if(!entity.getUser().equals(user)){
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        monthlyGoalRepository.delete(entity);
    }


    private LocalDate getFirstDayOfMonth(LocalDate localDate) {
        return localDate.withDayOfMonth(1);
    }
}
