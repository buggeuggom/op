package com.ajou.op.service.dailywork;

import com.ajou.op.domain.dailywork.routine.DayOfWeekBit;
import com.ajou.op.domain.dailywork.routine.RoutineJob;
import com.ajou.op.domain.user.User;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.dailywork.RoutineJobRepository;
import com.ajou.op.request.dailywork.RoutineJobRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ajou.op.domain.dailywork.routine.DayOfWeekBit.*;
import static com.ajou.op.exception.ErrorCode.*;
import static com.ajou.op.exception.ErrorCode.INVALID_PERMISSION;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineJobService {

    private final RoutineJobRepository routineJobRepository;

    private static Set<DayOfWeekBit> allDays = new HashSet<>(List.of(new DayOfWeekBit[]{MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}));


    public void saveRoutineJobs(List<RoutineJobRequest> requests, User user) {
        List<RoutineJob> routinJobs = requests.stream().map((req) -> RoutineJob.builder()
                        .user(user)
                        .goals(req.getGoals())
                        .startedAt(req.getStartedAt())
                        .workingDays(
                                (req.getWorkingDays() == null || req.getWorkingDays().isEmpty()) ? allDays : req.getWorkingDays())
                        .endedAt(req.getEndedAt())
                        .build())
                .toList();

        routineJobRepository.saveAll(routinJobs);
    }

    public void updateRoutineJobs(Long id, RoutineJobRequest request, User user) {
        var entity = routineJobRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(ROUTINE_JOB_NOT_FOUND));

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeGoals(request.getGoals());
    }

    public void deleteRoutineJob(Long id, User user) {
        var entity = routineJobRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(ROUTINE_JOB_NOT_FOUND));

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        routineJobRepository.delete(entity);
    }
}
