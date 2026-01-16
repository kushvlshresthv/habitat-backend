package com.project.habitat.backend.service;

import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.TodoStatus;
import com.project.habitat.backend.exception.TodoNotCompletedException;
import org.springframework.stereotype.Service;

@Service
public class XpCalculationService {
    private final int XP_MULTIPLIER = 2;
    private final float RATING_NORMALIZER = 10f;
    private final float TODO_COMPLETION_XP_MULTIPLIER = 0.2f;

    public Integer calculateXp(Todo todo) {
        if(todo.getStatus() != TodoStatus.COMPLETED) {
            throw new TodoNotCompletedException();
        }

        Integer todoCompletionTimeMinutes = todo.getEstimatedCompletionTimeMinutes();
        Integer rating = todo.getTodoRating().getScore();

        float earnedXp = (todoCompletionTimeMinutes * XP_MULTIPLIER * rating) / RATING_NORMALIZER;
        float todoCompletionXp = (todoCompletionTimeMinutes * TODO_COMPLETION_XP_MULTIPLIER);

        return (int) Math.ceil(earnedXp + todoCompletionXp);
    }
}
