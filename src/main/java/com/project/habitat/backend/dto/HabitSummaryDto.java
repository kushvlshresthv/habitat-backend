package com.project.habitat.backend.dto;

import com.project.habitat.backend.entity.Habit;
import lombok.Getter;

@Getter
public class HabitSummaryDto {
    Integer id;
    String name;
    Integer streak;

    HabitSummaryDto(Habit habit) {
        this.id = habit.getId();
        this.name = habit.getName();
        this.streak = habit.getStreak();
    }
}
