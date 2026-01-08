package com.project.habitat.backend.entity;

import com.project.habitat.backend.enums.HabitStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "habits")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    HabitStatus status;
    @Column(name = "start_date")
    LocalDate startDate;
    @Column(name = "end_date")
    LocalDate endDate;
    @Column(name = "cheat_days")
    Integer cheatDays;
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private String uuid;
    @OneToMany(
            mappedBy = "habit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<HabitFrequency> frequencies = new HashSet<>();


    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Habit that = (Habit) o;
        return Objects.equals(uuid, that.uuid);
    }
}
