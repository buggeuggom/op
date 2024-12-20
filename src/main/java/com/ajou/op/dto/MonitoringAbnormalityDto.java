package com.ajou.op.dto;


import com.ajou.op.domain.MonitoringAbnormality;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MonitoringAbnormalityDto {

    private Long id;
    private Boolean smartDr;
    private Boolean smartServer;
    private Boolean pacsDr;
    private Boolean pacsServer;
    private Boolean tapeBackup;
    private Boolean diskBackup;
    private String status;
    private UserDto userDto;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private MonitoringAbnormalityDto(Long id, Boolean smartDr, Boolean smartServer, Boolean pacsDr, Boolean pacsServer, Boolean tapeBackup, Boolean diskBackup, String status, UserDto userDto, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.smartDr = smartDr;
        this.smartServer = smartServer;
        this.pacsDr = pacsDr;
        this.pacsServer = pacsServer;
        this.tapeBackup = tapeBackup;
        this.diskBackup = diskBackup;
        this.status = status;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    private MonitoringAbnormalityDto(MonitoringAbnormality entity) {
        this.id = entity.getId();
        this.smartDr = entity.getSmartDr();
        this.smartServer = entity.getSmartServer();
        this.pacsDr = entity.getPacsDr();
        this.pacsServer = entity.getPacsServer();
        this.tapeBackup = entity.getTapeBackup();
        this.diskBackup = entity.getDiskBackup();
        this.status = entity.getStatus();
        this.userDto = UserDto.from(entity.getUser());
    }

    public static MonitoringAbnormalityDto fromEntity(MonitoringAbnormality entity) {
        return new MonitoringAbnormalityDto(entity);
    }
}
