package cat.itacademy.s05.t01.n01.controllers;

import cat.itacademy.s05.t01.n01.dto.PlayerMoveDTO;
import cat.itacademy.s05.t01.n01.model.Game;
import cat.itacademy.s05.t01.n01.repository.CardRepository;
import cat.itacademy.s05.t01.n01.services.GameService;
import cat.itacademy.s05.t01.n01.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Open a new Game for a player",
            description = "Open a new Game for a player."
    )
    @PostMapping("/{playerName}")
    public ResponseEntity<Mono<Game>> newGame(@PathVariable String playerName){
        System.out.println("playerName = " + playerName);
        Mono<Game> playerGames = gameService.createPlayerNewGame(playerName);
        System.out.println("carregat = " + playerName);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerGames);
    }

    @Operation(
            summary = "Add a play movement to Player in game",
            description = "Add a play movement to Player in game."
    )
    @PostMapping("/{playerName}/play")
    public ResponseEntity<Mono<Game>> setPlayerMove(@PathVariable String playerName, @RequestBody PlayerMoveDTO playerMoveDto) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.createPlayerNewMovement(playerName, playerMoveDto));
    }

    @Operation(
            summary = "Delete a game by their id",
            description = "Delete a game by their id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Boolean>> deleteGame(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(gameService.deleteGame(id));
    }

    @Operation(
            summary = "Show a game by their id",
            description = "Show a game by their id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Game>> getGame(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameById(id));
    }

}
