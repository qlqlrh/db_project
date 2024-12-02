package com.example.lightning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(
        name = "timetable",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "day_of_week", "start_time", "end_time"})
)
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timetable_id")
    private Long timetableId;

    // Many-to-One 관계로 User 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "day_of_week", nullable = true)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = true)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = true)
    private LocalTime endTime;

    // Constructors
    public Timetable() {
    }

    public Timetable(User user, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Enum for days of the week
    public enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }
}
