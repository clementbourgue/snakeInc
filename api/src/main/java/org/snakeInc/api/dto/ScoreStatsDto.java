package org.snakeInc.api.dto;

public record ScoreStatsDto(
        String snake,
        int min,
        int max,
        double average
) {}
