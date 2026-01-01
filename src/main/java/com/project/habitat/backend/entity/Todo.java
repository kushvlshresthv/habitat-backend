package com.project.habitat.backend.entity;

import com.project.habitat.backend.enums.TaskRating;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer uid;

    @Column(name="description")
    String description;

    @Column(name="is_completed")
    Boolean isCompleted;

    @Column(name="deadline")
    LocalDateTime deadline;

    @Column(name="expected_completion_time_minutes")
    Integer expectedCompletionTimeMinutes; // in minutes

    @Column(name="task_rating", nullable=true)
    @Enumerated(EnumType.ORDINAL)
    TaskRating taskRating;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "uid", nullable=false)
    AppUser user;
}
