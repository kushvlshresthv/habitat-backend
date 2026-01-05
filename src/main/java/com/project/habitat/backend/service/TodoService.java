package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.dto.TodoSummaryDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.Status;
import com.project.habitat.backend.exception.ExceptionMessage;
import com.project.habitat.backend.exception.UserDoesNotExistException;
import com.project.habitat.backend.repository.AppUserRepository;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    AppUserRepository appUserRepository;
    TodoRepository todoRepository;
    EntityValidator entityValidator;

    TodoService(AppUserRepository appUserRepository, TodoRepository todoRepository, EntityValidator entityValidator) {
        this.appUserRepository = appUserRepository;
        this.todoRepository = todoRepository;
        this.entityValidator = entityValidator;
    }

    public void createNewTodo(TodoCreationDto todoCreationDto, String username) {
        entityValidator.validate(todoCreationDto);
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserDoesNotExistException(ExceptionMessage.USER_DOES_NOT_EXIST);
        }
        Todo todo = Todo.builder().
                user(user.get()).
                description(todoCreationDto.getDescription()).
                status(Status.NOT_STARTED).
                deadlineDate(todoCreationDto.getDeadlineDate()).
                estimatedCompletionTimeMinutes(todoCreationDto.getEstimatedCompletionTimeMinutes()).
                totalElapsedSeconds(0).
                lastResumedAt(null)
                .build();
        todoRepository.save(todo);
    }

    public List<TodoSummaryDto> getIncompletedTodo(String username) {
        List<Todo> incompleteTodos =  todoRepository.getIncompleteTodos(username);
        List<TodoSummaryDto> incompleteTodoSummaries = incompleteTodos.stream()
                .map(TodoSummaryDto::new)
                .toList();
        return incompleteTodoSummaries;
    }

    public void startTodo(Integer todoId, String username) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById( todoId, username);
        if(retrievedTodoOptional.isEmpty()) {
            //throw exception
            return;
        }
        Todo retrievedTodo = retrievedTodoOptional.get();
        retrievedTodo.setLastResumedAt(LocalTime.now());
        retrievedTodo.setStatus(Status.IN_PROGRESS);
        todoRepository.save(retrievedTodo);
    }

    public void pauseTodo(Integer todoId, String username) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById( todoId, username);
        if(retrievedTodoOptional.isEmpty()) {
            //throw exception
            return;
        }
        Todo retrievedTodo = retrievedTodoOptional.get();

        Integer newTotalElapsedSeconds = LocalTime.now().getSecond();
        retrievedTodo.setTotalElapsedSeconds(newTotalElapsedSeconds);
        retrievedTodo.setLastResumedAt(null);
        retrievedTodo.setStatus(Status.PAUSED);
        todoRepository.save(retrievedTodo);
    }
}
