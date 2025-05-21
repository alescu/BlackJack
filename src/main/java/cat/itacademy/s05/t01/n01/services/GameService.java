package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.model.PlayerGame;
import cat.itacademy.s05.t01.n01.model.Player;
import cat.itacademy.s05.t01.n01.repository.GameRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static reactor.core.publisher.Mono.*;

@Data
@Service
public class GameService {

    private final ConcurrentHashMap<String, Mono<List<Card>>> gameStacksMono = new ConcurrentHashMap<>();

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final CardService cardService;

    GameService(GameRepository gameRepository, PlayerService playerService, CardService cardService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.cardService = cardService;
    }

    public Mono<PlayerGame> getGameById(String gameId) {
        return gameRepository.findById(gameId);
    }

    public Mono<Boolean> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .flatMap(gameToDelete -> gameRepository.delete(gameToDelete)
                        .thenReturn(true))
                .switchIfEmpty(just(false));
    }

    public Mono<PlayerGame> createPlayerNewGame(String playerName) {
        return playerService.getPlayerByName(playerName)
                .flatMap(player -> {
                    Mono<List<Card>> shuffledCardsMono = cardService.getShuffledCardsListMono();
                    return shuffledCardsMono.flatMap(deckCards -> {
                        List<Card> gamedCards = new ArrayList<>();
                        gamedCards.add(deckCards.getFirst());
                        PlayerGame newGame = PlayerGame.builder()
                                .playerId(player.getId())
                                .deckCards(deckCards)
                                .cardsReceived(gamedCards)
                                .build();
                        return Mono.just(newGame).flatMap(gameRepository::save);
                    });
                });
    }


    public Mono<PlayerGame> saveGame(PlayerGame game) {

        return gameRepository.findById(game.getId()).flatMap(savedGame -> {
            //List<Card> deckCards = savedGame.getDeckCards();
            savedGame.getDeckCards().add(savedGame.getDeckCards().getFirst());
            return Mono.just(savedGame).flatMap(gameRepository::save);
        });
    }


    public Mono<List<Card>> getFirstCardAsNewList(Mono<List<Card>> monoCardList) {
        return monoCardList.map(shuffledCards -> {
            Card firstCard = shuffledCards.get(0);
            System.out.println("Primera carta obtinguda: " + firstCard);
            List<Card> newList = new ArrayList<>();
            newList.add(firstCard);
            return newList;
        });
    }

    public Mono<Optional<Card>> getFirstCardOptional(Mono<List<Card>> monoCardList) {
        return monoCardList.map(shuffledCards -> shuffledCards.isEmpty() ? Optional.empty() : Optional.of(shuffledCards.get(0)));
    }

    public Flux<PlayerGame> getPlayerGames(String playerName) {
        Mono<Player> player = playerService.getPlayerByName(playerName);
        return gameRepository.findPlayerGamesByName(playerName);
    }
/*
    public Mono<Game> updateGame(String id, ArrayList<Card> cardsReceived, ArrayDeque<Card> deckCards) {
        return gameRepository.findById(id)
                .flatMap(gameItem -> {
                    gameItem.setDeckCards(deckCards);
                    gameItem.setCardsReceived(cardsReceived);
                    return gameRepository.save(gameItem);
                });
    }
    */
}
