package org.snakeinc.snake.model;

import java.util.HashMap;
import lombok.Data;
import org.snakeinc.snake.GameParams;

@Data
public class Grid {

    private HashMap<CellKey, Cell> tiles = new HashMap<>();

    private static Grid instance;

    private Grid() {
        for (int x = 0; x < GameParams.TILES_X; x++) {
            for (int y = 0; y < GameParams.TILES_Y; y++) {
                tiles.put(new CellKey(x, y), new Cell(x, y));
            }
        }
    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public Cell getTile(int x, int y) {
        return tiles.get(new CellKey(x, y));
    }


    private record CellKey(int x, int y) {

    }

}
