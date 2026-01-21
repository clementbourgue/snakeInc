package org.snakeInc.api.dto;

import java.time.LocalDateTime;

public record ScoreResponseDto(
        String snake,
        int score,
        LocalDateTime playedAt,
        int playerId
) {
}
