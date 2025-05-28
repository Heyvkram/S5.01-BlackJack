package blackjack;
import blackjack.model.Game;
import blackjack.model.Player;
import blackjack.repository.GameRepository;
import blackjack.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepo;

    @InjectMocks
    private GameService gameService;

    @Test
    void createGameTest() {
        // 1. Arrange (Preparar)
        Player player = new Player();
        player.setId(1L);
        Game newGame = new Game(player.getId());
        newGame.setId("testGameId");

        // Mock the repository's save method to return a Mono with the newGame
        when(gameRepo.save(any(Game.class))).thenReturn(Mono.just(newGame));

        // 2. Act (Actuar)
        Mono<Game> result = gameService.createGame(player);

        // 3. Assert (Verificar)
        StepVerifier.create(result)
                .expectNextMatches(game -> {
                    assertNotNull(game.getId());
                    assertEquals(player.getId(), game.getPlayerId());
                    assertFalse(game.isFinished());
                    assertNotNull(game.getPlayerCards());
                    assertEquals(2, game.getPlayerCards().size());
                    assertNotNull(game.getCroupierCards());
                    assertEquals(1, game.getCroupierCards().size());
                    return true;
                })
                .verifyComplete();
    }
}