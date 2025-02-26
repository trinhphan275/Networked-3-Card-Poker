import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private Deck deck = new Deck();
    private ArrayList<Card> hand = new ArrayList<>();

    public Dealer() {}

    public ArrayList<Card> dealHand() {
        if (deck.deckSize() <= 34) { // Re-shuffle the deck if fewer than 34 cards remain
            deck.newDeck();
        }

        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hand.add(deck.dealCards(1).get(0)); // Deal 3 cards
        }

        hand.sort((card1, card2) -> Integer.compare(card2.getValue(), card1.getValue())); // Sort in descending order of value
        return hand;
    }

    public String calculateGameResult(Player player) {
        int playerValue = player.getHandValue();
        int dealerValue = this.getHandValue();
        if (playerValue > dealerValue) {
            return "Player wins!";
        } else if (playerValue < dealerValue) {
            return "Dealer wins!";
        } else {
            return "It's a tie!";
        }
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue(); // Add the value of each card
        }
        return value;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
