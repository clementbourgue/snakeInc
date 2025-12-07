package org.snakeinc.snake.model;

public interface SnakeObserver {

    /**
     * Appelé à chaque fois que le serpent a terminé un déplacement.
     *
     * @param snake   le serpent qui a bougé
     * @param newHead la nouvelle cellule de la tête
     */
    void onSnakeMoved(Snake snake, Cell newHead);
}
