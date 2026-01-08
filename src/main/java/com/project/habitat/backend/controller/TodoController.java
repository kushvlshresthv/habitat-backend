package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.TaskSummaryDto;
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

    @GetMapping("incomplete-tasks")
    public ResponseEntity<ApiResponse> createNewTodo(Authentication authentication) {
        List<TaskSummaryDto> incompleteTasks = this.todoService.getIncompletedTasks(authentication.getName());
        return ResponseEntity.ok(new ApiResponse(incompleteTasks));
    }

    @PutMapping("start-task")
    public ResponseEntity<ApiResponse> startTask(@RequestParam Integer id, @RequestParam String type, Authentication authentication) {
        TaskSummaryDto ongoingTask = todoService.startTask( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_STARTED, ongoingTask));
    }

    @PutMapping("pause-task")
    public ResponseEntity<ApiResponse> pauseTask(@RequestParam Integer id, Authentication authentication) {
        TaskSummaryDto ongoingTask = todoService.pauseTask( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_PAUSED, ongoingTask));
    }

    @PutMapping("task-completed")
    public ResponseEntity<ApiResponse> taskCompleted(@RequestParam Integer id, Authentication authentication) {
        TaskSummaryDto completedTask = todoService.taskCompleted( id, authentication.getName());
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.TODO_COMPLETED, completedTask));
    }
}
