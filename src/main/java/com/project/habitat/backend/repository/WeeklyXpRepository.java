package com.project.habitat.backend.repository;


import com.project.habitat.backend.entity.WeeklyXp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyXpRepository extends PagingAndSortingRepository<WeeklyXp, Integer> {
    @Query("""
               SELECT w
               FROM WeeklyXp w
               WHERE w.weekStart = :weekStart
               ORDER BY w.xp DESC
            """)
    List<WeeklyXp> findLeaderboard(LocalDate weekStart, Pageable pageable);
}
