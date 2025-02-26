import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

	@Test
	void test() {
		assertTrue(true);
	}

	//ThereCardLogic Class tests
	@Test
	void evalHandsHighCard(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 10);
		Card c2 = new Card('H', 2);
		Card c3 = new Card('S', 14);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(0, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}
	@Test
	void evalHandsStraightFlush(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 11);
		Card c2 = new Card('C', 12);
		Card c3 = new Card('C', 13);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(1, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}
	@Test
	void evalHandsThreeOfAKind(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('S', 10);
		Card c2 = new Card('C', 10);
		Card c3 = new Card('D', 10);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(2, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}
	@Test
	void evalHandsStraight(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 9);
		Card c2 = new Card('D', 7);
		Card c3 = new Card('S', 8);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(3, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}
	@Test
	void evalHandsFlush(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('H', 9);
		Card c2 = new Card('H', 4);
		Card c3 = new Card('H', 2);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);

		assertEquals(4, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}
	@Test
	void evalHandsOnePair(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('H', 9);
		Card c2 = new Card('D', 9);
		Card c3 = new Card('H', 2);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(5, ThreeCardLogic.evalHand(hand), "The evalHand method did not return the correct value(0) for a high card hand");
	}



	@Test
	void evalPPWinningsHighCard(){
		//should return 0
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 10);
		Card c2 = new Card('H', 2);
		Card c3 = new Card('S', 14);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);

		assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
	}

	@Test
	void evalPPWinningsStraightFlush(){
		//should return 40 * the bet
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 11);
		Card c2 = new Card('C', 12);
		Card c3 = new Card('C', 13);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(400, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
	}

	@Test
	void evalPPWinningsThreeOfAKind(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('S', 10);
		Card c2 = new Card('C', 10);
		Card c3 = new Card('D', 10);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(300, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
	}
	@Test
	void evalPPWinningsStraight(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('C', 9);
		Card c2 = new Card('D', 7);
		Card c3 = new Card('S', 8);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(60, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
	}
	@Test
	void evalPPWinningsFlush(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('H', 9);
		Card c2 = new Card('H', 4);
		Card c3 = new Card('H', 2);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);

		assertEquals(30, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
		ArrayList<Card> hand2 = new ArrayList<>();
		Card c4 = new Card('D', 9);
		Card c5 = new Card('D', 5);
		Card c6 = new Card('D', 14);

		hand2.add(c4);
		hand2.add(c5);
		hand2.add(c6);

		assertEquals(4, ThreeCardLogic.evalHand(hand2));
		assertEquals(36, ThreeCardLogic.evalPPWinnings(hand2, 12), "The evalPPWinnings method did not return the correct value");

	}
	@Test
	void evalPPWinningsOnePair(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card('H', 9);
		Card c2 = new Card('D', 9);
		Card c3 = new Card('H', 2);

		hand.add(c1);
		hand.add(c2);
		hand.add(c3);


		assertEquals(10, ThreeCardLogic.evalPPWinnings(hand, 10), "The evalPPWinnings method did not return the correct value");
	}

	@Test
	void compareHandsTest1(){
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 10);
		Card d2 = new Card('H', 6);
		Card d3 = new Card('D', 13);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('H', 5);
		Card p2 = new Card('S', 12);
		Card p3 = new Card('S', 13);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTest2(){
		ArrayList<Card> dealer = new ArrayList<>();
		//straight flush
		Card d1 = new Card('H', 14);
		Card d2 = new Card('H', 13);
		Card d3 = new Card('H', 12);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('H', 5);
		Card p2 = new Card('S', 12);
		Card p3 = new Card('S', 13);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(1, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}
	@Test
	void compareHandsTest3(){
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 4);
		Card d2 = new Card('H', 6);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//straight flush
		Card p1 = new Card('S', 11);
		Card p2 = new Card('S', 12);
		Card p3 = new Card('S', 13);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(1, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp4(){
		//high card -- dealer wins
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('D', 4);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 12);
		Card p2 = new Card('S', 2);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp5(){
		//high card -- player wins
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 10);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('D', 4);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 12);
		Card p2 = new Card('S', 2);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp6(){
		//high card same, player wins by second card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('D', 4);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 14);
		Card p2 = new Card('S', 2);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp7(){
		//high card same, dealer wins by second card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('D', 9);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 14);
		Card p2 = new Card('S', 2);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp8(){
		System.out.println("TEST 8");
		//high card same, player wins by third card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('H', 5);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 14);
		Card p2 = new Card('S', 4);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp9(){
		//high card same, dealer wins by third card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 4);
		Card d3 = new Card('H', 5);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 14);
		Card p2 = new Card('S', 2);
		Card p3 = new Card('D', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestHighCardComp10(){
		System.out.println("TEST 10");
		//high card same player and dealer tie
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('S', 14);
		Card d2 = new Card('H', 4);
		Card d3 = new Card('D', 5);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('D', 14);
		Card p2 = new Card('S', 4);
		Card p3 = new Card('S', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(0, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win
		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp11(){
		//dealer wins with higher flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 13);
		Card p2 = new Card('S', 6);
		Card p3 = new Card('S', 7);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp12(){
		//player wins with higher flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 13);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 6);
		Card p3 = new Card('S', 7);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp13(){
		//player wins with higher flush, second card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 6);
		Card p3 = new Card('S', 2);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp14(){
		//dealer wins with higher flush, second card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 4);
		Card p3 = new Card('S', 2);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp15(){
		//player wins with higher flush, third card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 5);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp16(){
		//dealer wins with higher flush, third card
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 4);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 5);
		Card p3 = new Card('S', 2);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestFlushComp17(){
		//dealer and player tie for flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 14);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 14);
		Card p2 = new Card('S', 5);
		Card p3 = new Card('S', 2);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(4, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(4, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win
		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightComp18(){
		//dealer wins with higher flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('H', 5);
		Card d3 = new Card('D', 6);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 2);
		Card p2 = new Card('C', 3);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(3, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(3, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightComp19(){
		//player wins with higher flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('H', 3);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 6);
		Card p2 = new Card('C', 5);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(3, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(3, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player  wins
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightComp20(){
		//Dealer and player have same level straight
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('H', 5);
		Card d3 = new Card('D', 6);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 6);
		Card p2 = new Card('C', 5);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(3, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(3, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win
		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightFlushComp21(){
		//dealer wins with higher straight flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 6);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 2);
		Card p2 = new Card('S', 3);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(1, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(1, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightFlushComp22(){
		//player wins with higher flush
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('D', 3);
		Card d3 = new Card('D', 2);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 6);
		Card p2 = new Card('S', 5);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(1, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(1, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player  wins
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestStraightFlushComp23(){
		//Dealer and player have same level straight
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 4);
		Card d2 = new Card('D', 5);
		Card d3 = new Card('D', 6);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('S', 6);
		Card p2 = new Card('S', 5);
		Card p3 = new Card('S', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(1, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(1, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win
		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestThreeOfAKindComp24(){
		//Dealer wins by higher three-of-a-kind
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 11);
		Card d2 = new Card('S', 11);
		Card d3 = new Card('C', 11);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('C', 4);
		Card p2 = new Card('S', 4);
		Card p3 = new Card('D', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(2, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(2, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}
	@Test
	void compareHandsTestThreeOfAKindComp25(){
		//player wins by higher three-of-a-kind
		ArrayList<Card> dealer = new ArrayList<>();
		//high card
		Card d1 = new Card('D', 3);
		Card d2 = new Card('S', 3);
		Card d3 = new Card('C', 3);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		//high card
		Card p1 = new Card('C', 4);
		Card p2 = new Card('S', 4);
		Card p3 = new Card('D', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(2, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(2, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestPairComp26(){
		//Dealer wins by higher pair
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 4);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 10);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 4);
		Card p2 = new Card('S', 9);
		Card p3 = new Card('D', 9);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(5, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestPairComp27(){
		//Player wins by higher pair
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 4);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 10);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 4);
		Card p2 = new Card('S', 11);
		Card p3 = new Card('D', 11);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(5, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestPairComp28(){
		//Dealer wins by pair, third card higher
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 8);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 10);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 7);
		Card p2 = new Card('C', 10);
		Card p3 = new Card('D', 10);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(5, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//dealer should win
		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestPairComp29(){
		//Player wins by pair, third card higher
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 4);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 10);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 7);
		Card p2 = new Card('C', 10);
		Card p3 = new Card('D', 10);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(5, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//Player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void compareHandsTestPairComp30(){
		//Tie by same pair and same value third card
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 4);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 10);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 4);
		Card p2 = new Card('C', 10);
		Card p3 = new Card('D', 10);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(5, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win --> tie
		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
	}

	@Test
	void evalPPWinningsExtra(){
		//Tie by same pair and same value third card
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('H', 4);
		Card d2 = new Card('S', 10);
		Card d3 = new Card('H', 9);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 2);
		Card p2 = new Card('C', 3);
		Card p3 = new Card('D', 4);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		//high cards
		assertEquals(0, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		assertEquals(3, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//neither should win --> tie
		assertEquals(60, ThreeCardLogic.evalPPWinnings(player, 10), "returns the wrong value for evalPPWinnings");
	}

	@Test
	void evalHandExtra(){
		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('C', 2);
		Card p2 = new Card('C', 3);
		Card p3 = new Card('C', 14);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		//straight flush
		assertEquals(1, ThreeCardLogic.evalHand(player), "EvalHand wrong");
	}

	@Test
	void compareHandsExtra(){
		ArrayList<Card> DHand = new ArrayList<>();

		Card d1 = new Card('H', 4, "h4.png");
		Card d2 = new Card('S', 10, "s10.png");
		Card d3 = new Card('H', 14, "h14.png");

		DHand.add(d1);
		DHand.add(d2);
		DHand.add(d3);

		ArrayList<Card> p1Test = new ArrayList<>();

		p1Test.add(d1);
		p1Test.add(d2);
		p1Test.add(d3);

		assertEquals(0, ThreeCardLogic.compareHands(DHand, p1Test));
	}

	@Test
	void compHandExtra2(){
		ArrayList<Card> dealer = new ArrayList<>();
		Card d1 = new Card('C', 4);
		Card d2 = new Card('H', 5);
		Card d3 = new Card('D', 5);

		dealer.add(d1);
		dealer.add(d2);
		dealer.add(d3);

		ArrayList<Card> player = new ArrayList<>();
		Card p1 = new Card('D', 7);
		Card p2 = new Card('D', 6);
		Card p3 = new Card('C', 5);

		player.add(p1);
		player.add(p2);
		player.add(p3);

		ArrayList<Card> player2 = new ArrayList<>();
		Card p2a = new Card('H', 4);
		Card p2b = new Card('H', 9);
		Card p2c = new Card('H', 6);

		player2.add(p2a);
		player2.add(p2b);
		player2.add(p2c);

		//pair-5
		assertEquals(5, ThreeCardLogic.evalHand(dealer), "EvalHand wrong");
		//straight-3
		assertEquals(3, ThreeCardLogic.evalHand(player), "EvalHand wrong");
		//flush-4
		assertEquals(4, ThreeCardLogic.evalHand(player2), "EvalHand wrong");
		//player should win
		assertEquals(true,ThreeCardLogic.evalHand(dealer) > ThreeCardLogic.evalHand(player));
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "returns the wrong value for compareHands");
		//player should win
		assertEquals(2, ThreeCardLogic.compareHands(dealer, player2), "returns the wrong value for compareHands");

	}


}
