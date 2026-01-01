package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TodoController {

    TodoService todoService;
    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("new-todo")
    public ResponseEntity<ApiResponse> createNewTodo(@RequestBody TodoCreationDto todoCreationDto, Authentication authentication) {
        String username = authentication.getName();
        this.todoService.createNewTodo(todoCreationDto, username);
        return ResponseEntity.ok(new ApiResponse());
    }

}
