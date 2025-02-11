package com.ajou.op.response.dailywork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Builder
@AllArgsConstructor
public class DailyWorkResponse {

    private String work;

}
