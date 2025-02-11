package com.ajou.op.request.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetDailyWorkRequest {

    private LocalDate day;
}
