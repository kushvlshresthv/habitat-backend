package com.project.habitat.backend.entity;

import com.project.habitat.backend.exception.ExceptionMessage;
import com.project.habitat.backend.exception.InvalidRatingException;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "app_users")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppUser {
    private static final int XP_MULTIPLIER = 2;
    private static final int XP_PER_LEVEL = 1000;
    private static final float RATING_NORMALIZER = 10f;
    private static final float TODO_COMPLETION_XP_MULTIPLIER = 0.2f;

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

    public ZoneId getZoneId() {
        return ZoneId.of(timezone);
    }

    public void rewardXp(int minutes, int todoCompletionTimeMinutes, int rating) {
        if (minutes <= 0) return;
        if(todoCompletionTimeMinutes <= 0) return;
        if(rating < 0 || rating > 10) throw new InvalidRatingException(ExceptionMessage.INVALID_RATING);

        float earnedXp = (minutes * XP_MULTIPLIER * rating) /RATING_NORMALIZER;
        float todoCompletionXp =  (todoCompletionTimeMinutes * TODO_COMPLETION_XP_MULTIPLIER);
        int totalXpToAdd = (int) Math.ceil(earnedXp + todoCompletionXp);

        if((this.xp + totalXpToAdd) >= XP_PER_LEVEL) {
            this.xp = (this.xp + totalXpToAdd) % XP_PER_LEVEL;
            this.level++;
        } else {
            this.xp = this.xp + totalXpToAdd;
        }
    }
}
