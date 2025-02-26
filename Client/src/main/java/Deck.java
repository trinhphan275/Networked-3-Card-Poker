import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        this.newDeck();
    }

    public void newDeck() {
        this.cards.clear();
        char[] suits = {'C', 'D', 'H', 'S'}; // Clubs, Diamonds, Hearts, Spades

        for (char suit : suits) {
            for (int value = 2; value <= 14; value++) { // Cards from 2 to Ace (14)
                cards.add(new Card(suit, value));
            }
        }

        Collections.shuffle(this.cards); // Shuffle the deck after creating it
    }

    public ArrayList<Card> dealCards(int num) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            hand.add(cards.remove(0)); // Deal cards from the top of the deck
        }
        return (ArrayList<Card>) hand;
    }

    public void shuffle() {
        Collections.shuffle(this.cards); // Shuffle the deck
    }

    public int deckSize() {
        return this.cards.size(); // Return the size of the deck
    }
}
