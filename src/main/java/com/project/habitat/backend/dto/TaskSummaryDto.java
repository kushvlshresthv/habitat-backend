package com.project.habitat.backend.dto;

import com.project.habitat.backend.entity.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TaskSummaryDto {
    Integer id;
    String description;
    String status;
    LocalDate deadlineDate;
    Integer estimatedCompletionTimeMinutes;
    Integer totalElapsedSeconds;
    LocalTime lastResumedAt;

    public TaskSummaryDto(Todo todo) {
        this.id = todo.getId();
        this.description = todo.getDescription();
        this.status = todo.getStatus().getStatus();
        this.deadlineDate = todo.getDeadlineDate();
        this.estimatedCompletionTimeMinutes = todo.getEstimatedCompletionTimeMinutes();
        this.totalElapsedSeconds = todo.getTotalElapsedSeconds();
        this.lastResumedAt = todo.getLastResumedAt();
    }
}
