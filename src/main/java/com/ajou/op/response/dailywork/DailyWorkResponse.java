package com.ajou.op.response.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DailyWorkResponse {

    private Long id;
    private String work;

}
