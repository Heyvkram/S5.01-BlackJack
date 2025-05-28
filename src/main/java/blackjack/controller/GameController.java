package blackjack.controller;

import blackjack.DTO.RankingResponseDTO;
import blackjack.model.Game;
import blackjack.model.Player;
import blackjack.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/new/{playerId}")
    public Mono<ResponseEntity<Game>> createNewGame(@PathVariable Long playerId) {
        Player player = new Player(); // Assuming PlayerService handles player retrieval/creation
        player.setId(playerId);
        return gameService.createGame(player)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @PostMapping("/{gameId}/hit")
    public Mono<ResponseEntity<Game>> playerHits(@PathVariable String gameId) {
        return gameService.play(gameId, true)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{gameId}/stand")
    public Mono<ResponseEntity<Game>> playerStands(@PathVariable String gameId) {
        return gameService.play(gameId, false)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<Game>> getGameById(@PathVariable String gameId) {
        return gameService.getGame(gameId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/list")
    public Flux<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/player/{playerId}")
    public Flux<ResponseEntity<Object>> getGamesByPlayer(@PathVariable Long playerId) {
        return gameService.getGamesByPlayerId(playerId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Flux.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/ranking")
    public Flux<RankingResponseDTO> getRanking() {
        return gameService.getPlayersIDsOrderedByWins();
    }

    @DeleteMapping("/delete/{gameId}")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String gameId) {
        return gameService.delete(gameId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}