package org.snakeinc.snake.model;

import java.util.Random;
import lombok.Getter;
import org.snakeinc.snake.ui.GamePanel;

public class Apple {

    @Getter
    private Tile position;
    private final Random random;

    public Apple() {
        random = new Random();
        updateLocation();
    }

    public void updateLocation() {
        position = new Tile(random.nextInt(0, (GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE)),
                random.nextInt(0, (GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE)));

    }

}
