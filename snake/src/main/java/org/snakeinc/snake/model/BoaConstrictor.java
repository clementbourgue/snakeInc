package org.snakeinc.snake.model;

public final class BoaConstrictor extends Snake {

    public BoaConstrictor(FoodEatenListener listener, Grid grid) {
        super(listener, grid);
    }

    @Override
    protected int tailsToRemoveOnEat() {
        // BoaConstrictor : taille -1 → on retire 2 segments
        return 2;
    }
}
