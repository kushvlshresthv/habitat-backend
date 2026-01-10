package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.TodoRating;
import com.project.habitat.backend.enums.TodoStatus;
import com.project.habitat.backend.enums.TodoType;
import com.project.habitat.backend.exception.ExceptionMessage;
import com.project.habitat.backend.exception.UserDoesNotExistException;
import com.project.habitat.backend.repository.AppUserRepository;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
        if (user.isEmpty()) {
            throw new UserDoesNotExistException(ExceptionMessage.USER_DOES_NOT_EXIST);
        }
        Todo todo = Todo.builder().
                description(todoCreationDto.getDescription()).
                status(TodoStatus.NOT_STARTED).
                deadlineDate(todoCreationDto.getDeadlineDate()).
                estimatedCompletionTimeMinutes(todoCreationDto.getEstimatedCompletionTimeMinutes()).
                totalElapsedSeconds(0).
                lastResumedAt(null).
                todoType(TodoType.PURE)
                .build();
        todoRepository.save(todo);

    }

    public List<TodoDto> getIncompletedTodos(Integer uid) {
        List<Todo> incompleteTodos = todoRepository.getIncompleteTodos(uid);
        List<TodoDto> incompleteTodosDto = incompleteTodos.stream()
                .map(TodoDto::new)
                .toList();
        return incompleteTodosDto;
    }

    public List<TodoDto> getIncompleteTodosForToday(Integer uid, String timezone) {
        ZoneId userZoneId = ZoneId.of(timezone);
        LocalDate todayInUserTZ = LocalDate.now(userZoneId);
        List<Todo> incompleteTodos = todoRepository.getIncompleteTodosForDate(uid, todayInUserTZ);
        List<TodoDto> incompleteTodosDto = incompleteTodos.stream()
                .map(TodoDto::new)
                .toList();
        return incompleteTodosDto;
    }

    public TodoDto startTodo(Integer todoId, Integer uid) {
        List<Todo> ongoingTodos = todoRepository.getOngoingTodo(uid);
        if (!ongoingTodos.isEmpty()) {
            throw new RuntimeException();
            //TODO: handle this exception properly
        }

        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, uid);
        if (retrievedTodoOptional.isEmpty()) {
            throw new RuntimeException();
            //TODO: handle this exception properly
        }

        Todo retrievedTodo = retrievedTodoOptional.get();
        if (retrievedTodo.getStatus() == TodoStatus.IN_PROGRESS) {
            //TODO: return that todo is already in pprogress
        } else if (retrievedTodo.getStatus() == TodoStatus.COMPLETED) {
            //TODO: return that todo is comppleted and cannot be started
        } else if (retrievedTodo.getStatus() == TodoStatus.NOT_STARTED || retrievedTodo.getStatus() == TodoStatus.PAUSED) {
            retrievedTodo.setLastResumedAt(Instant.now());
            retrievedTodo.setStatus(TodoStatus.IN_PROGRESS);
            Todo savedTodo = todoRepository.save(retrievedTodo);
            return new TodoDto(savedTodo);
        }
        //A_TODO: throw unknown todo status exception
        throw new RuntimeException();
    }

    public TodoDto pauseTodo(Integer todoId, Integer uid) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, uid);
        if (retrievedTodoOptional.isEmpty()) {
            //TODO: throw exception
            throw new RuntimeException();
        }
        Todo retrievedTodo = retrievedTodoOptional.get();

        if (retrievedTodo.getStatus() != TodoStatus.IN_PROGRESS) {
            //TODO: return that todo cannot be paused
            throw new RuntimeException();
        }

        Integer newTotalElapsedSeconds = Duration.between(retrievedTodo.getLastResumedAt(), Instant.now()).toSecondsPart()
                + retrievedTodo.getTotalElapsedSeconds();
        retrievedTodo.setTotalElapsedSeconds(newTotalElapsedSeconds);
        retrievedTodo.setLastResumedAt(null);
        retrievedTodo.setStatus(TodoStatus.PAUSED);
        Todo savedTodo = todoRepository.save(retrievedTodo);
        return new TodoDto(savedTodo);
    }


    public List<TodoDto> getExpiredTodos(Integer uid, String timezone) {
        ZoneId userZoneId = ZoneId.of(timezone);
        LocalDate todayInUserTZ = LocalDate.now(userZoneId);
        List<Todo> expiredTodos = todoRepository.getExpiredTodos(uid, todayInUserTZ);
        List<TodoDto> expiredTodosDto = expiredTodos.stream()
                .map(TodoDto::new)
                .toList();
        return expiredTodosDto;
    }

    public void rateTodo(Integer todoId, Integer ratingValue, Integer uid) {
        if(ratingValue < 0 || ratingValue > 10) {
            //throw IllegalRatingValueException
            throw new RuntimeException();
        }
        Optional<Todo> optionalTodo = todoRepository.getUserTodoById(todoId, uid);
        if(optionalTodo.isEmpty()) {
            //throw TodoNotAccessibleException
            throw new RuntimeException();
        }

        Todo targetTodo = optionalTodo.get();

        if(targetTodo.getTotalElapsedSeconds() < targetTodo.getEstimatedCompletionTimeMinutes() * 60) {
            //throw TodoNotYetCompletedException
            throw new RuntimeException();
        }

        targetTodo.setStatus(TodoStatus.COMPLETED);
        targetTodo.setTodoRating(TodoRating.fromScore(ratingValue));
        todoRepository.save(targetTodo);
    }
}
