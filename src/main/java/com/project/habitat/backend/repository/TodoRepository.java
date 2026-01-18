package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("""
                SELECT t
                FROM Todo t
                WHERE t.createdBy = :uid
                            AND t.status = 'COMPLETED'
                AND t.completedAt >= :startDate
                AND t.completedAt <= :endDate
            """)
    List<Todo> getCompletedTodosBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("uid") Integer uid
    );

    @Query("""
                SELECT t
                FROM Todo t
                WHERE t.createdBy =: uid
                  AND (t.status = 'NOT_STARTED' OR t.status='IN_PROGRESS' OR t.status='PAUSED')
            """)
    public List<Todo> getIncompleteTodos(Integer uid);

    @Query("""
            SELECT t
            FROM Todo t
            WHERE t.createdBy = :uid
              AND (t.status = 'NOT_STARTED' OR t.status='IN_PROGRESS' OR t.status='PAUSED')
              AND t.deadlineDate = :today
            """)
    List<Todo> getIncompleteTodosForDate(@Param("uid") Integer uid,
                                         @Param("today") LocalDate today);


    @Query("""
            SELECT t
            FROM Todo t
            WHERE t.createdBy = :uid
              AND (t.status = 'NOT_STARTED' OR t.status='IN_PROGRESS' OR t.status='PAUSED')
              AND t.deadlineDate < :today
            """)
    List<Todo> getExpiredTodos(@Param("uid") Integer uid,
                               @Param("today") LocalDate today);


    @Query("""
                            SELECT t
                            FROM Todo t
                            WHERE t.createdBy = :uid
                              AND t.status = 'IN_PROGRESS'
            """)

    public List<Todo> getOngoingTodo(Integer uid);

    @Query("""
                SELECT t
                FROM Todo t
                WHERE t.createdBy = :uid
                  AND t.id = :todoId
            """)

    public Optional<Todo> getUserTodoById(Integer todoId, Integer uid);


    @Query("""
            SELECT COUNT(t)
            FROM Todo t
            WHERE t.status = 'COMPLETED'
              AND t.completedAt >= :startOfDay
              AND t.completedAt < :endOfDay
            """)
    Long countCompletedTodosBetween(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
            SELECT t.estimatedCompletionTimeMinutes, t.todoRating
            FROM Todo t
            WHERE t.status = 'COMPLETED'
              AND t.completedAt >= :startOfDay
              AND t.completedAt < :endOfDay
            """)
    List<Object[]> getEstimatedTimesAndRatingsBetween(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
            SELECT COUNT(t)
            FROM Todo t
            WHERE t.status = 'IN_PROGRESS'
            """)
    Long countOngoingTodos();
}
