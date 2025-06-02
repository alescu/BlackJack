package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.business.GameOptions;
import cat.itacademy.s05.t01.n01.controllers.GameController;
import cat.itacademy.s05.t01.n01.exception.BlackJackGameException;
import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.repository.PlayerRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
                .flatMap(existingPlayer -> {
                    logger.warn("El jugador amb nom '{}' ja existeix. No es pot crear un de nou.", paramPlayer.getPlayerName());
                    return Mono.<Player>error(new BlackJackGameException("El jugador amb nom '" + paramPlayer.getPlayerName() + "' ja existeix."));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    logger.info("Creant nou jugador: {}", paramPlayer.getPlayerName());

                    Double accountInitial = paramPlayer.getAccount();

                    if(GameOptions.INITIAL_ACOUNT>accountInitial){
                        return Mono.error(new BlackJackGameException("La aportació inicial ha de ser major de " + GameOptions.INITIAL_ACOUNT));
                    }

                    Player newPlayer = new Player(paramPlayer.getPlayerName());
                    newPlayer.setAccount(accountInitial);
                    newPlayer.setGamesWon(0);
                    newPlayer.setGamesLost(0);
                    return playerRepository.save(newPlayer);
                }))
                .doOnNext(player -> {
                    logger.info("Jugador '{}' creat amb èxit.", player.getPlayerName());
                });
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

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll()
                .doOnComplete(() -> logger.info("Retornats tots els jugadors."))
                .doOnError(e -> logger.error("Error obtenint tots els jugadors: {}", e.getMessage()));
    }

    public Flux<Player> getAllPlayersByProfit() {
        return playerRepository.findPlayersByProfit()
                .doOnComplete(() -> logger.info("Retornats tots els jugadors."))
                .doOnError(e -> logger.error("Error obtenint tots els jugadors: {}", e.getMessage()));
    }

    public Mono<Player> updatePlayerAccount(String playerName, Double accountValue) {
        logger.info("Actualitzant compte del jugador amb nom {}. Nou valor: {}", playerName, accountValue);

        return playerRepository.findPlayerByName(playerName)
                .flatMap(player -> {
                    Double newValue = player.getAccount() + accountValue;
                    player.setAccount(newValue);
                    return playerRepository.save(player);
                })
                .doOnSuccess(player -> logger.info("Compte del jugador {} actualitzat correctament.", player.getPlayerName()))
                .doOnError(e -> logger.error("Error actualitzant compte del jugador amb nom {}: {}", accountValue, e.getMessage()))
                .switchIfEmpty(Mono.error(new BlackJackGameException("Jugador amb nom " + accountValue + " no trobat.")));
    }

}
