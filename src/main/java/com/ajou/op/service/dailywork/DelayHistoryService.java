package com.ajou.op.service.dailywork;

import com.ajou.op.domain.DelayHistory;
import com.ajou.op.domain.user.User;
import com.ajou.op.exception.OpApplicationException;
import com.ajou.op.repositoty.DelayHistoryRepository;
import com.ajou.op.request.dailywork.DelayHistoryCreateRequest;
import com.ajou.op.request.dailywork.DelayHistoryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ajou.op.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DelayHistoryService {

    private final DelayHistoryRepository delayHistoryRepository;


    @Transactional
    public void create(DelayHistoryCreateRequest request, User user) {

        var entity = DelayHistory.builder()
                .description(request.getDescription())
                .descriptedAt(request.getDescriptedAt())
                .user(user)
                .build();

        delayHistoryRepository.save(entity);
    }

    @Transactional
    public void update(Long id, DelayHistoryUpdateRequest request, User user) {

        DelayHistory entity = delayHistoryRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(DELAY_NOT_FOUND));

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        entity.changeDescription(request.getDescription());

    }

    @Transactional
    public void delete(Long id, User user) {

        DelayHistory entity = delayHistoryRepository.findById(id)
                .orElseThrow(() -> new OpApplicationException(DELAY_NOT_FOUND));

        if (!entity.getUser().equals(user)) {
            throw new OpApplicationException(INVALID_PERMISSION);
        }

        delayHistoryRepository.delete(entity);
    }

}
