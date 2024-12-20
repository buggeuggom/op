package com.ajou.op.domain;


import com.ajou.op.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class MonitoringAbnormality extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Boolean smartDr;
    private Boolean smartServer;
    private Boolean pacsDr;
    private Boolean pacsServer;
    private Boolean tapeBackup;
    private Boolean diskBackup;
    private String status;
    @ManyToOne
    private User user;

    @Builder
    private MonitoringAbnormality(Boolean smartDr, Boolean smartServer, Boolean pacsDr, Boolean pacsServer, Boolean tapeBackup, Boolean diskBackup, String status, User user) {
        this.smartDr = smartDr;
        this.smartServer = smartServer;
        this.pacsDr = pacsDr;
        this.pacsServer = pacsServer;
        this.tapeBackup = tapeBackup;
        this.diskBackup = diskBackup;
        this.status = status;
        this.user = user;
    }
}
