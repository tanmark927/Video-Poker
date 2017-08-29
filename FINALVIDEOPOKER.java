package pokerthree;
import java.util.*;

/**
 * A Video Poker game.
 * @version 12/08/15
 */
public class FINALVIDEOPOKER
{
	Scanner in = new Scanner(System.in);
	Scanner play = new Scanner(System.in);
	
	private int[][] cards = new int[4][13];
	private int[][] tempArray = new int[4][13];
	private int[] aceToKing = new int[13];
	
	private int[] hand = new int[5];
	private int[] handSuit = new int[5];
	
	private double pot;
	private double potOriginal;
	private double wager;
	private double earnings;
	
	private int tempX;
	private int tempY;
	
	private int round;
	private int deckSize;
	private int cardsTaken;
	private int numRejectCards;
	private int rejectedCard;
	
	private int A;
	private int J;
	private int Q;
	private int K;
	
	private int numPairs;
	private int threeOfAKind;
	
	private boolean continueGame;
	private boolean keepPlaying;
	private boolean correctAnswer;
	
	/**
	 * Constructs a Video Poker game.
	 */
	public FINALVIDEOPOKER(int pokerMoney)
	{
		for (int x = 0; x < cards.length; x++)
		{
			for (int y = 0; y < cards[0].length; y++)
			{
				cards[x][y] = y + 1;
				tempArray[x][y] = 0;
				aceToKing[y] = y + 1;
			}
		}
		
		for (int x = 0; x < hand.length; x++)
		{
			hand[x] = 0;
			handSuit[x] = 0;
		}
		
		potOriginal = pokerMoney;
		pot = potOriginal;
		wager = 0;
		earnings = 0;
		
		tempX = (int)(Math.random()*4);
		tempY = (int)(Math.random()*13);
		
		round = 1;
		deckSize = 52;
		cardsTaken = 0;
		numRejectCards = 0;
		rejectedCard = 0;
		
		A = 1;
		J = 11;
		Q = 12;
		K = 13;
		
		numPairs = 0;
		threeOfAKind = 0;
		
		continueGame = false;
		keepPlaying = true;
		correctAnswer = false;
	}
	
	/**
	 * Shuffles int[][] cards by setting each of the indices within an int[] of cards to 0
	 * and replaces them with indices from int[] aceToKing picked at random.
	 * Once it is finished shuffling an int[] of cards, it resets aceToKing to its proper order
	 * and repeats for all int[] of cards.
	 * deckSize is reset to 52.
	 */
	public void shuffle()
	{
		int temp = 0;
		
		for (int x = 0; x < cards.length; x++)
		{
			for (int y = 0; y < cards[0].length; y++)
			{
				cards[x][y] = 0;
			}
			
			for (int y = 0; y < cards[0].length; y++)
			{
				while (cards[x][y] == 0)
				{
					temp = (int)(Math.random()*13);
					cards[x][y] = aceToKing[temp];
					aceToKing[temp] = 0;
				}
			}
			
			for (int y = 0; y < aceToKing.length; y++)
			{
				aceToKing[y] = y + 1;
			}
		}
		deckSize = 52;
	}
	
	/**
	 * Adds earnings to the pot and notifies the Player how much he or she has won
	 * and what his or her current pot is.
	 */
	public void updatePot()
	{
		pot = pot + earnings;
		
		System.out.print("You currently have $");
		System.out.printf("%.2f", pot);
		System.out.println(" in your pot.");
		
		if (pot - potOriginal < 0)
		{
			System.out.print("You have lost $");
			System.out.printf("%.2f", potOriginal - pot);
			System.out.println(".");
		}
		else
		{
			System.out.print("You have won $");
			System.out.printf("%.2f", pot - potOriginal);
			System.out.println(".");
		}
	}
	
	/**
	 * Gives us access to the private int pot.
	 * @return pot, which is how much money the Player currently has available.
	 */
	public double getPot()
	{
		return pot;
	}
	
