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
public class DailyWork extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Comment("내용")
    String work;


    @Comment("한날")
    @Column(nullable = false, updatable = false)
    private LocalDate workDay;

    @Builder
    private DailyWork(User user, String work, LocalDate workDay) {
        this.user = user;
        this.work = work;
        this.workDay = workDay;
    }

    public void changeWork(String work) {
        this.work = work;
    }
}
