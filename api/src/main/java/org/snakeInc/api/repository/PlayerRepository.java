package org.snakeInc.api.repository;

import org.snakeInc.api.entities.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    // Pas besoin de code : les méthodes basiques CRUD sont déjà là :
    // save, findById, findAll, deleteById, etc.
}
