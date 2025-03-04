package com.ajou.op.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class PartResponse {

    private Integer id;
    private String name;

    @Builder
    public PartResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
