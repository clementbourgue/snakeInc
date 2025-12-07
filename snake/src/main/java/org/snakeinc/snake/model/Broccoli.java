package org.snakeinc.snake.model;

import java.awt.Color;
import lombok.Getter;

@Getter
public final class Broccoli implements SnakeFood {

    private final boolean steamed;   // brocoli vapeur ou non

    public Broccoli(boolean steamed) {
        this.steamed = steamed;
    }

    @Override
    public int deltaLengthFor(Snake snake) {
        // Règles données :
        // Anaconda + Brocoli  -> -2
        // Boa      + Brocoli  ->  0
        // Python   + Brocoli  -> -3
        if (snake instanceof Anaconda) {
            return -2;
        } else if (snake instanceof Python) {
            return -3;
        } else if (snake instanceof BoaConstrictor) {
            return 0;
        }
        throw new IllegalStateException("Unknown snake type: " + snake.getClass());
    }

    @Override
    public Color getColor() {
        // Tu peux ajuster les couleurs comme tu veux
        return steamed ? Color.ORANGE : Color.GREEN.darker();
    }

    @Override
    public int getPoints() {
        return steamed ? 0 : 1;
    }
}
