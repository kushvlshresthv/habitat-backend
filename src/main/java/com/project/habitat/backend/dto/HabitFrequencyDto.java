package com.project.habitat.backend.dto;

import lombok.Getter;

@Getter
public class HabitFrequencyDto {
    Integer day;
    Integer durationMinutes;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HabitFrequencyDto target) {
            if(target == this) {
                return true;
            }
            return this.day.equals(target.day);
        } else {
            return false;
        }
    }
}
