package com.project.habitat.backend.enums;

import lombok.Getter;

@Getter
public enum TodoType {
    PURE("PURE"),
    HABIT("HABIT"),
    ;

    final private String status;
    TodoType(String status) {
        this.status = status;
    }
}
