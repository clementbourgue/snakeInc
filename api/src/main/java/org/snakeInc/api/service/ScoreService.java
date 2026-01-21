package org.snakeInc.api.service;

import org.snakeInc.api.entities.Player;
import org.snakeInc.api.entities.Score;
import org.snakeInc.api.entities.SnakeType;
import org.snakeInc.api.repository.PlayerRepository;
import org.snakeInc.api.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerRepository playerRepository;

    public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
    }

    public Score createScore(SnakeType snake, int score, LocalDateTime playedAt, int playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        Score newScore = new Score(snake, score, playedAt, player);
        return scoreRepository.save(newScore);
    }
}
