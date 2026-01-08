package com.project.habitat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "habit_frequency",
        uniqueConstraints = @UniqueConstraint(columnNames = {"habit_id", "day"})
)
public class HabitFrequency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Enumerated(EnumType.STRING)
    @Column(name="day_of_week", nullable = false)
    private DayOfWeek day;

    // Duration in minutes (null or 0 = no habit that day)
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
}
