package org.snakeInc.api.repository;

import org.snakeInc.api.entities.SnakeType;

public interface ScoreStatsView {
    SnakeType getSnake();
    Integer getMin();
    Integer getMax();
    Double getAverage();
}
