package org.snakeinc.snake.model;

public interface FoodEatenListener {
    void onFoodEaten(SnakeFood food, Cell cell);
}
