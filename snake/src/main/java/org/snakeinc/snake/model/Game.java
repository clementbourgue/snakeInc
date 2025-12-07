package org.snakeinc.snake.model;

import java.util.Random;
import lombok.Getter;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;

@Getter
public class Game {

    private final Grid grid;
    private final CrazyBasket basket;
    private final Snake snake;
    private final FoodSpawnStrategy foodSpawnStrategy;    // <-- AJOUTÉ

    private int movesCount;
    private int foodsEatenCount;
    private int points;

    private static final int NB_FOOD_ON_GRID = 3;

    public Game() {
        grid = new Grid();

        // 1) Stratégie de spawn choisie aléatoirement
        foodSpawnStrategy = chooseRandomStrategy();

        // 2) CrazyBasket (pas encore lié au Snake)
        basket = new CrazyBasket(grid, foodSpawnStrategy);

        // 3) Snake avec listener qui utilise le basket + met à jour les stats
        snake = SnakeFactory.createRandomSnake(
                (food, cell) -> {
                    basket.removeFoodInCell(food, cell);   // SUPPRIME LA NOURRITURE
                    foodsEatenCount++;                     // +1 nourriture
                    points += food.getPoints();            // +points
                },
                grid
        );

        // 4) CrazyBasket observe maintenant les mouvements du Snake
        snake.addObserver(basket);

        // 5) Première nourriture
        basket.refillIfNeeded(NB_FOOD_ON_GRID, foodSpawnStrategy, snake);


        // éventuellement, pour debug :
        System.out.println("Food spawn strategy: " + foodSpawnStrategy.getName());

    }


    private FoodSpawnStrategy chooseRandomStrategy() {
        Random random = new Random();
        int i = random.nextInt(3);
        return switch (i) {
            case 0 -> new RandomFoodSpawnStrategy();
            case 1 -> new EasyNearHeadFoodSpawnStrategy();
            default -> new HardBorderFoodSpawnStrategy();
        };
    }

    public void iterate(char direction) throws OutOfPlayException, SelfCollisionException {
        snake.move(direction);
        movesCount++;
        basket.refillIfNeeded(NB_FOOD_ON_GRID, foodSpawnStrategy, snake);    // <-- CHANGÉ
    }
}
