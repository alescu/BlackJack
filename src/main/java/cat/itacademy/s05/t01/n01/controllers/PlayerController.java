package cat.itacademy.s05.t01.n01.controllers;

import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(
            summary = "Add a new Player",
            description = "Add a new Player in game."
    )
    @PostMapping("")
    public ResponseEntity<Mono<Player>> newPlayer(@RequestBody Player newPlayer){
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.setNewPlayer(newPlayer));
    }

    @Operation(
            summary = "Get all players",
            description = "Get all players in game."
    )
    @GetMapping("")
    public ResponseEntity<Flux<Player>> getPlayers(){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayers());
    }

    @Operation(
            summary = "List of players by profit order",
            description = "Get all players in game by profit order"
    )
    @GetMapping("/getPlayersByProfit")
    public ResponseEntity<Flux<Player>> getPlayersByProfit(){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayersByProfit());
    }

    @Operation(
            summary = "Get player for their name",
            description = "Get player for their name"
    )
    @GetMapping("/{playerName}")
    public ResponseEntity<Mono<Player>> getPlayerById(@PathVariable String playerName){
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.getPlayerByName(playerName));
    }

    @Operation(
            summary = "Add credit to the user's account",
            description = "Add credit to the user's account"
    )
    @PostMapping("/{playerName}/addAccount")
    public ResponseEntity<Mono<Player>> updatePlayerAccount(@PathVariable String playerName, @RequestBody Double accountValue){
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.updatePlayerAccount(playerName, accountValue));
    }

}
