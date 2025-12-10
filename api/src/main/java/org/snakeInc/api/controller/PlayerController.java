package org.snakeInc.api.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.snakeInc.api.entities.Player;
import org.snakeInc.api.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@Valid @RequestBody PlayerParams params) {

        Player player = playerService.addPlayer(
                params.name(),
                params.age()
        );

        return ResponseEntity.ok(player);
    }

    private record PlayerParams(
            @NotBlank(message = "Tu dois absolument renseigner un nom !")
            String name,

            @Min(value = 14, message = "Tu dois avoir plus de 13 ans !")
            int age
    ) { }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable int id) {

        Player player = playerService.getPlayerById(id);

        if (player == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(player);
    }
}
