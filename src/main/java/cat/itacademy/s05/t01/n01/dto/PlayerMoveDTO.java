package cat.itacademy.s05.t01.n01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMoveDTO {
    @lombok.NonNull
    private String gameId;
    @lombok.NonNull
    private Double bet;
    @lombok.NonNull
    private String moveType;
}
