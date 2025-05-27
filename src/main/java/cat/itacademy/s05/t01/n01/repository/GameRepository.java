package cat.itacademy.s05.t01.n01.repository;

import cat.itacademy.s05.t01.n01.model.Game;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {

    @Query("{ 'playerId' : ?0 }")
    Flux<Game> findPlayerGameByPlayerId(Long playerId);

    @Query("{ 'playerGames.id' : ?0 }")
    Mono<Game> findPlayerGameById(String gameId);

}
