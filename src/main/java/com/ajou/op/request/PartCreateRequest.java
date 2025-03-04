package com.ajou.op.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartCreateRequest {

    private String name;

    @Builder
    public PartCreateRequest(String name) {
        this.name = name;
    }
}
