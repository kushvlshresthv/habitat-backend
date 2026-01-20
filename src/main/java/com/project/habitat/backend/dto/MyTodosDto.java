package com.project.habitat.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyTodosDto {
    List<TodoDto> notStartedTodos;
    List<TodoDto> completedTodos;
    List<TodoDto> expiredTodos;
}
