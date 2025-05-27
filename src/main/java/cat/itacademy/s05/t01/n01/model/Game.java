package cat.itacademy.s05.t01.n01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blackjack_games")
public class Game {
    @Id
    private String id;
    private long playerId;
    @JsonIgnore
    private List<Card> deckCards;
    private List<Card> cardsReceived = new ArrayList<>();
    private List<Card> dealerCards = new ArrayList<>();
    private String resultMessage;
    @Builder.Default
    private Integer resultCode = 0;
    private Integer deckCardsHashCode;
    @Builder.Default
    private Integer playerPoints = 0;
    @Builder.Default
    private Integer dealerPoints = 0;
    @Builder.Default
    private Double totalBet = 0.0;
    @Builder.Default
    private Double resultBet = 0.0;
    @Builder.Default
    private List<GameMovement> playerMoves = new ArrayList<>();
    private List<GameMovement> dealerMoves = new ArrayList<>();

    public Game(long playerId, List<Card> deckCards) {
        this.playerId = playerId;
        this.deckCards = deckCards;
        this.id = null;
    }

    public void addBet(Double bet){
      this.totalBet += bet;
    }

}
