package blackjack.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Table("player")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    private Long id;
    private String name;

    public Player(String name) {
        this.name = name;
    }
}