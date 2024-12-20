package com.ajou.op.repositoty;

import com.ajou.op.domain.MonitoringAbnormality;
import com.ajou.op.domain.QMonitoringAbnormality;
import com.ajou.op.domain.user.QUser;
import com.ajou.op.dto.MonitoringAbnormalityDto;
import com.ajou.op.response.PagingResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ajou.op.domain.QMonitoringAbnormality.*;
import static com.ajou.op.domain.user.QUser.*;

@RequiredArgsConstructor
public class MonitoringAbnormalityRepositoryImpl implements MonitoringAbnormalityRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PagingResponse<MonitoringAbnormality> findAllCustom() {

        long totalCount = jpaQueryFactory.select(monitoringAbnormality.count())
                .from(monitoringAbnormality)
                .fetchFirst();

        List<MonitoringAbnormality> monitoringAbnormalities = jpaQueryFactory.select(monitoringAbnormality).from(monitoringAbnormality)
                .leftJoin(user)
                .on(user.eq(monitoringAbnormality.user))
                .orderBy(monitoringAbnormality.createdAt.desc())
                .fetch();

        return PagingResponse.<MonitoringAbnormality>builder()
                .totalCount(totalCount)
                .items(monitoringAbnormalities)
                .build();
    }
}
