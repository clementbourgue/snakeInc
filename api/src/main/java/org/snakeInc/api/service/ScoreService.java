package org.snakeInc.api.service;

import org.snakeInc.api.dto.ScoreResponseDto;
import org.snakeInc.api.entities.Player;
import org.snakeInc.api.entities.Score;
import org.snakeInc.api.entities.SnakeType;
import org.snakeInc.api.repository.PlayerRepository;
import org.snakeInc.api.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.snakeInc.api.dto.ScoreStatsDto;
import org.snakeInc.api.repository.ScoreStatsView;
import org.snakeInc.api.dto.ScoresStatsResponse;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<ScoreResponseDto> getScores(SnakeType snake, Integer playerId) {
        List<Score> scores;

        if (snake != null && playerId != null) {
            scores = scoreRepository.findBySnakeAndPlayer_Id(snake, playerId);
        } else if (snake != null) {
            scores = scoreRepository.findBySnake(snake);
        } else if (playerId != null) {
            scores = scoreRepository.findByPlayer_Id(playerId);
        } else {
            scores = scoreRepository.findAll();
        }

        return scores.stream()
                .map(s -> new ScoreResponseDto(
                        s.getSnake().name(),
                        s.getScore(),
                        s.getPlayedAt(),
                        s.getPlayer().getId()
                ))
                .toList();
    }

    public ScoresStatsResponse getStatsForPlayer(int playerId) {

        playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        List<ScoreStatsView> rawStats = scoreRepository.findStatsByPlayer(playerId);

        List<ScoreStatsDto> stats = rawStats.stream()
                .map(s -> new ScoreStatsDto(
                        s.getSnake().name(),
                        s.getMin(),
                        s.getMax(),
                        s.getAverage()
                ))
                .toList();

        return new ScoresStatsResponse(playerId, stats);
    }
}
