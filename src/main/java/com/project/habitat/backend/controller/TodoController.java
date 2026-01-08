package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
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
    public ResponseEntity<ApiResponse> incompleteTodos(Authentication authentication) {
        List<TodoDto> incompleteTasks = this.todoService.getIncompletedTodos(authentication.getName());
        return ResponseEntity.ok(new ApiResponse(incompleteTasks));
    }

    @PutMapping("start-todo")
    public ResponseEntity<ApiResponse> startTodo(@RequestParam Integer id, @RequestParam String type, Authentication authentication) {
        TodoDto ongoingTask = todoService.startTodo( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_STARTED, ongoingTask));
    }

    @PutMapping("pause-task")
    public ResponseEntity<ApiResponse> pauseTodo(@RequestParam Integer id, Authentication authentication) {
        TodoDto ongoingTask = todoService.pauseTodo( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_PAUSED, ongoingTask));
    }

    @PutMapping("todo-completed")
    public ResponseEntity<ApiResponse> taskCompleted(@RequestParam Integer id, Authentication authentication) {
        TodoDto completedTask = todoService.todoCompleted( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_COMPLETED, completedTask));
    }
}
