package org.snakeInc.api.repository;

import org.snakeInc.api.entities.Score;
import org.snakeInc.api.entities.SnakeType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Integer> {

    List<Score> findAll();

    List<Score> findBySnake(SnakeType snake);

    List<Score> findByPlayer_Id(int playerId);

    List<Score> findBySnakeAndPlayer_Id(SnakeType snake, int playerId);

    @Query("""
    select 
        s.snake as snake,
        min(s.score) as min,
        max(s.score) as max,
        avg(s.score) as average
    from Score s
    where s.player.id = :playerId
    group by s.snake
""")
    List<ScoreStatsView> findStatsByPlayer(@Param("playerId") int playerId);
}
