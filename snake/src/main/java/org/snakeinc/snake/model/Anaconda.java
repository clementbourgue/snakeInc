package org.snakeinc.snake.model;

public final class Anaconda extends Snake {

    public Anaconda(FoodEatenListener listener, Grid grid) {
        super(listener, grid);
    }

    @Override
    protected int tailsToRemoveOnEat() {
        // Anaconda : taille +1 → on ne retire aucun segment de la queue
        return 0;
    }
}
