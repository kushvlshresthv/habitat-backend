package com.project.habitat.backend.controller;


import com.project.habitat.backend.dto.HabitCreationDto;
import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
import com.project.habitat.backend.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HabitController {

    private final HabitService habitService;

    HabitController(HabitService habitService) {
        this.habitService = habitService;
    }
    @PostMapping("habit")
    public ResponseEntity<ApiResponse> createNewHabit(@RequestBody HabitCreationDto habitCreationDto) {
        this.habitService.createNewHabit(habitCreationDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.HABIT_CREATED));
    }

}
