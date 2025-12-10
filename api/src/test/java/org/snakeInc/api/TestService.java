package org.snakeInc.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.snakeInc.api.entities.Player;
import org.snakeInc.api.service.PlayerService;

import static org.assertj.core.api.Assertions.assertThat;

class TestService {

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPlayer_shouldCreatePlayerWithGeneratedIdAndStoreIt() {

        Player player = playerService.addPlayer("Clément", 22);

        assertThat(player).isNotNull();
        assertThat(player.getId()).isGreaterThan(0);
        assertThat(player.getName()).isEqualTo("Clément");
        assertThat(player.getAge()).isEqualTo(22);
        assertThat(player.getCategory()).isEqualTo("SENIOR");

        Player fromService = playerService.getPlayerById(player.getId());
        assertThat(fromService).isNotNull();
        assertThat(fromService.getName()).isEqualTo("Clément");
    }

    @Test
    void getPlayerById_shouldReturnNullWhenIdDoesNotExist() {

        Player player = playerService.getPlayerById(999);

        assertThat(player).isNull();
    }
}
