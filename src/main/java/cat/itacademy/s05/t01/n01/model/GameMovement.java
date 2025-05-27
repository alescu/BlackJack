package cat.itacademy.s05.t01.n01.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class GameMovement {
    @Id
    private Integer id;
    private String moveType;
    private String cardName;
    @Builder.Default
    private Integer cardPoints = 0;
    @Builder.Default
    private Integer totalPoints = 0;
    @Builder.Default
    private Double bet = 0.0;
    private Integer deckCardsHashCode;
}
