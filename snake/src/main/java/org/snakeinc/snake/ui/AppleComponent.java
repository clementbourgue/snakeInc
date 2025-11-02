package org.snakeinc.snake.ui;

import java.awt.Color;
import java.awt.Graphics;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.snakeinc.snake.model.Apple;

@Data
@AllArgsConstructor
public class AppleComponent implements Component {

    private Apple apple;

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        apple.getPosition().drawOval(g);
    }
}
