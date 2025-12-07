package org.snakeinc.snake.model;

import java.awt.Color;

public sealed interface SnakeFood permits Apple, Broccoli {

    /**
     * Variation de taille finale du serpent après avoir mangé
     * (ex : +1, 0, -2, -3, etc.).
     */
    int deltaLengthFor(Snake snake);

    /** Couleur d’affichage de cet aliment. */
    Color getColor();

    /** Nombre de points obtenus en mangeant Apple ou Broccoli
     * Apple : +2 (0 if poisoned)
     * Broccoli : +1 (0 if steamed)
     */
    int getPoints();
}
