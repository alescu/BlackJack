package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.business.BlackJackBusiness;
import cat.itacademy.s05.t01.n01.business.GameOptions;
import cat.itacademy.s05.t01.n01.dto.PlayerMoveDTO;
import cat.itacademy.s05.t01.n01.exception.BlackJackGameException;
import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.model.Game;
import cat.itacademy.s05.t01.n01.model.GameMovement;
import cat.itacademy.s05.t01.n01.model.Player;
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
    private final BlackJackBusiness blackJackBussines;

    GameService(GameRepository gameRepository, PlayerService playerService, CardService cardService, BlackJackBusiness blackJackBussines) {
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
                    final Double minimalBet = GameOptions.MINIMAL_BET;

                    if (player.getAccount() < minimalBet) {
                        return Mono.error(new BlackJackGameException("El saldo del jugador és insuficient."));
                    }

                    Mono<List<Card>> shuffledCardsMono = cardService.getShuffledCardsListMono();
                    return shuffledCardsMono.flatMap(deckCards -> {

                        List<Card> copiedList = new ArrayList<>(deckCards);
                        deckCards.addAll(copiedList);
                        Collections.shuffle(deckCards, new Random());

                        List<Card> gamedCards = new ArrayList<>();
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
                                .moveType(GameOptions.HIT)
                                .bet(minimalBet)
                                .cardName(firstCard.getName())
                                .cardPoints(cardPoints)
                                .totalPoints(cardPoints)
                                .deckCardsHashCode(blackJackBussines.getHashCode(deckCards))
                                .build();


                        newGame.getPlayerMoves().add(playerFirstMove);
                        newGame.setPlayerPoints(cardPoints);

                        player.addGameStarted();
                        player.addAccount(-minimalBet);
                        player.addProfit(-minimalBet);

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

                    Double playerBet = dto.getBet();

                    if (0.0 > playerBet) {
                        return Mono.error(new BlackJackGameException("L'aposta ha de ser positiva."));
                    }

                    if (player.getAccount() < playerBet) {
                        return Mono.error(new BlackJackGameException("El saldo del jugador és insuficient."));
                    }

                    return getGameById(dto.getGameId())
                            .switchIfEmpty(Mono.error(new NoSuchElementException("Joc no trobat amb ID: " + dto.getGameId())))
                            .flatMap(game -> {

                                if (game.getResultCode() != 0) {
                                    return Mono.error(new BlackJackGameException("Aquesta partida ja s'ha acabat"));
                                }

                                if (game.getDeckCards().isEmpty()) {
                                    return Mono.error(new BlackJackGameException("No queden cartes a la baralla."));
                                }

                                if (!player.getId().equals(game.getPlayerId())) {
                                    return Mono.error(new BlackJackGameException("Aquesta partida no pertany al jugador."));
                                }

                                if (player.getAccount() < playerBet) {
                                    return Mono.error(new BlackJackGameException("El saldo del jugador és inferior a la seva aposta."));
                                }

                                GameMovement playerMove;
                                if (GameOptions.HIT.equalsIgnoreCase(dto.getMoveType())) {
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
                                    player.addAccount(- dto.getBet());
                                    player.addProfit(- dto.getBet());
                                    if (game.getPlayerPoints() > 21) {
                                        game.setResultMessage("YOU LOSS");
                                        game.setResultCode(1);
                                    }

                                } else if (GameOptions.STAND.equalsIgnoreCase(dto.getMoveType())) {
                                    playerMove = GameMovement.builder()
                                            .id(game.getPlayerMoves().size())
                                            .moveType(dto.getMoveType())
                                            .build();
                                    game.getPlayerMoves().add(playerMove);
                                    game.addBet(dto.getBet());
                                    player.addAccount(- dto.getBet());
                                    player.addProfit(- dto.getBet());

                                    blackJackBussines.generateDealerMovements(game);

                                } else {
                                    return Mono.error(new BlackJackGameException("El saldo del jugador és inferior a la seva aposta."));
                                }

                                blackJackBussines.resolveGame(player, game);

                                return playerService.getPlayerRepository().save(player)
                                        .flatMap(updatedPlayer -> {
                                            return gameRepository.save(game);
                                        });

                            });
                });
    }

}
