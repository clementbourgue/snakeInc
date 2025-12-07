package org.snakeinc.snake.model;

import java.util.ArrayList;

import lombok.Getter;
import org.snakeinc.snake.GameParams;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;

@Getter
public abstract sealed class Snake permits Anaconda, Python, BoaConstrictor {

    protected final ArrayList<Cell> body;
    protected final FoodEatenListener onFoodEatenListener;
    private final Grid grid;
    private SnakeHealthState healthState;
    private Direction currentDirection = Direction.R;
    private char lastRawDirection = 'R';
    private final java.util.List<SnakeObserver> observers = new java.util.ArrayList<>();


    public Snake(FoodEatenListener listener, Grid grid) {
        this.body = new ArrayList<>();
        this.onFoodEatenListener = listener;
        this.grid = grid;
        Cell head = grid.getTile(GameParams.SNAKE_DEFAULT_X, GameParams.SNAKE_DEFAULT_Y);
        head.addSnake(this);
        body.add(head);

        this.healthState = new GoodHealthState();
    }

    public int getSize() {
        return body.size();
    }

    public Cell getHead() {
        return body.get(0);
    }

    public void eat(SnakeFood food, Cell cell) {
        // Longueur AVANT de manger
        int previousLength = body.size();   // L

        System.out.println("=== EAT ===");
        System.out.println("Snake type   : " + this.getClass().getSimpleName());
        System.out.println("Food type    : " + food.getClass().getSimpleName());
        System.out.println("Length before: " + previousLength);

        // 1) On ajoute la nouvelle tête
        body.add(0, cell);                 // longueur = L + 1
        cell.addSnake(this);

        // 2) On notifie le basket / score
        onFoodEatenListener.onFoodEaten(food, cell);

        // 3) L’état de santé peut changer (good -> poisoned -> permanent, etc.)
        healthState = healthState.afterEating(this, food);

        // 4) Variation de taille voulue selon le couple (snake, food)
        int delta = food.deltaLengthFor(this);   // ex : +1, 0, -2, -3
        System.out.println("Delta length : " + delta);

        // Longueur VISEE APRÈS avoir mangé
        int targetLength = previousLength + delta;  // L + delta
        System.out.println("Target length: " + targetLength);

        if (targetLength <= 0) {
            System.out.println("=> Death (targetLength <= 0)");
            // Le serpent n'a plus de segments => mort de malnutrition
            for (Cell c : body) {
                c.removeSnake();
            }
            body.clear();
            throw new RuntimeException("Snake died of malnutrition");
        }

        // 5) Longueur actuelle après ajout de la tête : L + 1
        int currentLength = body.size();          // = L + 1

        // Nombre de segments à retirer pour atteindre exactement targetLength
        int toRemove = currentLength - targetLength; // (L+1) - (L+delta) = 1 - delta
        System.out.println("To remove    : " + toRemove);

        // 6) On retire les segments de queue un par un
        for (int i = 0; i < toRemove && !body.isEmpty(); i++) {
            int lastIndex = body.size() - 1;
            Cell tail = body.get(lastIndex);
            tail.removeSnake();
            body.remove(lastIndex);
        }

        System.out.println("Length after : " + body.size());
        System.out.println("=============\n");

        // Sécurité
        if (body.isEmpty()) {
            throw new RuntimeException("Snake died of malnutrition (unexpected empty body)");
        }
    }



    protected abstract int tailsToRemoveOnEat();


    public enum Direction {
        U(0,-1),
        D(0,1),
        R(1,0),
        L(-1,0);
        private final int dx;
        private final int dy;
        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        public int dx() {return this.dx;}
        public int dy() {return this.dy;}

        public static Direction fromChar(char c) {
            return switch (Character.toUpperCase(c)) {
                case 'U' -> U;
                case 'D' -> D;
                case 'L' -> L;
                case 'R' -> R;
                default -> throw new IllegalArgumentException("Direction invalide: " + c);
            };
        }
    }

