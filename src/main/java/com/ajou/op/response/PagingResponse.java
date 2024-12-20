package com.ajou.op.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PagingResponse<T> {

    private long totalCount;
    private List<T> items;


    @Builder
    private PagingResponse(long totalCount, List<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }
}
