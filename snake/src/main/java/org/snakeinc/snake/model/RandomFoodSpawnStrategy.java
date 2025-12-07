package org.snakeinc.snake.model;

import java.util.Random;
import org.snakeinc.snake.GameParams;

public class RandomFoodSpawnStrategy implements FoodSpawnStrategy {

    private final Random random = new Random();

    @Override
    public Cell chooseCell(Grid grid, Snake snake) {
        Cell cell;
        do {
            int x = random.nextInt(0, GameParams.TILES_X);
            int y = random.nextInt(0, GameParams.TILES_Y);
            cell = grid.getTile(x, y);
        } while (cell.containsASnake() || cell.containsFood());   // évite corps et autre food
        return cell;
    }

    @Override
    public String getName() {
        return "Random";
    }
}
