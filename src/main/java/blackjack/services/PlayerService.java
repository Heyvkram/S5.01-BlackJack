package blackjack.services;

import blackjack.model.Player;
import blackjack.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Player> changePlayerName(Long playerId, String newName) {
        return playerRepository.findById(playerId)
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                })
                .switchIfEmpty(Mono.empty()); // Indica que no se encontró el jugador
    }

    public Mono<Void> deletePlayer(Long playerId) {
        return playerRepository.deleteById(playerId);
    }
}
