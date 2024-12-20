package com.ajou.op.response;


import com.ajou.op.dto.MonitoringAbnormalityDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MonitoringAbnormalityResponse {

    private Long id;
    private Boolean smartDr;
    private Boolean smartServer;
    private Boolean pacsDr;
    private Boolean pacsServer;
    private Boolean tapeBackup;
    private Boolean diskBackup;
    private String status;
    private String userName;
    private String userEmail;
    private LocalDateTime createdAt;

    private MonitoringAbnormalityResponse(Long id, Boolean smartDr, Boolean smartServer, Boolean pacsDr, Boolean pacsServer, Boolean tapeBackup, Boolean diskBackup, String status, String userName, String userEmail, LocalDateTime createdAt) {
        this.id = id;
        this.smartDr = smartDr;
        this.smartServer = smartServer;
        this.pacsDr = pacsDr;
        this.pacsServer = pacsServer;
        this.tapeBackup = tapeBackup;
        this.diskBackup = diskBackup;
        this.status = status;
        this.userName = userName;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
    }

    public static MonitoringAbnormalityResponse fromDto(MonitoringAbnormalityDto dto) {
        return new MonitoringAbnormalityResponse(dto.getId(),
                dto.getSmartDr(),
                dto.getSmartServer(),
                dto.getPacsDr(),
                dto.getPacsServer(),
                dto.getTapeBackup(),
                dto.getDiskBackup(),
                dto.getStatus(),
                dto.getUserDto().getName(),
                dto.getUserDto().getEmail(),
                dto.getCreatedAt()
        );
    }
}
