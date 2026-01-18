package com.project.habitat.backend.controller;

import com.project.habitat.backend.dto.CommunitySummaryDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.service.CommunityService;
import com.project.habitat.backend.service.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("community-summary")
    public ResponseEntity<ApiResponse> getCommunitySummary() {
        CommunitySummaryDto summary = communityService.getCommunitySummary();
        return ResponseEntity.ok(new ApiResponse(summary));
    }
}
