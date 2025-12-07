package org.snakeinc.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.snakeinc.snake.GameParams;

public class HardBorderFoodSpawnStrategy implements FoodSpawnStrategy {

    private final Random random = new Random();

    @Override
    public Cell chooseCell(Grid grid, Snake snake) {
        List<Cell> candidates = new ArrayList<>();

        int maxX = GameParams.TILES_X;
        int maxY = GameParams.TILES_Y;

        // On sélectionne les cellules sur l'anneau externe (x==0, y==0, x==maxX-1, y==maxY-1)
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                boolean nearBorder = (x == 0 || y == 0 || x == maxX - 1 || y == maxY - 1);
                if (!nearBorder) continue;

                Cell c = grid.getTile(x, y);
                if (!c.containsASnake() && !c.containsFood()) {
                    candidates.add(c);
                }
            }
        }

        if (!candidates.isEmpty()) {
            return candidates.get(random.nextInt(candidates.size()));
        }

        // Si aucun bord dispo → fallback sur random classique
        return new RandomFoodSpawnStrategy().chooseCell(grid, snake);
    }

    @Override
    public String getName() {
        return "HardBorder";
    }
}
