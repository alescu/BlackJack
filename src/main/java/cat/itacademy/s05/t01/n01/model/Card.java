package cat.itacademy.s05.t01.n01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("cards")
public class Card implements Persistable<Integer> {

    @Id
    @Column("id")
    private Integer id;

    @Column("name")
    private String name;

    @Column("type")
    private String type;

    @Column("date_reg")
    private LocalDateTime dateReg;

    @Column("date_modify")
    private LocalDateTime dateModify;

    @Transient
    private boolean newCard;

    @Override
    public boolean isNew() {
        return newCard || getId() == null;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

}