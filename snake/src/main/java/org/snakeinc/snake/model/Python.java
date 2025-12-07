package org.snakeinc.snake.model;

public final class Python extends Snake {

    public Python(FoodEatenListener listener, Grid grid) {
        super(listener, grid);
    }

    @Override
    protected int tailsToRemoveOnEat() {
        // Python : taille inchangée → on retire 1 segment (comme un mouvement normal)
        return 1;
    }
}
