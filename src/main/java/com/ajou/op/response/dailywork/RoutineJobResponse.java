package com.ajou.op.response.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RoutineJobResponse {

    private Long id;
    private String goals;
}
