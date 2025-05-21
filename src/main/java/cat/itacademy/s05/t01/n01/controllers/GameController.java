package cat.itacademy.s05.t01.n01.controllers;

import cat.itacademy.s05.t01.n01.dto.PlayerMoveDTO;
import cat.itacademy.s05.t01.n01.model.PlayerGame;
import cat.itacademy.s05.t01.n01.repository.CardRepository;
import cat.itacademy.s05.t01.n01.services.GameService;
import cat.itacademy.s05.t01.n01.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/game")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;

    @GetMapping("/{playerName}")
    public ResponseEntity<Mono<PlayerGame>> newGame(@PathVariable String playerName){
        System.out.println("playerName = " + playerName);
        Mono<PlayerGame> playerGames = gameService.createPlayerNewGame(playerName);
        System.out.println("carregat = " + playerName);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerGames);
    }

    @PostMapping("/play/{id}")
    public Mono<ResponseEntity<Mono<PlayerGame>>> playMove(@PathVariable String id, @RequestBody PlayerMoveDTO playerMove) {
        // String orderType, double amount
        // Cal mirar si l'usuari ja és a la bd.
        // CAl mirar si té partida coemençada? o sols es guarden quan has acabat?
        // Carta, Separar, Plantarse

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(gameService.getGameById(id)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Mono<Boolean>>> deleteGame(@PathVariable String id) {
        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(gameService.deleteGame(id)));
    }

}
