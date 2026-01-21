package org.snakeInc.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.snakeInc.api.entities.Score;
import org.snakeInc.api.entities.SnakeType;
import org.snakeInc.api.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.snakeInc.api.dto.ScoresResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<Score> createScore(@Valid @RequestBody ScoreParams params) {

        Score created = scoreService.createScore(
                params.snake(),
                params.score(),
                params.playedAt(),
                params.playerId()
        );

        return ResponseEntity.ok(created);
    }

    public record ScoreParams(
            @NotNull(message = "snake is required")
            SnakeType snake,

            @Min(value = 0, message = "score must not be negative")
            int score,

            @NotNull(message = "playedAt is required")
            LocalDateTime playedAt,

            @Min(value = 1, message = "playerId must be >= 1")
            int playerId
    ) {}

    @GetMapping
    public ResponseEntity<ScoresResponse> getScores(
            @RequestParam(required = false) SnakeType snake,
            @RequestParam(required = false, name = "player") Integer playerId
    ) {
        return ResponseEntity.ok(new ScoresResponse(scoreService.getScores(snake, playerId)));
    }
}
