import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    private GameState gameState;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;

    // Store hands as arrays of strings that represent the image paths
    private String[] playerHandStr;
    private String[] dealerHandStr;

    private boolean dealerHandVisible;
    private String playerHandRank;
    private String dealerHandRank;

    // Betting information
    private double anteBet;
    private double pairPlusBet;
    private double playBet;
    private double currentGameWinnings;
    private double totalWinnings;

    // Game status
    private boolean playerFolded;
    private boolean playerWins;
    private String gameResult;
    private boolean playAnotherGame;
    private boolean dealerQualified;

    // constructor to initialize all fields
    public PokerInfo() {
        this.playerHandStr = new String[3];
        this.dealerHandStr = new String[3];
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        this.totalWinnings = 0.0;
        this.dealerQualified = false;
        this.playerWins = false;
        this.playerFolded = false;
        this.playAnotherGame = false;
        this.dealerHandVisible = false;
        this.pairPlusWinnings = 0.0;
    }


    // Getters and Setters
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setPlayerHand(String[] playerHand) {
        this.playerHandStr = playerHand;
    }

    public void setDealerHand(String[] dealerHand) {
        this.dealerHandStr = dealerHand;
    }

    // Get the actual Card objects
    public ArrayList<Card> getPlayerCards() {
        return playerHand;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerHand;
    }

    // Set the actual Card objects
    public void setPlayerCards(ArrayList<Card> cards) {
        this.playerHand = cards;
    }

    public void setDealerCards(ArrayList<Card> cards) {
        this.dealerHand = cards;
    }

    public String[] getPlayerHand() {
        return playerHandStr;
    }

    public String[] getDealerHand() {
        return dealerHandStr;
    }
    public boolean isDealerHandVisible() {
        return dealerHandVisible;
    }

    public void setDealerHandVisible(boolean dealerHandVisible) {
        this.dealerHandVisible = dealerHandVisible;
    }

    public double getAnteBet() {
        return anteBet;
    }

    public void setAnteBet(double anteBet) {
        this.anteBet = anteBet;
    }

    public double getPairPlusBet() {
        return pairPlusBet;
    }

    public void setPairPlusBet(double pairPlusBet) {
        this.pairPlusBet = pairPlusBet;
    }

    // Add these methods to your PokerInfo class
    public void setDecision(String decision) {
        if (decision.equals("Play")) {
            this.playerFolded = false;
            this.playBet = this.anteBet; // Play bet equals ante bet
        }
        else if (decision.equals("Fold")) {
            this.playerFolded = true;
            this.playBet = 0; // No play bet when folding
        }
    }

    public boolean getDecision() {
        return playerFolded;
    }

    public double getPlayBet() {
        return playBet;
    }

    public void setPlayBet(double playBet) {
        this.playBet = playBet;
    }

    public boolean isPlayerFolded() {
        return playerFolded;
    }

    public void setPlayerFolded(boolean playerFolded) {
        this.playerFolded = playerFolded;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public boolean isPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(boolean playerWins) {
        this.playerWins = playerWins;
    }

    public double getCurrentGameWinnings() {
        return currentGameWinnings;
    }

    public void setCurrentGameWinnings(double amount) {
        this.currentGameWinnings = amount;
    }


    public double getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(double totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public boolean isPlayAnotherGame() {
        return playAnotherGame;
    }

    // Fix the dealer qualification methods
    public void setDealerQualified(boolean qualified) {
        this.dealerQualified = qualified;
    }

    public boolean isDealerQualified() {
        return dealerQualified; // Fix variable name from 'qualified' to 'dealerQualified'
    }

    // Add method to update total winnings with current game results
    public void updateTotalWinnings() {
        this.totalWinnings += this.currentGameWinnings;
    }

    // Add method for pair plus winnings if needed
    private double pairPlusWinnings;
    public double getPairPlusWinnings() {
        return pairPlusWinnings;
    }
    public void setPairPlusWinnings(double pairPlusWinnings) {
        this.pairPlusWinnings = pairPlusWinnings;
    }


    public void setPlayAnotherGame(boolean playAnotherGame) {
        this.playAnotherGame = playAnotherGame;
    }

    // Helper methods
    public void resetBets() {
        this.anteBet = 0;
        this.pairPlusBet = 0;
        this.playBet = 0;
    }

    public void resetHands() {
        this.playerHand.clear();
        this.dealerHand.clear();
        this.playerHandRank = null;
        this.dealerHandRank = null;
    }

    public void resetGameState() {
        this.gameState = GameState.WAITING_FOR_BET;
        this.playerFolded = false;
        this.playerWins = false;
        this.gameResult = null;
        this.dealerHandVisible = false;
        this.playAnotherGame = false;
        this.currentGameWinnings = 0.0;
    }



    // Override toString method for debugging
    @Override
    public String toString() {
        return "PokerInfo{" +
                "gameState=" + gameState +
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                ", dealerHandVisible=" + dealerHandVisible +
                ", anteBet=" + anteBet +
                ", pairPlusBet=" + pairPlusBet +
                ", playBet=" + playBet +
                ", totalWinnings=" + totalWinnings +
                ", playerFolded=" + playerFolded +
                ", playerWins=" + playerWins +
                ", gameResult='" + gameResult + '\'' +
                ", playerHandRank='" + playerHandRank + '\'' +
                ", dealerHandRank='" + dealerHandRank + '\'' +
                ", playAnotherGame=" + playAnotherGame +
                '}';
    }


}