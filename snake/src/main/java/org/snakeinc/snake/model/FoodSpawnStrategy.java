package org.snakeinc.snake.model;

public interface FoodSpawnStrategy {

    /**
     * Choisit une cellule où faire apparaître la nourriture.
     * Doit retourner une Cell non nulle, libre de serpent et de nourriture.
     */
    Cell chooseCell(Grid grid, Snake snake);

    /**
     * Nom de la stratégie (utile pour debug/logs).
     */
    String getName();
}
