package com.ajou.op.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkRequest {

    private String memo;

    @Builder
    public WorkRequest(String memo) {
        this.memo = memo;
    }
}
