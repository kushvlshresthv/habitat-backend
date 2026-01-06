package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.TaskSummaryDto;
import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.Status;
import com.project.habitat.backend.exception.ExceptionMessage;
import com.project.habitat.backend.exception.UserDoesNotExistException;
import com.project.habitat.backend.repository.AppUserRepository;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
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
                status(Status.NOT_STARTED).
                deadlineDate(todoCreationDto.getDeadlineDate()).
                estimatedCompletionTimeMinutes(todoCreationDto.getEstimatedCompletionTimeMinutes()).
                totalElapsedSeconds(0).
                lastResumedAt(null)
                .build();
        todoRepository.save(todo);

    }

    public List<TaskSummaryDto> getIncompletedTasks(String username) {
        List<Todo> incompleteTodos = todoRepository.getIncompleteTodos(username);
        List<TaskSummaryDto> incompleteTasks = incompleteTodos.stream()
                .map(TaskSummaryDto::new)
                .toList();
        return incompleteTasks;
    }

    public TaskSummaryDto startTodo(Integer todoId, String username) {
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
        if (retrievedTodo.getStatus() == Status.IN_PROGRESS) {
            //TODO: return that task is already in pprogress
        } else if (retrievedTodo.getStatus() == Status.CANCELLED) {
            //TODO: return that task is cancelled and cannot be started
        } else if (retrievedTodo.getStatus() == Status.COMPLETED) {
            //TODO: return that task is comppleted and cannot be started
        }

        retrievedTodo.setLastResumedAt(Instant.now());
        retrievedTodo.setStatus(Status.IN_PROGRESS);
        Todo savedTodo = todoRepository.save(retrievedTodo);
        return new TaskSummaryDto(savedTodo);
    }

    public TaskSummaryDto pauseTodo(Integer todoId, String username) {
        Optional<Todo> retrievedTodoOptional = todoRepository.getUserTodoById(todoId, username);
        if (retrievedTodoOptional.isEmpty()) {
            //TODO: throw exception
            return null;
        }
        Todo retrievedTodo = retrievedTodoOptional.get();

        if (retrievedTodo.getStatus() != Status.IN_PROGRESS) {
            //TODO: return that task cannot be paused
        }

        Integer newTotalElapsedSeconds = Duration.between(Instant.now(), retrievedTodo.getLastResumedAt()).toSecondsPart()
                + retrievedTodo.getTotalElapsedSeconds();
        retrievedTodo.setTotalElapsedSeconds(newTotalElapsedSeconds);
        retrievedTodo.setLastResumedAt(null);
        retrievedTodo.setStatus(Status.PAUSED);
        Todo savedTodo = todoRepository.save(retrievedTodo);
        return new TaskSummaryDto(savedTodo);
    }
}
