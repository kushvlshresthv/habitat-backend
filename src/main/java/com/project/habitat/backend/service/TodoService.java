package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.TodoCreationDto;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.repository.AppUserRepository;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

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

        Integer userId = appUserRepository.getUserIdFromUsername(username);
        Todo todo = Todo.builder().
                uid(userId).
                description(todoCreationDto.getDescription()).
                isCompleted(todoCreationDto.getIsCompleted()).
                deadline(todoCreationDto.getDeadline()).
                expectedCompletionTimeMinutes(todoCreationDto.getExpectedCompletionTimeMinutes())
                .build();

        todoRepository.save(todo);
    }
}
