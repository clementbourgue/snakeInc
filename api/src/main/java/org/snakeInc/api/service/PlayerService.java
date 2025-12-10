package org.snakeInc.api.service;

import org.snakeInc.api.entities.Player;
import org.snakeInc.api.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player addPlayer(String name, int age) {
        // Crée un joueur (sans id, id sera généré par la DB)
        Player player = new Player(name, age);
        // Hibernate va faire INSERT et remplir player.id
        return playerRepository.save(player);
    }

    public Player getPlayerById(int id) {
        Optional<Player> optional = playerRepository.findById(id);
        return optional.orElse(null); // tu peux garder null pour l’instant, comme avant
    }
}
