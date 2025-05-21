package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.controllers.GameController;
import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.repository.PlayerRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final PlayerRepository playerRepository;

    PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> setNewPlayer(Player paramPlayer) {
        logger.info("Rebuda petició /new per al jugador: {}", paramPlayer.getPlayerName());

        return playerRepository.findPlayerByName(paramPlayer.getPlayerName())
                .switchIfEmpty(Mono.defer(() -> {
                    logger.info("Creant nou jugador: {}", paramPlayer.getPlayerName());
                    return playerRepository.save(paramPlayer);
                }))
                .doOnNext(player -> logger.info("Retornant jugador: {}", player.getPlayerName()));
    }

    public Mono<Player> getPlayerByName(String playerName) {
        logger.info("Rebuda petició /new per al jugador: {}", playerName);
        return playerRepository.findPlayerByName(playerName)
                .switchIfEmpty(Mono.defer(() -> {
                    Player newPlayer = new Player(playerName);
                    newPlayer.setAccount(100.00);
                    logger.info("Creant nou jugador: {}", playerName);
                    return playerRepository.save(newPlayer);
                }))
                .doOnNext(player -> logger.info("Retornant jugador: {}", player.getPlayerName()));
    }

}
