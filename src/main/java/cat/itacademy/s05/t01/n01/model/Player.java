package cat.itacademy.s05.t01.n01.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@RequiredArgsConstructor
@Table("players")
public class Player {
    @Id
    private Long id;
    @lombok.NonNull
    private String playerName;
    private double account;
    private double profitBalance;
    private int gamesStarted;
    private int gamesWon;
    private int gamesLost;
}
