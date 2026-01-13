package com.project.habitat.backend.service;

import com.project.habitat.backend.entity.LeaderboardRowDto;
import com.project.habitat.backend.entity.WeeklyXp;
import com.project.habitat.backend.repository.WeeklyXpRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class LeaderboardService {
    private final WeeklyXpRepository weeklyXpRepository;
    public LeaderboardService(WeeklyXpRepository weeklyXpRepository) {
        this.weeklyXpRepository = weeklyXpRepository;
    }

    public List<LeaderboardRowDto> getLeaderBoard() {

        LocalDate weekStartDate = getWeekStartUTC();
        Pageable pageable = PageRequest.of(
                0,
                50
        );
        List<WeeklyXp> weeklyXps = weeklyXpRepository.findLeaderboard(weekStartDate, pageable);

        List<LeaderboardRowDto> leaderboardRows = weeklyXps.stream().map(LeaderboardRowDto::new).toList();

        return leaderboardRows;
    }

    public LocalDate getWeekStartUTC() {
        return ZonedDateTime.now(ZoneOffset.UTC)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toLocalDate();
    }
}
