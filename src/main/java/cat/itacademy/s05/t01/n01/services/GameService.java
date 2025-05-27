package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.business.BlackJackBussines;
import cat.itacademy.s05.t01.n01.business.GameOptions;
import cat.itacademy.s05.t01.n01.dto.PlayerMoveDTO;
import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.model.Game;
import cat.itacademy.s05.t01.n01.model.GameMovement;
import cat.itacademy.s05.t01.n01.repository.GameRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
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
    private final BlackJackBussines blackJackBussines;

    GameService(GameRepository gameRepository, PlayerService playerService, CardService cardService, BlackJackBussines blackJackBussines) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.cardService = cardService;
        this.blackJackBussines = blackJackBussines;
    }

    public Mono<Game> getGameById(String gameId) {
        return gameRepository.findById(gameId);
    }

    public Mono<Boolean> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .flatMap(gameToDelete -> gameRepository.delete(gameToDelete)
                        .thenReturn(true))
                .switchIfEmpty(just(false));
    }

    public Mono<Game> createPlayerNewGame(String playerName) {
        return playerService.getPlayerByName(playerName)
                .flatMap(player -> {
                    final Double minimalBet = 5.0;
                    Mono<List<Card>> shuffledCardsMono = cardService.getShuffledCardsListMono();
                    return shuffledCardsMono.flatMap(deckCards -> {
                        List<Card> gamedCards = new ArrayList<>();
                        deckCards.addAll(deckCards);
                        Collections.shuffle(deckCards, new Random());

                        Card firstCard = deckCards.removeFirst();
                        gamedCards.add(firstCard);
                        int cardPoints = blackJackBussines.getCardValue(firstCard.getName(), 0);

                        Game newGame = Game.builder()
                                .playerId(player.getId())
                                .deckCards(deckCards)
                                .deckCardsHashCode(deckCards.hashCode())
                                .cardsReceived(gamedCards)
                                .build();

                        GameMovement playerFirstMove = GameMovement.builder()
                                .id(0)
                                .moveType("C")
                                .bet(minimalBet)
                                .cardName(firstCard.getName())
                                .cardPoints(cardPoints)
                                .totalPoints(cardPoints)
                                .deckCardsHashCode(blackJackBussines.getHashCode(deckCards))
                                .build();


                        newGame.getPlayerMoves().add(playerFirstMove);
                        newGame.setPlayerPoints(cardPoints);

                        // return Mono.just(newGame).flatMap(gameRepository::save);
                        player.addGameStarted();
                        player.setAccount(player.getAccount() - minimalBet);
                        return playerService.getPlayerRepository().save(player)
                                .flatMap(updatedPlayer -> {
                                    return gameRepository.save(newGame);
                                });
                    });
                });

    }

    public Mono<Game> createPlayerNewMovement(String playerName, PlayerMoveDTO dto) {
        return playerService.getPlayerByName(playerName)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Jugador no trobat: " + playerName)))
                .flatMap(player -> {

                    if (player.getAccount() < GameOptions.MINIMAL_BET) {
                        return Mono.error(new IllegalStateException("El saldo del jugador és insuficient."));
                    }

                    return getGameById(dto.getGameId())
                            .switchIfEmpty(Mono.error(new NoSuchElementException("Joc no trobat amb ID: " + dto.getGameId())))
                            .flatMap(game -> {

                                if (game.getResultCode() != 0) {
                                    return Mono.error(new IllegalStateException("Aquesta partida ja s'ha acabat"));
                                }

                                if (game.getDeckCards().isEmpty()) {
                                    return Mono.error(new IllegalStateException("No queden cartes a la baralla."));
                                }

                                if (!player.getId().equals(game.getPlayerId())) {
                                    return Mono.error(new IllegalStateException("Aquesta partida no pertany al jugador."));
                                }

                                if (player.getAccount() < dto.getBet()) {
                                    return Mono.error(new IllegalStateException("El saldo del jugador és inferior a la seva aposta."));
                                }

                                GameMovement playerMove;
                                if (GameOptions.HIT.equals(dto.getMoveType())) {
                                    Card nextCard = game.getDeckCards().removeFirst();
                                    game.getCardsReceived().add(nextCard);
                                    int cardValue = blackJackBussines.getCardValue(nextCard.getName(), game.getPlayerPoints());
                                    playerMove = GameMovement.builder()
                                            .id(game.getPlayerMoves().size())
                                            .moveType(dto.getMoveType())
                                            .bet(dto.getBet())
                                            .cardName(nextCard.getName())
                                            .cardPoints(cardValue)
                                            .totalPoints(game.getPlayerPoints() + cardValue)
                                            .deckCardsHashCode(game.getDeckCards().hashCode())
                                            .build();
                                    game.setPlayerPoints(game.getPlayerPoints() + cardValue);
                                    game.getPlayerMoves().add(playerMove);
                                    game.addBet(dto.getBet());
                                    player.setAccount(player.getAccount() - dto.getBet());

                                    if (game.getPlayerPoints() > 21) {
                                        game.setResultMessage("YOU LOSS");
                                        game.setResultCode(1);
                                    }

                                } else if (GameOptions.STAND.equals(dto.getMoveType())) {
                                    playerMove = GameMovement.builder()
                                            .id(game.getPlayerMoves().size())
                                            .moveType(dto.getMoveType())
                                            .build();
                                    game.getPlayerMoves().add(playerMove);
                                    game.addBet(dto.getBet());
                                    blackJackBussines.generateDealerMovements(game);

                                } else {
                                    return Mono.error(new IllegalStateException("El saldo del jugador és inferior a la seva aposta."));
                                }

                                if (game.getResultCode() == 1) {
                                    game.setResultMessage("La banca guanya");
                                    player.addLostGame();
                                } else if ((game.getResultCode() == 2)) {
                                    game.setResultMessage("Empat");
                                    player.addProfit(game.getTotalBet());
                                    player.addDrawGame();
                                } else if ((game.getResultCode() == 3)) {
                                    game.setResultMessage("La banca perd");
                                    player.addProfit(game.getTotalBet() * 2);
                                    player.addWinGame();
                                }

                                return playerService.getPlayerRepository().save(player)
                                        .flatMap(updatedPlayer -> {
                                            return gameRepository.save(game);
                                        });

                            });
                });
    }

    public Mono<Game> saveGame(Game game) {

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

}
