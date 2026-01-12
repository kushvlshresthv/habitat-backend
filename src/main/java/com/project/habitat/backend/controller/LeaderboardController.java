package com.project.habitat.backend.controller;

import com.project.habitat.backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class LeaderboardController {
    @GetMapping("weekly-leaderboards")
    public ResponseEntity<ApiResponse> getWeeklyLeaderBoard() {

        return null;
    }
}
