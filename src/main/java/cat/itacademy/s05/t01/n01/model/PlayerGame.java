package cat.itacademy.s05.t01.n01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blackjack_games")
public class PlayerGame {
    @Id
    private String id;
    private long playerId;
    private List<Card> cardsReceived;
    private List<Card> deckCards;
    private List<PlayerMove> playerMoves;

    public PlayerGame(long playerId, List<Card> deckCards) {
        this.playerId = playerId;
        this.deckCards = deckCards;
        this.id = null;
    }
}
