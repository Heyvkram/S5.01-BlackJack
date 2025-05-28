package blackjack.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Data
@NoArgsConstructor
public class Card {
    private CardEnum suit;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return suit == card.suit && value.equals(card.value);
    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    private static final Random random = new Random();
    private static final List<CardEnum> suits = Arrays.asList(CardEnum.values());
    private static final List<String> possibleValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13");
    private static final int TOTAL_CARDS = suits.size() * possibleValues.size();

    public static Card generateCard(Set<Card> drawnCards) {
        if (drawnCards.size() >= TOTAL_CARDS) {
            throw new IllegalStateException("All cards have been drawn");
        }

        Card card;
        do {
            CardEnum randomSuit = suits.get(random.nextInt(suits.size()));
            String randomValue = possibleValues.get(random.nextInt(possibleValues.size()));
            card = new Card();
            card.setSuit(randomSuit);
            card.setValue(randomValue);
        } while (drawnCards.contains(card));

        drawnCards.add(card);
        return card;
    }

    @Override
    public String toString() {
        return suit + " " + getValueDisplay();
    }

    public String getValueDisplay() {
        return switch (value) {
            case "1" -> "A";
            case "11" -> "J";
            case "12" -> "Q";
            case "13" -> "K";
            default -> value;
        };
    }
}