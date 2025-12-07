package org.snakeinc.snake.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import lombok.AllArgsConstructor;
import org.snakeinc.snake.model.Cell;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Anaconda;
import org.snakeinc.snake.model.Python;
import org.snakeinc.snake.model.BoaConstrictor;
import org.snakeinc.snake.model.SnakeFood;


@AllArgsConstructor
public class CellUI {

    private Cell cell;
    private int upperPixelX;
    private int upperPixelY;

    public void drawRectangle(Graphics g) {
        g.fillRect(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE.darker());
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
    }

    public void drawOval(Graphics g) {
        g.fillOval(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
    }

    public void draw(Graphics g) {

        if (cell.containsFood()) {
            SnakeFood food = cell.getFood();
            g.setColor(food.getColor());
            drawOval(g);
        }

        if (cell.containsASnake()) {
            Snake snake = cell.getSnake();

            Color snakeColor;
            if (snake instanceof Anaconda) {
                snakeColor = Color.GRAY;        // Anaconda : gris
            } else if (snake instanceof Python) {
                snakeColor = Color.GREEN;       // Python : vert
            } else if (snake instanceof BoaConstrictor) {
                snakeColor = Color.BLUE;        // Boa : bleu
            } else {
                snakeColor = Color.GREEN;       // fallback sécurité
            }

            g.setColor(snakeColor);
            drawRectangle(g);
        }
    }


}
