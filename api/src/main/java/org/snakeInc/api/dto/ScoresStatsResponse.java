package org.snakeInc.api.dto;

import java.util.List;

public record ScoresStatsResponse(
        int playerId,
        List<ScoreStatsDto> stats
) {}
