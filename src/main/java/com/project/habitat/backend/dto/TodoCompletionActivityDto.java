package com.project.habitat.backend.dto;

import com.project.habitat.backend.enums.TodoRating;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoCompletionActivityDto {
    String username;
    Integer estimatedCompletionTimeMinutes;
    Integer rating;
    Integer xp;
    LocalDateTime completionTime;

    public TodoCompletionActivityDto(String username, Integer estimatedCompletionTimeMinutes, TodoRating rating, LocalDateTime completionTime)  {
        this.username = username;
        this.estimatedCompletionTimeMinutes = estimatedCompletionTimeMinutes;
        this.rating = rating.getScore();
        this.completionTime = completionTime;
    }
}
