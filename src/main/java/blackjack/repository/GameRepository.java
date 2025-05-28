package blackjack.repository;
import blackjack.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByWinner(String winner);

    Flux<Object> findByPlayerId(Long id);
}