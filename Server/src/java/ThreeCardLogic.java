import java.util.ArrayList;

import java.util.Comparator;

public class ThreeCardLogic {

    public static int evalHand(ArrayList<Card> hand){
        /*
        The method evalHand will return an integer
        value representing the value of the hand passed in. It will return:
        • 0 if the hand just has a high card
        • 1 for a straight flush
        • 2 for three of a kind
        • 3 for a straight
        • 4 for a flush
        • 5 for a pair
        */
        hand.sort(Comparator.comparingInt(Card::getValue));

        if((hand.get(0).getValue() == hand.get(1).getValue()) || (hand.get(0).getValue() == hand.get(2).getValue()) || (hand.get(2).getValue() == hand.get(1).getValue())){
            //one pair
            if((hand.get(0).getValue() == hand.get(1).getValue()) && (hand.get(0).getValue() == hand.get(2).getValue()) && (hand.get(2).getValue() == hand.get(1).getValue())){
                //three of kind
                return 2;
            }
            return 5;
        }


        boolean isStraight = (hand.get(1).getValue() == hand.get(0).getValue() + 1) && (hand.get(2).getValue() == hand.get(1).getValue() + 1);
        //Accounts for A, 2, 3 pairing
        if(hand.get(0).getValue() == 2 && hand.get(1).getValue() == 3 && hand.get(2).getValue() == 14){
            isStraight = true;
        }
        boolean isFlush = (hand.get(0).getSuit() == hand.get(1).getSuit()) && (hand.get(0).getSuit() == hand.get(2).getSuit());

        if(isStraight && isFlush){
            //straight flush
            return 1;
        } else
        if(isFlush){
            //flush
            return 4;
        }
        else if(isStraight){
            //straight
            return 3;
        }
        return 0;
    }

    public static int evalPPWinnings(ArrayList<Card> hand, int bet){
        /*
        The method evalPPWinnings will return the amount won for the PairPlus bet. It will
        evaluate the hand and then evaluate the winnings and return the amount won. If the
        player lost the Pair Plus bet, it will just return 0.
        */
        int handValue = evalHand(hand);

        if(handValue == 0){
            //high card
            //no winnings
            return 0;
        } else if(handValue == 1){
            //straight flush -- 40 to 1
            return bet * 40;
        } else if(handValue == 2){
            //three of a kind -- 30 to 1
            return bet * 30;
        } else if(handValue == 3){
            //straight  -- 6 to 1
            return bet * 6;
        } else if(handValue == 4){
            // flush -- 3 to 1
            return bet * 3;
        } else if(handValue == 5){
            //one pair
            return bet;
        }
        return 0;
    }

    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){
        /*The method compareHands will compare the two hands passed in and return an
        integer based on which hand won:
        • 0 if neither hand won
        • 1 if the dealer hand won
        • 2 if the player hand won*/

        int dealerValue = evalHand(dealer);
        int playerValue  = evalHand(player);
        if(dealerValue != 0 && (dealerValue < playerValue)){
            //not a high hand and the dealer value is less as it has higher value
            return 1;
        } else if((dealerValue != 0) && (dealerValue > playerValue) && (playerValue != 0)){
            //not a high hand and the dealer value is less as it has higher value
            return 2;
        } else if((dealerValue == 0) && (playerValue > 0)){
            return 2;
        } else if((playerValue == 0) && (dealerValue > 0)){
            return 1;
        } else if(dealerValue == playerValue){
            System.out.println("same value");
            if(dealerValue == 0 || dealerValue == 4 || dealerValue == 3 || dealerValue == 1){
                //0 --> both hands are high cards, return which one was higher
                //4 --> both have flush, return highest card
                //3 --> both have straights, return highest card
                //1 --> staright flush, return higher card
//                System.out.println("high card");
//                System.out.println("dealer: " + highestCardValue(dealer));
//                System.out.println("player: " + highestCardValue(player));
                if(highestCardValue(dealer) > highestCardValue(player)){
                    //dealer won
                    System.out.println("Where ");
                    return 1;
                } else if (highestCardValue(dealer) < highestCardValue(player)){
                    return 2;
                } else if(highestCardValue(dealer) == highestCardValue(player)){
                    //same high card
                    //get second card value
                    if(getSecondHighestCard(dealer) > getSecondHighestCard(player)){
                        //dealer won

                        System.out.println("Where 2 ");
                        return 1;
                    } else if(getSecondHighestCard(dealer) < getSecondHighestCard(player)){
                        return 2;
                    } else {
                        //tie --> third highest card
                        if(getThirdHighestCard(dealer) > getThirdHighestCard(player)){
                            //dealer won

                            System.out.println("Where 3");
                            return 1;
                        } else if(getThirdHighestCard(dealer) < getThirdHighestCard(player)){
                            return 2;
                        } else {
                            //tie
                            return 0;
                        }
                    }
                }
            }
            else if(dealerValue == 5){
                //5 --> both have pairs
                //find pair, find value
                System.out.println("Dealer pair val: " + pairValue(dealer));
                System.out.println("Player pair val: " + pairValue(player));
                if(pairValue(player) < pairValue(dealer)){
                    //dealer won
                    return 1;
                } else if (pairValue(player) > pairValue(dealer)){
                    return 2;
                } else if(pairValue(player) == pairValue(dealer)){
                    //tie --> get third card
                    System.out.println("Dealer 3rd card: " + getThirdHighestCard(dealer));
                    System.out.println("Player 3rd card: " + getThirdHighestCard(player));
                    if(getThirdHighestCard(dealer) > getThirdHighestCard(player)){
                        //dealer won
                        return 1;
                    } else if(getThirdHighestCard(dealer) < getThirdHighestCard(player)){
                        return 2;
                    } else {
                        //tie
                        return 0;
                    }
                }
            }
            else if(dealerValue == 2){
                //2 --> both have three-of-a-kind, return highest card
                if(dealer.get(0).getValue() > player.get(0).getValue()){
                    //dealer won
                    return 1;
                } else if (dealer.get(0).getValue() < player.get(0).getValue()){
                    return 2;
                } else if(dealer.get(0).getValue() == player.get(0).getValue()) {
                    //same value for the three-of-a-kind --> tie
                    return 0;
                }
            }

            //at this point we have gone through the possibilities and the dealer and the player are truly tied. Thus,
            //put this pot to the side and play the next game for it. True tie.
        }
        return 0;
    }

    public static int highestCardValue(ArrayList<Card> c){
        //returns the value of the highest card in a hand
        Card highestCard = c.get(0);
        for (int i = 1; i < 3; i++) {
            if (c.get(i).getValue() > highestCard.getValue()) {
                highestCard = c.get(i);
            }
        }
        return highestCard.getValue();
    }
    public static int getSecondHighestCard(ArrayList<Card> c){
        //returns the value of the second highest card in a hand
        c.sort(Comparator.comparingInt(Card::getValue));
        return c.get(1).getValue(); //middle card
    }

    public static int getThirdHighestCard(ArrayList<Card> c){
        //returns the value of the third highest card in a hand
        c.sort(Comparator.comparingInt(Card::getValue));
        return c.get(0).getValue();
    }
    public static int pairValue(ArrayList<Card> c){
        //returns the value of a pair
        if(c.get(0).getValue() == c.get(1).getValue()){
            return c.get(0).getValue();
        } else if(c.get(0).getValue() == c.get(2).getValue()){
            return c.get(0).getValue();
        } else if(c.get(1).getValue() == c.get(2).getValue()){
            return c.get(1).getValue();
        }
        return -1;
    }


}
