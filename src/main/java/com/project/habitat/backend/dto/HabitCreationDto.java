package com.project.habitat.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class HabitCreationDto {
    String name;
    LocalDate startDate;
    LocalDate endDate;
    Integer cheatDays;
    Set<HabitFrequencyDto> frequencies;
}
