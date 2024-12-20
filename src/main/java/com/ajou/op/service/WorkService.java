package com.ajou.op.service;

import com.ajou.op.domain.Work;
import com.ajou.op.domain.user.User;
import com.ajou.op.repositoty.WorkRepository;
import com.ajou.op.request.WorkRequest;
import com.ajou.op.response.WorkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkService {

    private final WorkRepository workRepository;

    @Transactional
    public void save(WorkRequest request, User user) {

        Work entity = Work.builder()
                .memo(request.getMemo())
                .workerName(user.getName())
                .build();

        workRepository.save(entity);
    }

    public List<WorkResponse> findAll() {
        return workRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(WorkResponse::fromEntity)
                .toList();
    }
}
