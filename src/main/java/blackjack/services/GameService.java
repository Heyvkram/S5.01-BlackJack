package blackjack.services;
import blackjack.DTO.RankingResponseDTO;
import blackjack.exceptions.GameAlreadyFinishedException;
import blackjack.exceptions.GameNotFoundException;
import blackjack.model.Card;
import blackjack.model.Game;
import blackjack.model.Player;
import blackjack.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepo;

    public Mono<Game> createGame(Player player) {
        Game game = new Game(player.getId());
        return gameRepo.save(game);
    }

    public Mono<Game> play(String gameId, boolean askForCard) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + gameId + " not found")))
                .flatMap(game -> {
                    if (game.isFinished()) {
                        return Mono.error(new GameAlreadyFinishedException("Game with ID " + gameId + " is already finished"));
                    }

                    if (askForCard) {
                        // PLAYER'S TURN
                        dealCardToPlayer(game);
                        if (game.getPlayerScore() > 21) {
                            game.setWinner("CROUPIER");
                            game.setFinished(true);
                        }
                    } else if (!game.isFinished()) {
                        // CROUPIER'S TURN
                        playCroupierTurn(game);
                        determineWinner(game);
                        game.setFinished(true);
                    }
                    return gameRepo.save(game);
                });
    }

    private void dealCardToPlayer(Game game) {
        Card newCard = Card.generateCard(game.getCardsInPlay());
        game.addPlayerCard(newCard);
    }

    private void dealCardToCroupier(Game game) {
        Card newCard = Card.generateCard(game.getCardsInPlay());
        game.addCroupierCard(newCard);
    }

    private void playCroupierTurn(Game game) {
        while (game.getCroupierScore() < 17) {
            dealCardToCroupier(game);
        }
        // The second condition was a bit unusual, adjusting logic
        if (game.getCroupierScore() <= 21) {
            while (game.getCroupierScore() < game.getPlayerScore() && game.getPlayerScore() <= 21) {
                dealCardToCroupier(game);
                if (game.getCroupierScore() > 21) break; // Croupier busts, no need to continue drawing
            }
        }
    }

    private void determineWinner(Game game) {
        int playerScore = game.getPlayerScore();
        int croupierScore = game.getCroupierScore();

        if (playerScore > 21) {
            game.setWinner("CROUPIER");
        } else if (croupierScore > 21) {
            game.setWinner("PLAYER");
        } else if (playerScore > croupierScore) {
            game.setWinner("PLAYER");
        } else if (croupierScore > playerScore) {
            game.setWinner("CROUPIER");
        } else {
            game.setWinner("DRAW");
        }
    }

    public Mono<Void> delete(String id) {
        return gameRepo.deleteById(id);
    }

    public Mono<Game> getGame(String id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + id + " not found")));
    }

    public Flux<Game> getAllGames() {
        return gameRepo.findAll();
    }

    public Flux<Object> getGamesByPlayerId(Long id) {
        return gameRepo.findByPlayerId(id)
                .switchIfEmpty(Flux.error(new GameNotFoundException("No games found for player with ID " + id)));
    }

    public Flux<RankingResponseDTO> getPlayersIDsOrderedByWins() {
        return gameRepo.findByWinner("PLAYER")
                .groupBy(Game::getPlayerId)
                .flatMap(groupedFlux -> groupedFlux.count()
                        .map(count -> RankingResponseDTO.builder().userId(groupedFlux.key()).wins(count).build()))
                .sort((a, b) -> Long.compare(b.getWins(), a.getWins()));
    }
}
