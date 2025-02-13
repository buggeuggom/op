package com.ajou.op.response.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String goals;

}
