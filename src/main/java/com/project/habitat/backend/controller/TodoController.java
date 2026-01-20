package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.MyTodosDto;
import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
import com.project.habitat.backend.service.TodoService;
import com.project.habitat.backend.service.security.AppUserDetails;
import org.apache.coyote.Response;
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
        List<TodoDto> incompleteTodos = this.todoService.getIncompleteTodosForToday(appUserDetails.getUserId(), appUserDetails.getTimeZone());
        return ResponseEntity.ok(new ApiResponse(incompleteTodos));
    }

    @GetMapping("expired-todos")
    public ResponseEntity<ApiResponse> expiredTodos(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<TodoDto> expiredTodos = this.todoService.getExpiredTodos(appUserDetails.getUserId(), appUserDetails.getTimeZone());
        return ResponseEntity.ok(new ApiResponse(expiredTodos));
    }

    @PutMapping("start-todo")
    public ResponseEntity<ApiResponse> startTodo(@RequestParam Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        TodoDto ongoingTodo = todoService.startTodo( id, appUserDetails.getUserId());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_STARTED, ongoingTodo));
    }

    @PutMapping("pause-todo")
    public ResponseEntity<ApiResponse> pauseTodo(@RequestParam Integer id, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        TodoDto ongoingTodo = todoService.pauseTodo( id, appUserDetails.getUserId());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_PAUSED, ongoingTodo));
    }

    @PutMapping("rate-todo")
    public ResponseEntity<ApiResponse> rateTodo(@RequestParam Integer id, @RequestParam Integer ratingValue, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        todoService.rateTodo(id, ratingValue, appUserDetails.getUserId());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_RATED));
    }

    @GetMapping("my-todos")
    public ResponseEntity<ApiResponse> myTodos(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        MyTodosDto myTodos = todoService.getMyTodos(appUserDetails.getUserId(), appUserDetails.getTimeZone());
        return ResponseEntity.ok(new ApiResponse(myTodos));
    }
}
