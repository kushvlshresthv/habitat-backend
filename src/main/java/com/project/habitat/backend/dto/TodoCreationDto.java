package com.project.habitat.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TodoCreationDto {
    String description;
    LocalDate deadlineDate;
    Integer estimatedCompletionTimeMinutes;
}
