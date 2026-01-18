package com.project.habitat.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunitySummaryDto {
    Long totalTodoCompletedToday;
    Long totalXpEarnedToday;
    Long totalOngoingTodos;
}
