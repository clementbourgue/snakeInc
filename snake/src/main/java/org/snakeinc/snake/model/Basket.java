package org.snakeinc.snake.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Basket {

    private Grid grid;
    private List<SnakeFood> foods;

    public Basket(Grid grid) {
        foods = new ArrayList<>();
        this.grid = grid;
    }

    // ⚠️ CHANGÉ : addFood suppose maintenant que "cell" n'est PAS null
    public void addFood(Cell cell) {
        SnakeFood food = FoodFactory.createRandomFoodInCell(cell);
        foods.add(food);
    }

    public void removeFoodInCell(SnakeFood food, Cell cell) {
        cell.removeFood();
        foods.remove(food);
    }

    public boolean isEmpty() {
        return foods.isEmpty();
    }

    // ⚠️ CHANGÉ : refill prend maintenant une stratégie et le snake
    private void refill(int nFoods, FoodSpawnStrategy strategy, Snake snake) {
        for (int i = 0; i < nFoods; i++) {
            Cell cell = strategy.chooseCell(grid, snake);  // stratégie choisit la case
            addFood(cell);
        }
    }

    // ⚠️ CHANGÉ : même chose ici
    public void refillIfNeeded(int nFoods, FoodSpawnStrategy strategy, Snake snake) {
        int missingFood = nFoods - foods.size();
        if (missingFood > 0) {
            refill(missingFood, strategy, snake);
        }
    }
}
