package blackjack.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document("games")
public class Game {
    @Id
    private String id;
    private Long playerId;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> croupierCards = new ArrayList<>();
    private String winner;
    private boolean finished = false;
    private Set<Card> cardsInPlay = new HashSet<>();

    public Game(Long playerId) {
        this.playerId = playerId;
        dealInitialCards();
    }

    private void dealInitialCards() {
        playerCards.add(Card.generateCard(cardsInPlay));
        playerCards.add(Card.generateCard(cardsInPlay));
        croupierCards.add(Card.generateCard(cardsInPlay));
    }

    public void addPlayerCard(Card card) {
        playerCards.add(card);
        cardsInPlay.add(card);
    }

    public void addCroupierCard(Card card) {
        croupierCards.add(card);
        cardsInPlay.add(card);
    }

    public int getPlayerScore() {
        return calculateScore(playerCards);
    }

    public int getCroupierScore() {
        return calculateScore(croupierCards);
    }

    private int calculateScore(List<Card> cards) {
        int score = 0;
        int aceCount = 0;

        for (Card card : cards) {
            switch (card.getValue()) {
                case "1": // Ace
                    aceCount++;
                    score += 11;
                    break;
                case "11": // Jack
                case "12": // Queen
                case "13": // King
                    score += 10;
                    break;
                default:
                    score += Integer.parseInt(card.getValue());
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }
}