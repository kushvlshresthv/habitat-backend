package com.project.habitat.backend.dto;

import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.TodoType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
public class TodoDto {
    Integer id;
    String description;
    String status;
    LocalDate deadlineDate;
    Integer estimatedCompletionTimeMinutes;
    Integer totalElapsedSeconds;
    Instant lastResumedAt;
    HabitSummaryDto habit;
    String todoType;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.description = todo.getDescription();
        this.status = todo.getStatus().getStatus();
        this.deadlineDate = todo.getDeadlineDate();
        this.estimatedCompletionTimeMinutes = todo.getEstimatedCompletionTimeMinutes();
        this.totalElapsedSeconds = todo.getTotalElapsedSeconds();
        this.lastResumedAt = todo.getLastResumedAt();
        if(todo.getTodoType() == TodoType.HABIT)
            this.habit = new HabitSummaryDto(todo.getHabit());
        this.todoType = todo.getTodoType().toString();
    }
}
