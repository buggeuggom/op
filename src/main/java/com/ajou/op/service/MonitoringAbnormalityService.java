package com.ajou.op.service;

import com.ajou.op.domain.MonitoringAbnormality;
import com.ajou.op.domain.user.User;
import com.ajou.op.repositoty.monitoringAbnormality.MonitoringAbnormalityRepository;
import com.ajou.op.request.MonitoringAbnormalityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonitoringAbnormalityService {

    private final MonitoringAbnormalityRepository monitoringAbnormalityRepository;

//    public List<MonitoringAbnormalityDto> findAll() {
//
//        return monitoringAbnormalityRepository.findAll().stream()
//                .map(entity-> MonitoringAbnormalityDto.fromEntity(entity))
//                .toList();
//    }

    @Transactional
    public void save(MonitoringAbnormalityRequest request, User user) {

        MonitoringAbnormality entity = MonitoringAbnormality.builder()
                .smartDr(request.getSmartDr())
                .smartServer(request.getSmartServer())
                .pacsDr(request.getPacsDr())
                .pacsServer(request.getPacsServer())
                .tapeBackup(request.getTapeBackup())
                .diskBackup(request.getDiskBackup())
                .user(user)
                .build();

        monitoringAbnormalityRepository.save(entity);
    }

}
