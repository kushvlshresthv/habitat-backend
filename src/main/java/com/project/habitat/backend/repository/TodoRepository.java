package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("""
                SELECT t
                FROM Todo t
                WHERE t.createdBy = :uid
                            AND t.status = 'COMPLETED'
                AND t.completionDate >= :startDate
                AND t.completionDate <= :endDate
            """)
    List<Todo> getCompletedTodosBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
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
}
