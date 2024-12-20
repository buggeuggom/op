package com.ajou.op.repositoty;


import com.ajou.op.domain.MonitoringAbnormality;
import com.ajou.op.response.PagingResponse;

public interface MonitoringAbnormalityRepositoryCustom {

    PagingResponse<MonitoringAbnormality> findAllCustom();
}
