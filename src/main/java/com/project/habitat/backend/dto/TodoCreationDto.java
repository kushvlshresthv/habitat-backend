package com.project.habitat.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class TodoCreationDto {
    String description;
    Boolean isCompleted;
    LocalDateTime deadline;
    Integer expectedCompletionTimeMinutes;
}
