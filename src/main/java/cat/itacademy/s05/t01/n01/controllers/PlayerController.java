package cat.itacademy.s05.t01.n01.controllers;

import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerServices;

    @PostMapping("")
    public ResponseEntity<Mono<Player>> newPlayer(@RequestBody Player newPlayer){
        System.out.println("name = " + newPlayer.getPlayerName());
        return ResponseEntity.status(HttpStatus.CREATED).body(playerServices.setNewPlayer(newPlayer));
    }

}
