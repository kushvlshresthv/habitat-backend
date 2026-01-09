package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.entity.Todo;
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
                user(user.get()).
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

    public List<TodoDto> getIncompletedTodos(String username) {
        List<Todo> incompleteTodos = todoRepository.getIncompleteTodos(username);
        List<TodoDto> incompleteTodosDto = incompleteTodos.stream()
                .map(TodoDto::new)
                .toList();
        return incompleteTodosDto;
    }

    public List<TodoDto> getIncompleteTodosForToday(String username, String timezone) {
        ZoneId userZoneId = ZoneId.of(timezone);
        LocalDate todayInUserTZ = LocalDate.now(userZoneId);
        List<Todo> incompleteTodos = todoRepository.getIncompleteTodosForDate(username, todayInUserTZ);
        List<TodoDto> incompleteTodosDto = incompleteTodos.stream()
                .map(TodoDto::new)
                .toList();
        return incompleteTodosDto;
    }

    public TodoDto startTodo(Integer todoId, String username) {
        List<Todo> ongoingTodos = todoRepository.getOngoingTodo(username);
        if (!ongoingTodos.isEmpty()) {
            throw new RuntimeException();
            //TODO: handle this exception properly
        }

        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, username);
        if (retrievedTodoOptional.isEmpty()) {
            throw new RuntimeException();
            //TODO: handle this exception properly
        }

        Todo retrievedTodo = retrievedTodoOptional.get();
        if (retrievedTodo.getStatus() == TodoStatus.IN_PROGRESS) {
            //TODO: return that todo is already in pprogress
        } else if (retrievedTodo.getStatus() == TodoStatus.CANCELLED) {
            //TODO: return that todo is cancelled and cannot be started
        } else if (retrievedTodo.getStatus() == TodoStatus.COMPLETED) {
            //TODO: return that todo is comppleted and cannot be started
        }

        retrievedTodo.setLastResumedAt(Instant.now());
        retrievedTodo.setStatus(TodoStatus.IN_PROGRESS);
        Todo savedTodo = todoRepository.save(retrievedTodo);
        return new TodoDto(savedTodo);
    }

    public TodoDto pauseTodo(Integer todoId, String username) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, username);
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

    public TodoDto todoCompleted(Integer todoId, String username) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, username);
        return null;
    }
}
