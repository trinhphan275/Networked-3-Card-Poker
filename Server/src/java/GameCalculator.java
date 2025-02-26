import java.util.ArrayList;

public class GameCalculator {
    private static final double MIN_BET = 5.0;
    private static final double MAX_BET = 25.0;
    private static final int STRAIGHT_FLUSH_MULTIPLIER = 40;
    private static final int THREE_OF_KIND_MULTIPLIER = 30;
    private static final int STRAIGHT_MULTIPLIER = 6;
    private static final int FLUSH_MULTIPLIER = 3;
    private static final int PAIR_MULTIPLIER = 1;

    private ThreeCardLogic logic;

    public GameCalculator() {
        this.logic = new ThreeCardLogic();
    }


    public void calculateGameResult(PokerInfo gameInfo) {
        ArrayList<Card> dealerHand = gameInfo.getDealerCards();
        ArrayList<Card> playerHand = gameInfo.getPlayerCards();
        double totalWinnings = 0.0; // Start from 0

        // Handle Pair Plus bet first
        if (gameInfo.getPairPlusBet() > 0.0) {
            double pairPlusWinnings = calculatePairPlusWinnings(playerHand, gameInfo.getPairPlusBet());
            totalWinnings += pairPlusWinnings;
            System.out.println("pair plus winnings: " + pairPlusWinnings);
        }

        // Handle fold
        if (gameInfo.isPlayerFolded()) {
            handleFoldResult(gameInfo);
            return;
        }

        // Dealer qualification check
        boolean dealerQualified = isDealerQualified(dealerHand);
        gameInfo.setDealerQualified(dealerQualified);

        if (!dealerQualified) {
            totalWinnings += gameInfo.getAnteBet(); // Win ante bet
            // Play bet is returned
            gameInfo.setGameResult("Dealer didn't qualify - Ante bet won, Play bet returned");
        } else {
            int compareResult = ThreeCardLogic.compareHands(dealerHand, playerHand);
            processQualifiedDealerResult(gameInfo, compareResult, totalWinnings);
        }

        // Update total winnings with current game results
        gameInfo.updateTotalWinnings();
        gameInfo.setGameResult(formatGameResult(gameInfo, totalWinnings));
    }

    private boolean isValidBetAmount(double betAmount) {
        return betAmount >= MIN_BET && betAmount <= MAX_BET;
    }

    public double calculatePairPlusWinnings(ArrayList<Card> playerHand, double pairPlusBet) {
        int handValue = ThreeCardLogic.evalHand(playerHand);
        double winnings = 0.0;

        switch (handValue) {
            case 1:
                winnings = pairPlusBet * STRAIGHT_FLUSH_MULTIPLIER;
                break;
            case 2: // Flush
                winnings = pairPlusBet * FLUSH_MULTIPLIER;
                break;
            case 3: // Straight
                winnings = pairPlusBet * STRAIGHT_MULTIPLIER;
                break;
            case 4: // Three of a Kind
                winnings = pairPlusBet * THREE_OF_KIND_MULTIPLIER;
                break;
            case 5: // Pair
                winnings = pairPlusBet * PAIR_MULTIPLIER ;
                break;
            default:
                winnings = 0;
        }

        return winnings;
    }

    private void handleFoldResult(PokerInfo gameInfo) {
        double totalLoss = -gameInfo.getAnteBet();
        if (gameInfo.getPairPlusBet() > 0) {
            totalLoss -= gameInfo.getPairPlusBet();
        }

        gameInfo.setTotalWinnings(totalLoss);
        gameInfo.setGameResult(String.format("Player Folded - Lost $%.2f", Math.abs(totalLoss)));
        gameInfo.setPlayerWins(false);
    }

    public boolean isDealerQualified(ArrayList<Card> dealerHand) {
        int handValue = ThreeCardLogic.evalHand(dealerHand);
        if (handValue > 0) return true; // Any pair or better qualifies

        // Need at least Queen high
        return ThreeCardLogic.highestCardValue(dealerHand) >= 12;
    }


    private void processQualifiedDealerResult(PokerInfo gameInfo, int compareResult, double currentWinnings) {
        double anteBet = gameInfo.getAnteBet();
        double playBet = gameInfo.getPlayBet();

        switch (compareResult) {
            case 1: // Dealer wins
                currentWinnings -= (anteBet + playBet);
                System.out.println("Player loses bet: " + currentWinnings);
                gameInfo.setPlayerWins(false);
                gameInfo.setGameResult("Dealer wins");
                break;
            case 2: // Player wins
                currentWinnings += (anteBet + playBet);
                System.out.println("Player wins: " + currentWinnings);
                gameInfo.setPlayerWins(true);
                gameInfo.setGameResult("Player wins");
                break;
            case 0: // Tie
                gameInfo.setGameResult("Tie - bets returned");
                gameInfo.setPlayerWins(false);
                break;
        }

        gameInfo.setCurrentGameWinnings(currentWinnings);
    }

    private String formatGameResult(PokerInfo gameInfo, double pairPlusWinnings) {
        StringBuilder result = new StringBuilder();

        // Base game result
        if (gameInfo.isPlayerFolded()) {
            result.append("Player Folded");
        } else if (!gameInfo.isDealerQualified()) {
            result.append("Dealer didn't qualify Queen high - Play bet returned, Ante pushed");
        } else {
            result.append(gameInfo.isPlayerWins() ? "Player wins" :
                    (gameInfo.getGameResult().contains("Tie") ? "Tie" : "Dealer wins"));
        }

        // Add Pair Plus result if applicable
        if (gameInfo.getPairPlusBet() > 0) {
            result.append(String.format(" | Pair Plus: $%.2f", pairPlusWinnings));
        }

        // Add total winnings/losses
        result.append(String.format(" | Total: $%.2f", gameInfo.getTotalWinnings()));

        return result.toString();
    }

    public boolean isValidGameState(PokerInfo gameInfo) {
        if (gameInfo.getDealerCards().size() != 3 || gameInfo.getPlayerCards().size() != 3) {
            return false;
        }

        if (!isValidBetAmount(gameInfo.getAnteBet())) {
            return false;
        }

        if (!gameInfo.isPlayerFolded()) {
            if (gameInfo.getPlayBet() != gameInfo.getAnteBet()) {
                return false;
            }
        }

        if (gameInfo.getPairPlusBet() > 0 && !isValidBetAmount(gameInfo.getPairPlusBet())) {
            return false;
        }

        return true;
    }

    public ThreeCardLogic getLogic() {
        return logic;
    }

    public void setLogic(ThreeCardLogic logic) {
        this.logic = logic;
    }
}