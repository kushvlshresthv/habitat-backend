package com.project.habitat.backend.enums;

public enum TodoRating {
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

    TodoRating(int score) {
        this.score = score;
    }

    public static TodoRating fromScore(int score) {
        for (TodoRating rating : values()) {
            if (rating.score == score) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid TodoRating score: " + score);
    }

    public int getScore() {
        return score;
    }
}
