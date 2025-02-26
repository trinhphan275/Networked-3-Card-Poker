import java.util.ArrayList;
import java.util.List;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();
    private int anteBet = 0;
    private int playBet = 0;
    private int pairPlusBet = 0;
    private int totalWinnings = 0;
    private boolean isFold = false;

    public Player() {}

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue(); // Add the value of each card
        }
        return value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    // Additional methods for handling bets, winnings, etc., can be added here
}
