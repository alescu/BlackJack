package cat.itacademy.s05.t01.n01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @Transient
    @JsonIgnore
    private boolean newCard;

    public Card(Object o, String dummy, int i) {
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return newCard || getId() == null;
    }

    @Override
    @JsonIgnore
    public Integer getId() {
        return this.id;
    }

}