package com.project.habitat.backend.controller;

import com.project.habitat.backend.entity.LeaderboardRowDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("weekly-leaderboards")
    public ResponseEntity<ApiResponse> getWeeklyLeaderBoard() {
        List<LeaderboardRowDto> leaderboardRows = leaderboardService.getLeaderBoard();
        return ResponseEntity.ok(new ApiResponse(leaderboardRows));
    }
}
