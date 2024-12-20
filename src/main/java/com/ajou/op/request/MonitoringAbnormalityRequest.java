package com.ajou.op.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoringAbnormalityRequest {

    private Boolean smartDr;
    private Boolean smartServer;
    private Boolean pacsDr;
    private Boolean pacsServer;
    private Boolean tapeBackup;
    private Boolean diskBackup;
    private String status;

    @Builder
    public MonitoringAbnormalityRequest(Boolean smartDr, Boolean smartServer, Boolean pacsDr, Boolean pacsServer, Boolean tapeBackup, Boolean diskBackup, String status) {
        this.smartDr = smartDr;
        this.smartServer = smartServer;
        this.pacsDr = pacsDr;
        this.pacsServer = pacsServer;
        this.tapeBackup = tapeBackup;
        this.diskBackup = diskBackup;
        this.status = status;
    }
}
