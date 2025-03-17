package com.ajou.op.domain.dailywork.routine;

import com.ajou.op.domain.AuditingFields;
import com.ajou.op.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class RoutineJob  extends AuditingFields {

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


    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "working_days", nullable = false)
    @ColumnDefault(value = "63")
    private Set<DayOfWeekBit> workingDays;

    @Builder
    private RoutineJob(String goals, LocalDate startedAt, LocalDate endedAt, User user, Set<DayOfWeekBit> workingDays) {
        this.goals = goals;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.user = user;
        this.workingDays = workingDays != null ? workingDays : new HashSet<>();
    }

    public void changeGoals(String goals) {
        this.goals = goals;
    }

    public void updateWorkingDays(Set<DayOfWeekBit> workingDays) {
        this.workingDays = workingDays;
    }

    /**
     * 주어진 날짜가 이 루틴의 작업일인지 확인
     * @param date 확인할 날짜
     * @return 작업일이면 true, 아니면 false
     */
    public boolean isWorkingDay(LocalDate date) {
        DayOfWeekBit dayBit;
        try {
            dayBit = DayOfWeekBit.valueOf(date.getDayOfWeek().name());
        } catch (IllegalArgumentException e) {
            return false;
        }

        return workingDays.contains(dayBit);
    }

    /**
     * 작업 요일 목록 반환
     * @return 작업 요일 Set
     */
    public Set<DayOfWeekBit> getWorkingDays() {
        return workingDays != null ? new HashSet<>(workingDays) : new HashSet<>();
    }
}