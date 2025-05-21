package cat.itacademy.s05.t01.n01.dto;

import cat.itacademy.s05.t01.n01.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blackjack_games")
public class PlayerMoveDTO {
    @lombok.NonNull
    private long playerId;
    @lombok.NonNull
    private double bet;
}
