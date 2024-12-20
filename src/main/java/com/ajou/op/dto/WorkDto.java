package com.ajou.op.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WorkDto {

    private Long id;
    private String memo;
    private String workerName;
    private LocalDateTime createdAt;


    @Builder
    private WorkDto(Long id, String memo, String workerName, LocalDateTime createdAt) {
        this.id = id;
        this.memo = memo;
        this.workerName = workerName;
        this.createdAt = createdAt;
    }


}
