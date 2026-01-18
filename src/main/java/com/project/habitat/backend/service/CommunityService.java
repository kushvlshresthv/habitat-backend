package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.CommunitySummaryDto;
import com.project.habitat.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class CommunityService {
    private final XpCalculationService xpCalculationService;
    private final TodoRepository todoRepository;

    public CommunityService(TodoRepository todoRepository, XpCalculationService xpCalculationService) {
        this.todoRepository = todoRepository;
        this.xpCalculationService = xpCalculationService;
    }

    public CommunitySummaryDto getCommunitySummary() {
        CommunitySummaryDto summary = new CommunitySummaryDto();
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDateTime todayStartofTheDay = today.atStartOfDay();
        LocalDateTime todayEndOfTheDay = today.plusDays(1).atStartOfDay();

        summary.setTotalTodoCompletedToday(todoRepository.countCompletedTodosBetween(todayStartofTheDay, todayEndOfTheDay));

        List<Object[]> completionTimesAndRatings = todoRepository.getEstimatedTimesAndRatingsBetween(todayStartofTheDay, todayEndOfTheDay);

        summary.setTotalXpEarnedToday(xpCalculationService.calculateXp(completionTimesAndRatings));

        summary.setTotalOngoingTodos(todoRepository.countOngoingTodos());
        return summary;
    }
}
