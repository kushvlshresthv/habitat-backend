package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.dto.TodoSummaryDto;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TodoController {

    TodoService todoService;
    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("todo")
    public ResponseEntity<ApiResponse> createNewTodo(@RequestBody TodoCreationDto todoCreationDto, Authentication authentication) {
        String username = authentication.getName();
        this.todoService.createNewTodo(todoCreationDto, username);
        return ResponseEntity.ok(new ApiResponse());
    }

    @GetMapping("incomplete-todos")
    public ResponseEntity<ApiResponse> createNewTodo(Authentication authentication) {
        List<TodoSummaryDto> incompleteTodoSummaries = this.todoService.getIncompletedTodo(authentication.getName());
        return ResponseEntity.ok(new ApiResponse(incompleteTodoSummaries));
    }
}
