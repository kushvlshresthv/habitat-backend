package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {

}
