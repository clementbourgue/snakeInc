package org.snakeinc.snake.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Data
@EqualsAndHashCode
public class Cell {

    @Getter
    private int x;

    @Getter
    private int y;

    Snake snake;
    SnakeFood food;

    protected Cell(int x, int y) {
        setX(x);
        setY(y);
    }

    public void addFood(SnakeFood food) {        // <-- CHANGÉ : addApple -> addFood
        this.food = food;
    }

    public void addSnake(Snake snake) {
        this.snake = snake;
    }

    public void removeSnake() {
        this.snake = null;
    }

    public void removeFood() {
        this.food = null;
    }

    public boolean containsASnake() {
        return this.snake != null;
    }

    public boolean containsFood() {
        return this.food != null;
    }

    public SnakeFood getFood() {
        return this.food;
    }

}
