package com.project.habitat.backend.service;

import com.project.habitat.backend.dto.HabitCreationDto;
import com.project.habitat.backend.dto.HabitFrequencyDto;
import com.project.habitat.backend.entity.AppUser;
import com.project.habitat.backend.entity.Habit;
import com.project.habitat.backend.entity.HabitFrequency;
import com.project.habitat.backend.entity.Todo;
import com.project.habitat.backend.enums.HabitStatus;
import com.project.habitat.backend.enums.TodoStatus;
import com.project.habitat.backend.enums.TodoType;
import com.project.habitat.backend.repository.AppUserRepository;
import com.project.habitat.backend.repository.HabitRepository;
import com.project.habitat.backend.repository.TodoRepository;
import com.project.habitat.backend.utils.EntityValidator;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class HabitService {

    HabitRepository habitRepository;
    EntityValidator entityValidator;
    TodoRepository todoRepository;
    AppUserRepository appUserRepository;



    HabitService(HabitRepository habitRepository, EntityValidator entityValidator, TodoRepository todoRepository, AppUserRepository appUserRepository) {
        this.habitRepository = habitRepository;
        this.entityValidator = entityValidator;
        this.todoRepository = todoRepository;
        this.appUserRepository = appUserRepository;
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

        for (HabitFrequencyDto habitFrequencyDto : habitCreationDto.getFrequencies()) {
            frequencies.add(HabitFrequency.builder().
                    day(DayOfWeek.of(habitFrequencyDto.getDay())).
                    durationMinutes(habitFrequencyDto.getDurationMinutes()).
                    habit(habit).
                    build()
            );
        }
        habit.setFrequencies(frequencies);
        habitRepository.save(habit);
        generateTodosForHabit(habit);
    }

    public void generateTodosForHabit(Habit habit) {

        LocalDate habitStartDate = habit.getStartDate().isAfter(LocalDate.now()) ? habit.getStartDate() : LocalDate.now();
        LocalDate habitEndDate = habit.getEndDate();
        int weekCounter = 0;
        List<Todo> habitTodos = new ArrayList<Todo>();

        boolean todoCreationFinished = false;

        Map<DayOfWeek, LocalDate> firstWeekDates = new HashMap<DayOfWeek, LocalDate>();
        while (!todoCreationFinished) {
            for (HabitFrequency frequency : habit.getFrequencies()) {
                //get the day of the week
                DayOfWeek dayOfWeek = frequency.getDay();

                //get the date for the week based on the weekCounter as well
                LocalDate nextHabitDate;
                if(weekCounter == 0) {
                    nextHabitDate = habitStartDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
                    firstWeekDates.put(dayOfWeek, nextHabitDate);
                } else {
                    LocalDate date = firstWeekDates.get(dayOfWeek);
                    nextHabitDate = date.plusWeeks(weekCounter);
                }

                if(nextHabitDate.isAfter(habitEndDate)) {
                    todoCreationFinished = true;
                    break;
                }


                AppUser appUser = appUserRepository.findByUsername("username").get();

                Todo habitTodo = Todo.builder().
                        description(habit.getName()).
                        habit(habit).
                        todoType(TodoType.HABIT).
                        status(TodoStatus.NOT_STARTED).
                        deadlineDate(nextHabitDate).
                        estimatedCompletionTimeMinutes(frequency.getDurationMinutes()).
                        totalElapsedSeconds(0).
                        //remove AppUser and only store the user id
                        user(appUser).
                        build();

                habitTodos.add(habitTodo);
            }
            weekCounter++ ;
        }
        this.todoRepository.saveAll(habitTodos);
    }
}
