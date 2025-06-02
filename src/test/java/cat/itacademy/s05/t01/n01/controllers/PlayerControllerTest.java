package cat.itacademy.s05.t01.n01.controllers;

import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        PlayerController playerController = new PlayerController(playerService);
        webTestClient = WebTestClient.bindToController(playerController).build();
    }

    @Test
    void newPlayer_success_returnsCreatedPlayer() {

        Player newPlayer = new Player(null, "Test Player");
        Player savedPlayer = new Player(1L, "Test Player");

        when(playerService.setNewPlayer(any(Player.class))).thenReturn(Mono.just(savedPlayer));

        webTestClient.post()
                .uri("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newPlayer)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Player.class)
                .isEqualTo(savedPlayer);

        verify(playerService).setNewPlayer(newPlayer);
    }

    @Test
    void newPlayer_nullBody_returnsBadRequest() {
        webTestClient.post()
                .uri("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();

        verify(playerService, never()).setNewPlayer(any(Player.class));
    }

    @Test
    void getPlayers_success_returnsListOfPlayers() {

        Player player1 = new Player(1L, "Aaaaa");
        Player player2 = new Player(2L, "Bbbbb");
        List<Player> expectedPlayers = Arrays.asList(player1, player2);

        when(playerService.getAllPlayers()).thenReturn(Flux.fromIterable(expectedPlayers));

        webTestClient.get()
                .uri("/player")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Player.class)
                .isEqualTo(expectedPlayers);

        verify(playerService).getAllPlayers();
    }

    @Test
    void getPlayers_noContent_returnsEmptyListAndOkStatus() {

        when(playerService.getAllPlayers()).thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/player")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Player.class)
                .hasSize(0);

        verify(playerService).getAllPlayers();
    }


}