    public void move(char direction) throws OutOfPlayException, SelfCollisionException {
        // 1) Met à jour la direction réelle UNIQUEMENT si le joueur a changé de touche
        if (direction != lastRawDirection) {
            Direction requested = Direction.fromChar(direction);          // touche brute
            Direction adjusted = healthState.adjustDirection(requested);  // affectée par l'état

            // Si je veux empêcher le demi-tour :
            // (currentDirection != null && adjusted == currentDirection.opposite()) { ... }

            currentDirection = adjusted;
            lastRawDirection = direction;
        }

        // 2) Applique toujours la direction RÉELLE, même si l'état change entre temps
        int x = getHead().getX();
        int y = getHead().getY();

        x += currentDirection.dx();
        y += currentDirection.dy();

        Cell newHead = grid.getTile(x, y);
        if (newHead == null) {
            throw new OutOfPlayException();
        }

        // queue :
        Cell tail = body.get(body.size() - 1);

        if (newHead.containsASnake()) {
            boolean isTail = (newHead == tail);

            if (!isTail || newHead.containsFood()) {
                throw new SelfCollisionException();
            }
        }

        // Eat food :
        if (newHead.containsFood()) {
            this.eat(newHead.getFood(), newHead);
            notifyObservers(newHead);
            return;
        }

        // The snake did not eat :
        newHead.addSnake(this);
        body.add(0, newHead);

        tail = body.get(body.size() - 1);
        tail.removeSnake();
        body.remove(body.size() - 1);

        notifyObservers(newHead);
    }


    // ÉTATS DE SANTÉ DU SERPENT
// =======================
    private sealed interface SnakeHealthState
            permits GoodHealthState, PoisonedState, PermanentlyDamagedState {

        // Ajuste la direction demandée en fonction de l'état de santé
        Direction adjustDirection(Direction input);

        // Calcule le nouvel état après avoir mangé un aliment
        SnakeHealthState afterEating(Snake snake, SnakeFood food);
    }

    // --- ÉTAT : Bonne santé ---
    private static final class GoodHealthState implements SnakeHealthState {

        @Override
        public Direction adjustDirection(Direction input) {
            // mouvements normaux
            return input;
        }

        @Override
        public SnakeHealthState afterEating(Snake snake, SnakeFood food) {
            // Quand le serpent est en bonne santé et mange une pomme empoisonnée → empoisonné
            if (food instanceof Apple apple && apple.isPoisoned()) {
                return new PoisonedState();
            }
            // sinon, il reste en bonne santé
            return this;
        }
    }

    // --- ÉTAT : Empoisonné ---
    private static final class PoisonedState implements SnakeHealthState {

        @Override
        public Direction adjustDirection(Direction input) {
            // Quand empoisonné, UP et DOWN sont inversés
            return switch (input) {
                case U -> Direction.D;
                case D -> Direction.U;
                default -> input; // L et R inchangés
            };
        }

        @Override
        public SnakeHealthState afterEating(Snake snake, SnakeFood food) {
            // empoisonné + pomme empoisonnée -> définitivement endommagé
            if (food instanceof Apple apple && apple.isPoisoned()) {
                return new PermanentlyDamagedState();
            }
            // empoisonné + brocoli (peu importe steamed ou non) -> retour à bonne santé
            if (food instanceof Broccoli) {
                return new GoodHealthState();
            }
            // sinon, reste empoisonné
            return this;
        }
    }

    // --- ÉTAT : Définitivement endommagé ---
    private static final class PermanentlyDamagedState implements SnakeHealthState {

        @Override
        public Direction adjustDirection(Direction input) {
            // UP -> DOWN, DOWN -> UP, LEFT -> RIGHT, RIGHT -> LEFT
            return switch (input) {
                case U -> Direction.D;
                case D -> Direction.U;
                case L -> Direction.R;
                case R -> Direction.L;
            };
        }

        @Override
        public SnakeHealthState afterEating(Snake snake, SnakeFood food) {
            // permanent: ne change plus d'état
            return this;
        }
    }


    // ===== OBSERVER =====

    public void addObserver(SnakeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SnakeObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Cell newHead) {
        for (SnakeObserver observer : observers) {
            observer.onSnakeMoved(this, newHead);
        }
    }



}
