package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("""
                SELECT t
                FROM Todo t
                JOIN t.user u
                WHERE u.username = :username
                  AND (t.status = 'NOT_STARTED' OR t.status='IN_PROGRESS' OR t.status='PAUSED')
            """)
    public List<Todo> getIncompleteTodos(String username);

    @Query("""
                            SELECT t
                            FROM Todo t
                            JOIN t.user u
                            WHERE u.username = :username
                              AND t.status = 'IN_PROGRESS'
            """)

    public List<Todo> getOngoingTodo(String username);

    @Query("""
                SELECT t
                FROM Todo t
                JOIN t.user u
                WHERE u.username = :username
                  AND t.id = :todoId
            """)

    public Optional<Todo> getUserTodoById(Integer todoId, String username);
}
