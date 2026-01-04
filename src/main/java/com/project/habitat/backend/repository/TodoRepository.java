package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("""
                SELECT t
                FROM Todo t
                JOIN t.user u
                WHERE u.username = :username
                  AND t.isCompleted = false
            """)
    public List<Todo> getIncompleteTodos(String username);
}
