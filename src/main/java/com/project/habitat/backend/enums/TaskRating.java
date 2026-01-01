package com.project.habitat.backend.enums;

public enum TaskRating {
    NOT_TOUCHED(0),
    BARELY_STARTED(1),
    SLIGHTLY_ENGAGED(2),
    LOW_ENGAGEMENT(3),
    PARTIALLY_ENGAGED(4),
    MODERATELY_ENGAGED(5),
    FOCUSED(6),
    HIGHLY_FOCUSED(7),
    DEEPLY_ENGAGED(8),
    FULLY_ENGAGED(9),
    COMPLETELY_IMMERSED(10);

    private final int score;

    TaskRating(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
