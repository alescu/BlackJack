package cat.itacademy.s05.t01.n01.repository;

import cat.itacademy.s05.t01.n01.model.PlayerGame;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<PlayerGame, String> {

    @Query("{ 'playerName' : ?0 }")
    Flux<PlayerGame> findPlayerGamesByName(String playerName);

}
