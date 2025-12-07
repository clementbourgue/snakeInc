package org.snakeinc.snake.model;

import java.util.Random;
import org.snakeinc.snake.GameParams;

public final class SnakeFactory {

    private static final Random RANDOM = new Random();

    public static Snake createRandomSnake(FoodEatenListener listener, Grid grid) {
        Snake snake = switch (RANDOM.nextInt(3)) {
            case 0 -> new Anaconda(listener, grid);
            case 1 -> new Python(listener, grid);
            default -> new BoaConstrictor(listener, grid);
        };

        // taille initiale = 3
        while (snake.getSize() < 3) {
            int x = RANDOM.nextInt(GameParams.TILES_X);
            int y = RANDOM.nextInt(GameParams.TILES_Y);
            Cell cell = grid.getTile(x, y);
            if (!cell.containsASnake()) {
                cell.addSnake(snake);
                snake.getBody().add(cell); // ajout en queue
            }
        }

        return snake;
    }
}
