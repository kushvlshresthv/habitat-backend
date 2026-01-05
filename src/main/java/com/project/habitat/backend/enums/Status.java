package com.project.habitat.backend.enums;

import lombok.Getter;

@Getter
public enum Status {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    PAUSED("PAUSED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    ;

    final private String status;
    Status(String status) {
        this.status = status;
    }
}
