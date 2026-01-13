package com.project.habitat.backend.entity;

import lombok.Getter;

@Getter
public class LeaderboardRowDto {
    Integer uid;
    String username;
    Integer xp;
    Integer level;
    public LeaderboardRowDto(WeeklyXp weeklyXp) {
        this.uid = weeklyXp.getUser().getUid();
        this.username = weeklyXp.getUser().getUsername();
        this.xp = weeklyXp.getXp();
        this.level = weeklyXp.getUser().getLevel();
    }
}
