package cat.itacademy.s05.t01.n01.model;

import cat.itacademy.s05.t01.n01.dto.PlayerMoveDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "blackjack_games")
public class PlayerMove {
    @Id
    private String id;
    @lombok.NonNull
    private long playerId;
    @lombok.NonNull
    private double bet;
}
