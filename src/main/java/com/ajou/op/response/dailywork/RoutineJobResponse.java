package com.ajou.op.response.dailywork;

import com.ajou.op.domain.dailywork.routine.DayOfWeekBit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class RoutineJobResponse {

    private Long id;
    private String goals;
}
