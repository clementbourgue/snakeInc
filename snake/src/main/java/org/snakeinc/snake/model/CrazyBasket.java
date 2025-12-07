package org.snakeinc.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrazyBasket extends Basket implements SnakeObserver {

    private final FoodSpawnStrategy strategy;
    private final Random random = new Random();

    public CrazyBasket(Grid grid, FoodSpawnStrategy strategy) {
        super(grid);
        this.strategy = strategy;
    }

    @Override
    public void onSnakeMoved(Snake snake, Cell head) {
        // 1) Chercher des fruits proches de la tête
        List<Cell> candidates = new ArrayList<>();
        for (Cell cell : getGrid().getTiles().values()) {
            if (cell.containsFood()) {
                int dx = Math.abs(cell.getX() - head.getX());
                int dy = Math.abs(cell.getY() - head.getY());
                int distance = dx + dy; // distance de Manhattan

                // "proche" : distance <= 2 (et pas sur la tête elle-même)
                if (distance > 0 && distance <= 2) {
                    candidates.add(cell);
                }
            }
        }

        if (candidates.isEmpty()) {
            return;
        }

        // 2) De temps en temps seulement (par ex. 30% des mouvements)
        if (random.nextDouble() < 0.3) {
            Cell oldCell = candidates.get(random.nextInt(candidates.size()));
            SnakeFood food = oldCell.getFood();

            // On enlève l'ancienne nourriture
            removeFoodInCell(food, oldCell);

            // On choisit une nouvelle case avec la même stratégie que pour le spawn
            Cell newCell = strategy.chooseCell(getGrid(), snake);

            // Et on repose la nourriture
            addFood(newCell);
        }
    }
}
