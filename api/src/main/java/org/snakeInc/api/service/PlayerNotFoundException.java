package org.snakeInc.api.service;

public class PlayerNotFoundException extends RuntimeException {
    private final int playerId;

    public PlayerNotFoundException(int playerId) {
        super("Player not found: " + playerId);
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
