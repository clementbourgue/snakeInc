package org.snakeinc.snake.model;

import java.util.Random;

public class FoodFactory {

    private static final Random RANDOM = new Random();

    public static SnakeFood createRandomFoodInCell(Cell cell) {
        boolean appleOrBroccoli = RANDOM.nextBoolean(); // true => Apple, false => Broccoli

        if (appleOrBroccoli) {
            boolean poisoned = RANDOM.nextBoolean();
            Apple apple = new Apple(poisoned);
            cell.addFood(apple);
            return apple;
        } else {
            boolean steamed = RANDOM.nextBoolean();
            Broccoli broccoli = new Broccoli(steamed);
            cell.addFood(broccoli);
            return broccoli;
        }
    }
}
