package blackjack.repository;
import blackjack.model.Player;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Long> {
    Mono<Player> findById(Long id);
    //Flux<Player> findAllOrderByRanking();
}