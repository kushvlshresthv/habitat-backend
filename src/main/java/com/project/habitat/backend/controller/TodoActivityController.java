package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TodoCompletionActivityDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class TodoActivityController {

    private final TodoService todoService;

    public TodoActivityController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping("recent-completions")
    public ResponseEntity<ApiResponse> getRecenltyCompletedTodos(@RequestParam Integer pageNumber) {
        List<TodoCompletionActivityDto> recentTodos = this.todoService.getRecentlyCompletedTodos(pageNumber);
        return ResponseEntity.ok(new ApiResponse(recentTodos));
    }
}
