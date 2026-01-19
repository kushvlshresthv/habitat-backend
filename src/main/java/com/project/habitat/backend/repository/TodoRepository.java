package com.project.habitat.backend.repository;

import com.project.habitat.backend.dto.TodoCompletionActivityDto;
import com.project.habitat.backend.dto.TodoDto;
import com.project.habitat.backend.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        WHERE t.createdBy.uid = :uid
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
        WHERE t.createdBy.uid = :uid
          AND t.status IN ('NOT_STARTED', 'IN_PROGRESS', 'PAUSED')
    """)
    List<Todo> getIncompleteTodos(@Param("uid") Integer uid);



    @Query("""
                SELECT new com.project.habitat.backend.dto.TodoDto(t)
                FROM Todo t
                WHERE t.createdBy.uid = :uid
                  AND t.status = 'NOT_STARTED'
            """)
    List<TodoDto> getNotStartedTodos(@Param("uid") Integer uid);


    @Query("""
        SELECT new com.project.habitat.backend.dto.TodoDto(t)
        FROM Todo t
        WHERE t.createdBy.uid = :uid
          AND t.status = 'COMPLETED'
    """)
    List<TodoDto> getCompletedTodos(@Param("uid") Integer uid);



    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.createdBy.uid = :uid
          AND t.status IN ('NOT_STARTED', 'IN_PROGRESS', 'PAUSED')
          AND t.deadlineDate = :today
    """)
    List<Todo> getIncompleteTodosForDate(
            @Param("uid") Integer uid,
            @Param("today") LocalDate today
    );


    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.createdBy.uid = :uid
          AND t.status IN ('NOT_STARTED', 'IN_PROGRESS', 'PAUSED')
          AND t.deadlineDate < :today
    """)
    List<Todo> getExpiredTodos(
            @Param("uid") Integer uid,
            @Param("today") LocalDate today
    );


    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.createdBy.uid = :uid
          AND t.status = 'IN_PROGRESS'
    """)
    List<Todo> getOngoingTodo(@Param("uid") Integer uid);


    @Query("""
        SELECT t
        FROM Todo t
        WHERE t.id = :todoId
          AND t.createdBy.uid = :uid
    """)
    Optional<Todo> getUserTodoById(
            @Param("todoId") Integer todoId,
            @Param("uid") Integer uid
    );


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


    @Query("""
        SELECT new com.project.habitat.backend.dto.TodoCompletionActivityDto(
            u.username,
            t.estimatedCompletionTimeMinutes,
            t.todoRating,
            t.completedAt
        )
        FROM Todo t
        JOIN t.createdBy u
        WHERE t.status = 'COMPLETED'
          AND t.completedAt IS NOT NULL
        ORDER BY t.completedAt DESC
    """)
    Page<TodoCompletionActivityDto> findRecentCompletedTodos(Pageable pageable);
}
