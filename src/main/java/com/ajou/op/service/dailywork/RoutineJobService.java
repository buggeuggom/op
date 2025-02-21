package com.ajou.op.service.dailywork;

import com.ajou.op.domain.dailywork.RoutineJob;
import com.ajou.op.domain.user.User;
import com.ajou.op.exception.ErrorCode;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.dailywork.RoutineJobRepository;
import com.ajou.op.request.dailywork.RoutineJobRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ajou.op.exception.ErrorCode.*;
import static com.ajou.op.exception.ErrorCode.INVALID_PERMISSION;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineJobService {

    private final RoutineJobRepository routineJobRepository;


    public void saveRoutineJobs(List<RoutineJobRequest> requests, User user) {
        List<RoutineJob> routinJobs = requests.stream().map((req) -> RoutineJob.builder()
                        .user(user)
                        .goals(req.getGoals())
                        .startedAt(req.getStartedAt())
                        .endedAt(req.getEndedAt())
                        .build())
                .toList();

        routineJobRepository.saveAll(routinJobs);
    }

    public  void updateRoutineJobs(Long id,  RoutineJobRequest request, User user) {
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
