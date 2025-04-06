package com.ajou.op.domain;


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
public class DelayHistory  extends AuditingFields{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Comment("딜레이 상황")
    private String description;
    @Comment("작성한 날")
    @Column(nullable = false, updatable = false)
    private LocalDate descriptedAt;


    @Builder
    private DelayHistory(User user, String description, LocalDate descriptedAt) {
        this.user = user;
        this.description = description;
        this.descriptedAt = descriptedAt;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
