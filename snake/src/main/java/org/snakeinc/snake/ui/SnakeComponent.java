package org.snakeinc.snake.ui;

import java.awt.Color;
import java.awt.Graphics;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Tile;

@Data
@AllArgsConstructor
public class SnakeComponent implements Component {

    private Snake snake;

    public void draw(Graphics g) {
        for (Tile t : snake.getBody()) {
            g.setColor(Color.GREEN);
            t.drawRectangle(g);
        }
    }

}
