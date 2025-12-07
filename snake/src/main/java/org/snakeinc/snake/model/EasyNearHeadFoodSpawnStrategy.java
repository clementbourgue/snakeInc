package org.snakeinc.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.snakeinc.snake.GameParams;

public class EasyNearHeadFoodSpawnStrategy implements FoodSpawnStrategy {

    private final Random random = new Random();

    @Override
    public Cell chooseCell(Grid grid, Snake snake) {
        Cell head = snake.getHead();
        int hx = head.getX();
        int hy = head.getY();

        List<Cell> candidates = new ArrayList<>();

        // On regarde dans un carré autour de la tête (distance <= 2)
        int radius = 2;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = hx + dx;
                int y = hy + dy;
                if (x < 0 || x >= GameParams.TILES_X || y < 0 || y >= GameParams.TILES_Y) continue;
                Cell c = grid.getTile(x, y);
                if (!c.containsASnake() && !c.containsFood()) {
                    candidates.add(c);
                }
            }
        }

        if (!candidates.isEmpty()) {
            return candidates.get(random.nextInt(candidates.size()));
        }

        // Si aucune cellule libre près de la tête → on tombe back sur la stratégie Random
        return new RandomFoodSpawnStrategy().chooseCell(grid, snake);
    }

    @Override
    public String getName() {
        return "EasyNearHead";
    }
}
