package com.ajou.op.domain.dailywork;

import com.ajou.op.domain.AuditingFields;
import com.ajou.op.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class MonthlyGoal  extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Comment("목표달")
    @Column(nullable = false, updatable = false)
    private LocalDate workDay;

    private String goals;

    @Builder
    private MonthlyGoal(User user, LocalDate workDay, String goals) {
        this.user = user;
        this.workDay = workDay;
        this.goals = goals;
    }
}
