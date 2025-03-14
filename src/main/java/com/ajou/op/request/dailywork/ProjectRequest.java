package com.ajou.op.request.dailywork;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectRequest {


    private LocalDate startedAt;
    private LocalDate endedAt;
    private String goals;

    @Builder
    public ProjectRequest(LocalDate startedAt, LocalDate endedAt, String goals) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.goals = goals;
    }


}
