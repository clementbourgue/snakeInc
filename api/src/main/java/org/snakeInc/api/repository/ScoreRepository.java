package org.snakeInc.api.repository;

import org.snakeInc.api.entities.Score;
import org.snakeInc.api.entities.SnakeType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Integer> {

    List<Score> findAll();

    List<Score> findBySnake(SnakeType snake);

    List<Score> findByPlayer_Id(int playerId);

    List<Score> findBySnakeAndPlayer_Id(SnakeType snake, int playerId);
}
