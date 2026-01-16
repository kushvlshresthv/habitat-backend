package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.ActivityDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.TodoService;
import com.project.habitat.backend.service.XpCalculationService;
import com.project.habitat.backend.service.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ActivityGraphController {
    TodoService todoService;
    XpCalculationService xpCalculationService;
    ActivityGraphController(TodoService todoService, XpCalculationService xpCalculationService) {
        this.todoService = todoService;
        this.xpCalculationService = xpCalculationService;
    }

    @GetMapping("activities")
    public ResponseEntity<ApiResponse> getActivitiesForLast365Days(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<ActivityDto> activities = todoService.getAllCompletedTodosOfLast365Days(appUserDetails.getUserId());
        return ResponseEntity.ok(new ApiResponse(activities));
    }
}
