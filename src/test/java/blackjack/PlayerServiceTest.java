package blackjack;

import blackjack.model.Player;
import blackjack.repository.PlayerRepository;
import blackjack.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void createPlayerTest() {
        Player newPlayer = new Player("Test Player");
        Player savedPlayer = new Player("Test Player");
        savedPlayer.setId(1L); // Simulate ID being assigned on save
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(savedPlayer));
        Mono<Player> result = playerService.createPlayer(newPlayer);
        StepVerifier.create(result)
                .expectNextMatches(player -> {
                    assertNotNull(player.getId());
                    assertEquals(newPlayer.getName(), player.getName());
                    return true;
                })
                .verifyComplete();
    }
}