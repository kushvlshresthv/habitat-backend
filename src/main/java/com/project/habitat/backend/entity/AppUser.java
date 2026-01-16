package com.project.habitat.backend.entity;

import com.project.habitat.backend.exception.ExceptionMessage;
import com.project.habitat.backend.exception.InvalidRatingException;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "app_users")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppUser {
    private static final int XP_PER_LEVEL = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer uid;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "lastname")
    String lastName;

    @Column(name = "username")
    String username;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "timezone", nullable = false, length = 64)
    private String timezone;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "xp", nullable = false)
    private Integer xp;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<WeeklyXp> weeklyXps;

    public ZoneId getZoneId() {
        return ZoneId.of(timezone);
    }

    public void addXp(int totalXpToAdd) {
        int newXp = this.xp + totalXpToAdd;
        while (newXp >= XP_PER_LEVEL) {
            newXp -= XP_PER_LEVEL;
            this.level++;
        }
        this.xp = newXp;
        LocalDate weekStart = getWeekStart(getZoneId());
        WeeklyXp weeklyXp =
                this.weeklyXps.stream().filter(weekly -> weekly.getWeekStart().isEqual(weekStart)).findFirst().orElse(null);

        if (weeklyXp == null) {
            weeklyXps.add(WeeklyXp.builder().weekStart(weekStart).xp(totalXpToAdd).user(this).build());
        } else {
            weeklyXp.setXp(weeklyXp.getXp() + totalXpToAdd);
        }
    }

    public LocalDate getWeekStart(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toLocalDate();
    }
}
