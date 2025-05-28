package blackjack.controller;

import blackjack.model.Player;
import blackjack.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/new")
    public Mono<ResponseEntity<Player>> createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player)
                .map(newPlayer -> ResponseEntity.ok(newPlayer));
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<Flux<Player>>> getAllPlayers() {
        return Mono.just(ResponseEntity.ok(playerService.getAllPlayers()));
    }

    @PutMapping("/modify/{playerId}")
    public Mono<ResponseEntity<Player>> changePlayerName(@PathVariable Long playerId, @RequestBody String newName) {
        return playerService.changePlayerName(playerId, newName)
                .map(updatedPlayer -> updatedPlayer != null ? ResponseEntity.ok(updatedPlayer) : ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{playerId}")
    public Mono<ResponseEntity<Void>> deletePlayer(@PathVariable Long playerId) {
        return playerService.deletePlayer(playerId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
