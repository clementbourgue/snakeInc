package org.snakeInc.api.dto;

import java.util.List;

public record ScoresResponse(
        List<ScoreResponseDto> scores
) {
}
