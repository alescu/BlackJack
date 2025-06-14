package cat.itacademy.s05.t01.n01.repository;

import cat.itacademy.s05.t01.n01.model.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {
    @Query("select * from players where player_name=:name")
    Mono<Player> findPlayerByName(String name);

    @Query("select * from players where id=:id")
    Mono<Player> findPlayerById(Long id);

    @Query("select * from players ORDER BY profit_balance ASC, player_name ")
    Flux<Player> findPlayersByProfit();
}
