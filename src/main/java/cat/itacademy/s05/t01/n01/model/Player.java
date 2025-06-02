package cat.itacademy.s05.t01.n01.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table("players")
public class Player {
    @Id
    private Long id;
    @lombok.NonNull
    private String playerName;
    private double account;
    private double profitBalance;
    private Integer gamesStarted = 0;
    private Integer gamesWon = 0;
    private Integer gamesLost = 0;
    private Integer gamesDraw = 0;

    public void addGameStarted(){
        this.gamesStarted++;
    }

    public void addLostGame(){
        this.gamesLost++;
    }

    public void addWinGame(){
        this.gamesWon++;
    }

    public void addDrawGame(){
        this.gamesDraw++;
    }

    public void addProfit(Double balance){
        this.profitBalance += balance;
    }

    public void addAccount(Double account){
        this.account += account;
    }

    public Player(Long id, @NonNull String playerName) {
        this.id = id;
        this.playerName = playerName;
    }
}
