package com.ajou.op.response;


import com.ajou.op.domain.Work;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WorkResponse {
    private Long id;

    private String memo;
    private String createdAt;
    private String workerName;


    private WorkResponse(Long id, String memo, LocalDateTime createdAt, String workerName) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.id = id;
        this.memo = memo;
        this.createdAt = createdAt.format(formatter);
        this.workerName = workerName;
    }

    public static WorkResponse fromEntity(Work work) {
        return new WorkResponse(work.getId(),
                work.getMemo(),
                work.getCreatedAt(),
                work.getWorkerName());
    }
}
