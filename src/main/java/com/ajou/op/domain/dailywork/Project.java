package com.ajou.op.domain.dailywork;

import com.ajou.op.domain.AuditingFields;
import com.ajou.op.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Project  extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Comment("시작일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    private LocalDate startedAt;
    @Comment("종료일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    private LocalDate endedAt;
    private String goals;

    @Builder
    private Project(User user, LocalDate startedAt, LocalDate endedAt, String goals) {
        this.user = user;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.goals = goals;
    }


    public void changeGoals(String goals) {
        this.goals = goals;
    }

}
