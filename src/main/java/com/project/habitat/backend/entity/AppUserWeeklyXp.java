package com.project.habitat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_weekly_xp",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"uid", "week_start"})
        }
)
@Getter
public class AppUserWeeklyXp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private AppUser user;

    @Column(name = "week_start", nullable = false)
    private LocalDate weekStart; // Monday of the week

    @Column(name = "xp", nullable = false)
    private int xp;
}
