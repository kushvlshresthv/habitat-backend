package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.ActivityDto;
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

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    AppUserRepository appUserRepository;
    TodoRepository todoRepository;
    EntityValidator entityValidator;
    XpCalculationService xpCalculationService;

    TodoService(AppUserRepository appUserRepository, TodoRepository todoRepository, EntityValidator entityValidator, XpCalculationService xpCalculationService) {
        this.appUserRepository = appUserRepository;
        this.todoRepository = todoRepository;
        this.entityValidator = entityValidator;
        this.xpCalculationService = xpCalculationService;
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

        Long newTotalElapsedSeconds = Duration.between(retrievedTodo.getLastResumedAt(), Instant.now()).toSeconds()
                + retrievedTodo.getTotalElapsedSeconds();
        retrievedTodo.setTotalElapsedSeconds(newTotalElapsedSeconds.intValue());
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
        if (ratingValue < 0 || ratingValue > 10) {
            //throw IllegalRatingValueException
            throw new RuntimeException();
        }
        Optional<Todo> optionalTodo = todoRepository.getUserTodoById(todoId, uid);
        if (optionalTodo.isEmpty()) {
            //throw TodoNotAccessibleException
            throw new RuntimeException();
        }

        Todo targetTodo = optionalTodo.get();

        //update the newTotalElapsedSeconds
        Long newTotalElapsedSeconds = Duration.between(targetTodo.getLastResumedAt(), Instant.now()).toSeconds()
                + targetTodo.getTotalElapsedSeconds();
        targetTodo.setTotalElapsedSeconds((Integer) newTotalElapsedSeconds.intValue());
        targetTodo.setLastResumedAt(null);

        if (targetTodo.getTotalElapsedSeconds() < targetTodo.getEstimatedCompletionTimeMinutes() * 60) {
            //throw TodoNotYetCompletedException
            throw new RuntimeException();
        }

        targetTodo.setStatus(TodoStatus.COMPLETED);
        targetTodo.setCompletionDate(LocalDate.now(ZoneOffset.UTC));
        targetTodo.setTodoRating(TodoRating.fromScore(ratingValue));

        Optional<AppUser> appUserOptional = appUserRepository.findById(uid);
        if (appUserOptional.isEmpty()) throw new UserDoesNotExistException(ExceptionMessage.USER_DOES_NOT_EXIST);

        AppUser appUser = appUserOptional.get();
        Integer xpToAdd = xpCalculationService.calculateXp(targetTodo);

        appUser.addXp(xpToAdd);
        appUserRepository.save(appUser);
        todoRepository.save(targetTodo);
    }


    public List<ActivityDto> getAllCompletedTodosOfLast365Days(Integer uid) {

        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = today.minusDays(364); // inclusive 365 days

        List<Todo> completedTodos =
                todoRepository.getCompletedTodosBetween(startDate, today, uid);


        Map<LocalDate, Integer> xpPerDay =
                completedTodos.stream()
                        .collect(Collectors.groupingBy(
                                Todo::getCompletionDate,
                                Collectors.summingInt(xpCalculationService::calculateXp)
                        ));

        List<ActivityDto> activities = new ArrayList<>(365);

        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            ActivityDto activity = new ActivityDto();
            activity.setLocalDate(date);
            activity.setXp(xpPerDay.getOrDefault(date, 0));
            activities.add(activity);
        }

        return activities;
    }
}
