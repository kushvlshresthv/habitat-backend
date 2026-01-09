package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.HabitCreationDto;
import com.project.habitat.backend.dto.HabitFrequencyDto;
import com.project.habitat.backend.entity.Habit;
import com.project.habitat.backend.entity.HabitFrequency;
import com.project.habitat.backend.enums.HabitStatus;
import com.project.habitat.backend.repository.HabitRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Service
public class HabitService {

    HabitRepository habitRepository;
    EntityValidator entityValidator;
    HabitService(HabitRepository habitRepository, EntityValidator entityValidator) {
        this.habitRepository = habitRepository;
        this.entityValidator = entityValidator;
   }

    public void createNewHabit(HabitCreationDto habitCreationDto) {
        entityValidator.validate(habitCreationDto);

        Habit habit = Habit.builder().
                name(habitCreationDto.getName()).
                status(HabitStatus.NOT_STARTED).
                startDate(habitCreationDto.getStartDate()).
                endDate(habitCreationDto.getEndDate()).
                cheatDays(habitCreationDto.getCheatDays()).
                streak(0).
                build();

        Set<HabitFrequency> frequencies = new HashSet<>();

        for(HabitFrequencyDto habitFrequencyDto: habitCreationDto.getFrequencies()) {
            frequencies.add(HabitFrequency.builder().
                day(DayOfWeek.of(habitFrequencyDto.getDay())).
                   durationMinutes(habitFrequencyDto.getDurationMinutes()).
                    habit(habit).
                    build()
            );
        }
        habit.setFrequencies(frequencies);
        habitRepository.save(habit);
    }
}
