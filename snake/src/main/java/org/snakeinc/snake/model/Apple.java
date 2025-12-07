package org.snakeinc.snake.model;

import java.awt.Color;
import lombok.Getter;

@Getter
public final class Apple implements SnakeFood {

    private final boolean poisoned;

    public Apple(boolean poisoned) {
        this.poisoned = poisoned;
    }

    @Override
    public int deltaLengthFor(Snake snake) {      // <-- AJOUTÉ
        // Règles pour APPLE (inchangées par rapport à ce qu'on avait déjà implicite) :
        // Anaconda : +1
        // Python   :  0
        // Boa      : -1
        if (snake instanceof Anaconda) {
            return +1;
        } else if (snake instanceof Python) {
            return 0;
        } else if (snake instanceof BoaConstrictor) {
            return -1;
        }
        throw new IllegalStateException("Unknown snake type: " + snake.getClass());
    }

    @Override
    public Color getColor() {                     // <-- AJOUTÉ
        // Pomme empoisonnée => violette
        return poisoned ? Color.MAGENTA : Color.RED;
    }

    @Override
    public int getPoints() {
        return poisoned ? 0 : 2;
    }
}
