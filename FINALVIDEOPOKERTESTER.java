package pokerthree;
/**
 * Creates a Video Poker object and lets you play the game.
 * @version 12/08/15
 */
public class FINALVIDEOPOKERTESTER
{
	public static void main (String[] args)
	{
		//game1 MUST have starting value greater than 0
		//or it will act like you already lost
		FINALVIDEOPOKER game1 = new FINALVIDEOPOKER(1000);
		
		System.out.println("                                   Welcome to Video Poker!");
		System.out.println();
		System.out.println("            This is a generic game of poker in which you compete against a dealer only.");
		System.out.println();
		System.out.println("            There are 52 cards in the deck; therefore, there are 13 cards of each suit.");
		System.out.println("        Hearts are represented by '<3', diamonds by '<>', spades by '<k', and clubs by 'c3'.");
		System.out.println();
		System.out.print("  At the start of the game, the deck will be shuffled immediately, and you will begin with $");
		System.out.printf("%.2f", game1.getPot());
		System.out.println(".");
		System.out.println("               You must make a bet of at least $1.00 each round if you want to play.");
		System.out.println();
		System.out.println("                  Once the round begins, you will be given a hand by the dealer,");
		System.out.println("                and you will have one chance to exchange whichever cards you want.");
		System.out.println();
		System.out.println("At the end of the round, you will be given a percentage of what you wagered based on your final hand.");
		System.out.println("    You will then be given the option to coninue playing or to leave with what you've earned.");
		System.out.println();
		System.out.println("                      Do you think you have what it takes to make big bucks?");
		System.out.println("                                     It's time to find out!");
		System.out.println();
		System.out.println("                       (Type '-' then 'ENTER' to progress through the game.)");
		game1.continueGame();
		System.out.println("                                    Let's play Video Poker!");
		
		game1.continueGame();
		
		game1.shuffle();
		System.out.println("The deck has been shuffled.");
		game1.continueGame();
		game1.updatePot();
		
		while (game1.getGameOver() == false)
		{		
			game1.continueGame();
			
			if (game1.getDeckSize() < 10)
			{
				game1.shuffle();
				System.out.println("The deck has been shuffled.");
				game1.continueGame();
			}
			else if (game1.getDeckSize() < 15)
			{
				System.out.println("There are less than 15 cards left in the deck.");
				System.out.println("The deck will be shuffled next round.");
				game1.continueGame();
			}
			
			System.out.println("Round " + game1.getRound());
			game1.continueGame();
			
			game1.wager();
			game1.continueGame();
			
			game1.draw();
			//game1.testDraw();
			System.out.println("Your hand: ");
			game1.getHand();
			game1.continueGame();
			
			//game1.orderHand();
			//game1.getHand();
			//game1.continueGame();
			
			game1.numRejectCards();
			game1.continueGame();
			
			if (game1.getNumRejectCards() > 0)
			{
				System.out.print("Enter the position(s) of the card(s) you would like to replace ('1,' '2,' '3,' '4,' and/or '5'): ");
				game1.reDraw();
				game1.continueGame();
			}
			//game1.getHand();
			
			//game1.orderHand();
			//game1.getHand();
			//game1.continueGame();
			
			System.out.println("Your final hand: ");
			game1.getHand();
			game1.continueGame();
			
			game1.checkHand();
			game1.continueGame();
			
			game1.updatePot();
			game1.continueGame();
			
			if (game1.getPot() > 0)
			{
				game1.keepPlaying();
			}
			game1.getGameOver();
			
		}
		game1.continueGame();
		
		if (game1.getPot() == 0)
		{
			System.out.println("Sorry, you lost everything and have been kicked from the table!");
			System.out.println();
			System.out.print("Come back when you have more money.");
		}
		else if (game1.getPot() < game1.getPotOriginal())
		{
			System.out.print("Sorry, you have lost $");
			System.out.printf("%.2f", game1.getPotOriginal() - game1.getPot());
			System.out.println("!");
			System.out.println();
			System.out.print("Better luck next time!");
		}
		else if (game1.getPot() == game1.getPotOriginal())
		{
			System.out.print("You have won $");
			System.out.printf("%.2f", game1.getPotOriginal() - game1.getPot());
			System.out.println("!");
			System.out.println();
			System.out.println("Well at least you broke even.");
			System.out.print("Come back soon!");
		}
		else if (game1.getPot() > game1.getPotOriginal())
		{
			System.out.print("Congratulations! You have won $");
			System.out.printf("%.2f", game1.getPot() - game1.getPotOriginal());
			System.out.println("!");
			System.out.println();
			System.out.print("Come back soon!");
		}
	}
}
