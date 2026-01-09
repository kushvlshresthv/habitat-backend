package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
import com.project.habitat.backend.service.TodoService;
import com.project.habitat.backend.service.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ApiResponse> incompleteTodos(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<TodoDto> incompleteTodos = this.todoService.getIncompleteTodosForToday(appUserDetails.getUsername(), appUserDetails.getTimeZone());
        return ResponseEntity.ok(new ApiResponse(incompleteTodos));
    }

    @GetMapping("expired-todos")
    public ResponseEntity<ApiResponse> expiredTodos(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<TodoDto> expiredTodos = this.todoService.getExpiredTodos(appUserDetails.getUsername(), appUserDetails.getTimeZone());
        return ResponseEntity.ok(new ApiResponse(expiredTodos));
    }

    @PutMapping("start-todo")
    public ResponseEntity<ApiResponse> startTodo(@RequestParam Integer id, @RequestParam String type, Authentication authentication) {
        TodoDto ongoingTodo = todoService.startTodo( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_STARTED, ongoingTodo));
    }

    @PutMapping("pause-todo")
    public ResponseEntity<ApiResponse> pauseTodo(@RequestParam Integer id, Authentication authentication) {
        TodoDto ongoingTodo = todoService.pauseTodo( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_PAUSED, ongoingTodo));
    }

    @PutMapping("todo-completed")
    public ResponseEntity<ApiResponse> todoCompleted(@RequestParam Integer id, Authentication authentication) {
        TodoDto completedTodo = todoService.todoCompleted( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_COMPLETED, completedTodo));
    }
}
