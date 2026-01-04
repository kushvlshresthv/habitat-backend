package com.project.habitat.backend.dto;

import com.project.habitat.backend.entity.Todo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoSummaryDto {
    String description;
    LocalDate deadlineDate;
    Integer estimatedCompletionTimeMinutes;

    public TodoSummaryDto(Todo todo) {
        this.description = todo.getDescription();
        this.deadlineDate = todo.getDeadlineDate();
        this.estimatedCompletionTimeMinutes = todo.getEstimatedCompletionTimeMinutes();
    }
}