	/**
	 * Gives us access to the private int potOriginal.
	 * @return potOriginal, which is how much money the Player begins with.
	 * In this case, the Player begins with $50.00.
	 */
	public double getPotOriginal()
	{
		return potOriginal;
	}
	
	/**
	 * Asks the Player how much he or she would like to wager.
	 * This amount must be at least $1.00 and no more than the value of the pot.
	 */
	public void wager()
	{
		double amount = 0;
		
		System.out.print("How much you would like to wager? ");
		
		while (correctAnswer == false)
		{
			while(!in.hasNextDouble())
			{
				if (in.hasNextDouble())
				{
					amount = in.nextDouble();
				}
				in.next();
			}
			amount = in.nextDouble();
			
			if (amount >= 1 && amount <= pot)
			{
				correctAnswer = true;
			}
			else
			{
				System.out.println();
				System.out.println("You must wager at least $1.00 and no more than what you currently have in your pot!");
				System.out.print("How much would you like to wager? ");
			}
		}
		correctAnswer = false;
		
		wager = amount;
	}
	
	/**
	 * Gives us access to the private int[][] cards.
	 * The first int[] of cards is hearts, the second int[] of cards is diamonds,
	 * the third int[] of cards is spades, and the fourth int[] of cards is clubs.
	 * Each int[] of cards has length 13.
	 */
	public void getCards()
	{
		for (int x = 0; x < cards.length; x++)
		{
			for (int y = 0; y < cards[0].length; y++)
			{
				System.out.print(cards[x][y] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Sets the values of the hand and their corresponding suits manually for testing purposes.
	 */
	public void testDraw()
	{
		hand[0] = 13;
		handSuit[0] = 1;
		hand[1] = 12;
		handSuit[1] = 2;
		hand[2] = 1;
		handSuit[2] = 0;
		hand[3] = 11;
		handSuit[3] = 0;
		hand[4] = 2;
		handSuit[4] = 3;
	}
	
	/**
	 * Sets each of the indices within an int[] of tempArray to 0.
	 * Then, an index of the tempArray is filled with a random value picked from cards.
	 * This value is then passed into the int[] hand,
	 * and the corresponding suit of the value is passed into the int[] handSuit.
	 * This is repeated until the hand is full.
	 * int deckSize and int cardsTaken are then updated, and the hand is ordered.
	 */
	public void draw()
	{		
		for (int x = 0; x < tempArray.length; x++)
		{
			for (int y = 0; y < tempArray[0].length; y++)
			{
				tempArray[x][y] = 0;
			}
		}
		
		while (cardsTaken < 5 && tempArray[tempX][tempY] == 0)
		{			
			tempArray[tempX][tempY] = cards[tempX][tempY];
			hand[cardsTaken] = tempArray[tempX][tempY];
			handSuit[cardsTaken] = tempX;
			
			while (tempArray[tempX][tempY] == cards[tempX][tempY])
			{
				tempX = (int)(Math.random()*4);
				tempY = (int)(Math.random()*13);
			}
			
			cardsTaken++;
		}
		deckSize = deckSize - cardsTaken;
		cardsTaken = 0;
		orderHand();
	}
	
	/**
	 * Asks the Player how many cards he or she would like to replace from his or her hand,
	 * and sets int numRejectCards equal to this number.
	 */
	public void numRejectCards()
	{
		System.out.print("How many cards you would like to replace ('0,' '1,' '2,' '3,' '4,' or '5')? ");
		
		while (correctAnswer == false)
		{
			String number = in.next();
			
			if (number.equals("0") || number.equals("1") || number.equals("2") ||
				number.equals("3") || number.equals("4") || number.equals("5"))
			{
				numRejectCards = Integer.parseInt(number);
				correctAnswer = true;
			}
		}
		correctAnswer = false;
	}
	
	/**
	 * Gives us access to the private int numRejectCards
	 * @return numRejectCards, which is how many cards the Player would like to remove from his or her hand.
	 */
	public int getNumRejectCards()
	{
		return numRejectCards;
	}
	
	/**
	 * Asks the Player which indices of his or her hand he or she would like to replace
	 * for the value of numRejectCards.
	 * Then, an index of the tempArray is filled with a random value picked from cards.
	 * This value is then passed into the hand,
	 * and the corresponding suit of the value is passed into the handSuit.
	 * deckSize, cardsTaken, and numRejectCards are then updated, and the hand is ordered.
	 */
	public void reDraw()
	{		
		while (cardsTaken < numRejectCards && tempArray[tempX][tempY] == 0)
		{
			while (correctAnswer == false)
			{
				String number = in.next();
				
				if (number.equals("1") || number.equals("2") || number.equals("3")
				 || number.equals("4") || number.equals("5"))
				{
					rejectedCard = Integer.parseInt(number);
					correctAnswer = true;
				}
			}
			correctAnswer = false;
			
			tempArray[tempX][tempY] = cards[tempX][tempY];
			hand[rejectedCard - 1] = tempArray[tempX][tempY];
			handSuit[cardsTaken] = tempX;
			
			while (tempArray[tempX][tempY] == cards[tempX][tempY])
			{
				tempX = (int)(Math.random()*4);
				tempY = (int)(Math.random()*13);
			}
			
			cardsTaken++;
		}
		deckSize = deckSize - cardsTaken;
		cardsTaken = 0;
		numRejectCards = 0;
		orderHand();
	}
	
	/**
	 * Rearranges the hand, and the corresponding suit of each value of hand,
	 * in ascending order (from smallest to biggest).
	 */
	public void orderHand()
	{
		int tempSuit = 0;
		int tempCard = 0;
		
		for (int x = 0; x < hand.length; x++)
		{
			for (int y = 0; y < hand.length; y++)
			{
				while(hand[x] < hand[y])
				{
					tempCard = hand[x];
					tempSuit = handSuit[x];
					hand[x] = hand[y];
					hand[y] = tempCard;
					handSuit[x] = handSuit[y];
					handSuit[y] = tempSuit;
				}
			}
		}
	}
	
	/**
	 * Gives us access to the private int round.
	 * @return round, which is what round the Player is currently on.
	 */
	public int getRound()
	{
		return round;
	}
	
	/**
	 * Gives us access to the private int deckSize.
	 * @return deckSize, which starts at 52 and decreases every time cards are drawn.
	 */
	public int getDeckSize()
	{
		return deckSize;
	}
	
	/**
	 * Allows the Player to see what cards are in his or her hand.
	 */
	public void getHand()
	{
		for (int x = 0; x < hand.length; x++)
		{
			if (hand[x] == A)
			{
				System.out.print("A");
			}
			else if (hand[x] == J)
			{
				System.out.print("J");
			}
			else if (hand[x] == Q)
			{
				System.out.print("Q");
			}
			else if (hand[x] == K)
			{
				System.out.print("K");
			}
			else
			{
				System.out.print(hand[x]);
			}
			
			if (handSuit[x] == 0)
			{
				System.out.print("<3 ");
			}
			else if (handSuit[x] == 1)
			{
				System.out.print("<> ");
			}
			else if (handSuit[x] == 2)
			{
				System.out.print("<k ");
			}
			else if (handSuit[x] == 3)
			{
				System.out.print("c3 ");
			}
		}
		System.out.println();
	}
	
	/**
	 * Checks if the Player has a valid combination in his or her hand,
	 * and calculates the payout.
	 */
	public void checkHand()
	{
		if (royalFlush() == true)
		{
			System.out.println("You got a royal flush!");
			earnings = wager*2.5;
		}
		else if (straightFlush() == true)
		{
			System.out.println("You got a straight flush!");
			earnings = wager*.5;
		}
		else if (fourOfAKind() == true)
		{
			System.out.println("You got four of a kind!");
			earnings = wager*.25;
		}
		else if (fullHouse() == true)
		{
			System.out.println("You got a full house!");
			earnings = wager*.06;
		}
		else if (flush() == true)
		{
			System.out.println("You got a flush!");
			earnings = wager*.05;
		}
		else if (straight() == true)
		{
			System.out.println("You got a straight!");
			earnings = wager*.04;
		}
		else if (threeOfAKind() == true)
		{
			System.out.println("You got three of a kind!");
			earnings = wager*.03;
		}
		else if (twoPairs() == true)
		{
			System.out.println("You got two pairs!");
			earnings = wager*.02;
		}
		else if (onePair() == true)
		{
			System.out.println("You got one pair!");
			earnings = wager*.01;
		}
		else
		{
			System.out.println("You got no pair!");
			earnings = -wager;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a pair.
	 * @return true, if there is only one pair, else it returns false.
	 * int numPairs is reset to 0.
	 */
	public boolean onePair()
	{		
		for (int x = 0; x < hand.length - 1; x++)
		{
			for (int y = x + 1; y < hand.length; y++)
			{
				if (hand[x] == hand[y])
				{
					numPairs++;
				}
			}
		}
		
		if (numPairs == 1)
		{
			numPairs = 0;
			return true;
		}
		else
		{
			numPairs = 0;
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has two pairs.
	 * @return true if it has two pairs of different numbers, else it returns false.
	 * int numPairs is reset to 0.
	 */
	public boolean twoPairs()
	{
		int pair1 = 0;
		int pair2 = 0;
		int count = 0;
		
		for (int x = 0; x < hand.length - 1; x++)
		{
			for (int y = x + 1; y < hand.length; y++)
			{
				if (hand[x] == hand[y])
				{
					if (count == 0)
					{
						pair1 = hand[x];
					}
					else if (hand[x] != pair1 && count == 1)
					{
						pair2 = hand[x];
					}
				
					numPairs++;
					count++;
				}
			}
		}
		
		if (pair1 != pair2 && numPairs == 2)
		{
			numPairs = 0;
			return true;
		}
		else
		{
			numPairs = 0;
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a three of a kind.
	 * @return true if three pairs of cards are equal and if threeOfAKind equals 3, else it returns false.
	 * int threeOfAKind is reset to 0.
	 */
	public boolean threeOfAKind()
	{
		int pair1 = 0;
		int pair2 = 0;
		int pair3 = 0;
		int count = 0;
		
		for (int x = 0; x < hand.length - 1; x++)
		{
			for (int y = x + 1; y < hand.length; y++)
			{
				if (hand[x] == hand[y])
				{
					if (count == 0)
					{
						pair1 = hand[x];
					}
					else if (count == 1)
					{
						pair2 = hand[x];
					}
					else if (count == 2)
					{
						pair3 = hand[x];
					}
				
					threeOfAKind++;
					count++;
				}
			}
		}
		
		if (pair1 == pair2 && pair2 == pair3 && pair3 == pair1 && threeOfAKind == 3)
		{
			threeOfAKind = 0;
			return true;
		}
		else
		{
			threeOfAKind = 0;
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a straight.
	 * @return true if count equals 5, else it returns false.
	 */
	public boolean straight()
	{
		int count = 0;
		
		for (int x = 4; x > 0; x--)
		{
			if (hand[x] - hand[x - 1] == 1)
			{
				count++;
			}
		}
		count++;
		
		if (hand[0] == 1 && hand[4] == 13)
		{
			count++;
		}
		
		if (count == 5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a flush.
	 * @return true if count equals 5 else it returns false.
	 */
	public boolean flush()
	{
		int count = 0;
		
		for (int x = 0; x < handSuit.length - 1; x++)
		{
			if (handSuit[x] == handSuit[x + 1])
			{
				count++;
			}
		}
		count++;
		
		if (count == 5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a full house.
	 * @return true if two pairs of cards are equal and if threeOfAKind equals 3, else it returns false.
	 * int threeOfAKind is reset to 0.
	 */
	public boolean fullHouse()
	{
		int pair1 = 0;
		int pair2 = 0;
		int pair3 = 0;
		int count = 0;
		
		for (int x = 0; x < hand.length - 1; x++)
		{
			for (int y = x + 1; y < hand.length; y++)
			{
				if (hand[x] == hand[y])
				{
					if (count == 0)
					{
						pair1 = hand[x];
					}
					else if (hand[x] == pair1 && count == 1)
					{
						pair2 = hand[x];
					}
					else if (hand[x] == pair2 || hand[x] == pair1 && count == 2)
					{
						pair3 = hand[x];
					}
					
					threeOfAKind++;
					count++;
					y++;
				}
			}
		}
		
		if ((pair1 == pair2 || pair2 == pair3 || pair3 == pair1) && threeOfAKind == 3)
		{
			threeOfAKind = 0;
			return true;
		}
		else
		{
			threeOfAKind = 0;
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a four of a kind.
	 * @return true if numPairs equals 4, else it returns false.
	 * int numPairs is reset to 0.
	 */
	public boolean fourOfAKind()
	{
		int pair1 = 0;
		int pair2 = 0;
		int count = 0;
		
		for (int x = 0; x < hand.length - 1; x++)
		{
			for (int y = x + 1; y < hand.length; y++)
			{
				if (hand[x] == hand[y])
				{
					if (count == 0)
					{
						pair1 = hand[x];
					}
					else if (hand[x] == pair1 && count == 1)
					{
						pair2 = hand[x];
					}
				
					numPairs++;
					count++;
					y++;
				}
			}
		}
		
		if (pair1 == pair2 && numPairs == 4)
		{
			numPairs = 0;
			return true;
		}
		else
		{
			numPairs = 0;
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a straight flush.
	 * @return true if straight and flush are equal to true, else it returns false.
	 */
	public boolean straightFlush()
	{
		if (straight() == true && flush() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//DESCRIBE THIS METHOD
	
	/**
	 * Checks to see if the hand has a royal flush.
	 * @return true if '10,' 'J,' 'Q,' 'K,' and 'A' are present in the hand and if flush is equal to true,
	 * else it returns false.
	 */
	public boolean royalFlush()
	{
		int count = 0;
		
		for (int x = 0; x < hand.length; x++)
		{
			if (hand[x] == 10 || hand[x] == 11 || hand[x] == 12 || hand[x] == 13 || hand[x] == 1)
			{
				count++;
			}
		}
		
		if (flush() == true && count == 5)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Allows the Player to control when the text appears.
	 */
	public void continueGame()
	{
		while (continueGame == false)
		{
			if (play.next().equals("-"))
			{
				continueGame = true;
			}
		}	
		continueGame = false;
	}
	
	/**
	 * Asks the Player if he or she wants to keep playing.
	 * If 'yes,' then round will be increased by one,
	 * else if 'no,' then the Player leaves the table.
	 */
	public void keepPlaying()
	{
		System.out.print("Would you like to keep playing ('yes' or 'no')? ");
		
		while (correctAnswer == false)
		{
			String phrase = play.next();
			
			if (phrase.equals("yes"))
			{
				keepPlaying = true;
				round++;
				correctAnswer = true;
			}
			else if (phrase.equals("no"))
			{
				keepPlaying = false;
				correctAnswer = true;
			}
		}
		correctAnswer = false;
	}
	
	/**
	 * Checks to see if the Player's pot has reached 0 or if the Player has left the table.
	 * @return true if either of the things above are true, else it returns false.
	 */
	public boolean getGameOver()
	{
		if (pot == 0 || keepPlaying == false)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
