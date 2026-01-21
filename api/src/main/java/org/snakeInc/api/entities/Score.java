package org.snakeInc.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SnakeType snake;

    private int score;

    private LocalDateTime playedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    protected Score() { }

    public Score(SnakeType snake, int score, LocalDateTime playedAt, Player player) {
        this.snake = snake;
        this.score = score;
        this.playedAt = playedAt;
        this.player = player;
    }

    public Integer getId() { return id; }
    public SnakeType getSnake() { return snake; }
    public int getScore() { return score; }
    public LocalDateTime getPlayedAt() { return playedAt; }
    public Player getPlayer() { return player; }
}
