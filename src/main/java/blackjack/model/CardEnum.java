package blackjack.model;

public enum CardEnum {
    HEARTS("Hearts"),
    CLUBS("Clubs"),
    SPADES("Spades"),
    DIAMONDS("Diamonds");

    private final String displayName;

    CardEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}