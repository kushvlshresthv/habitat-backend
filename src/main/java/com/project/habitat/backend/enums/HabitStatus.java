package com.project.habitat.backend.enums;
import lombok.Getter;

@Getter
public enum HabitStatus {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("FAILED"),
    ;

    final private String status;
    HabitStatus(String status) {
        this.status = status;
    }
}
