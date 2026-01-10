package com.project.habitat.backend.entity;

import com.project.habitat.backend.enums.TodoStatus;
import com.project.habitat.backend.enums.TodoRating;
import com.project.habitat.backend.enums.TodoType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    //using uuid for comparison instead of id because id is not assigned until the entity is stored in the database
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(name="description")
    String description;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    TodoStatus status ;

    @Column(name="deadline_date")
    LocalDate deadlineDate;

    @Column(name="estimated_completion_time_minutes")
    Integer estimatedCompletionTimeMinutes; // in minutes

    @Column(name="todo_rating", nullable=true)
    @Enumerated(EnumType.ORDINAL)
    TodoRating todoRating;

    @Column(name="total_elapsed_seconds")
    Integer totalElapsedSeconds;

    @Column(name="last_resumed_at", nullable = true)
    Instant lastResumedAt;


    @ManyToOne
    @JoinColumn(name="habit_id", referencedColumnName="id", nullable = true)
    Habit habit;

    @Column(name="type", nullable=false)
    @Enumerated(EnumType.STRING)
    TodoType todoType;

    @Column(name = "created_by", updatable = false, nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Todo that = (Todo) o;
        return Objects.equals(uuid, that.uuid);
    }
}
