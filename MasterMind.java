/**
 *	Plays the game of MasterMind.
 *	A guessing game where you have to guess the correct order of pegs to win
 *	@author Amogh Upadhyaya
 *	@since  Novemebr 6 2020
 */

public class MasterMind {

	private boolean reveal;			// true = reveal the master combination
	private PegArray[] guesses;		// the array of guessed peg arrays
	private PegArray master;		// the master (key) peg array
	private Prompt prompt;			// Instance of Prompt Class
	
	// Constants
	private final int PEGS_IN_CODE = 4;		// Number of pegs in code
	private final int MAX_GUESSES = 10;		// Max number of guesses
	private final int PEG_LETTERS = 6;		// Number of different letters on pegs
											// 6 = A through F
	/**
	 *	Constructor
	 */
	public MasterMind()
	{
		prompt = new Prompt();
		reveal = false;
		guesses = new PegArray[10];
		master = new PegArray(4);
		for(int i = 0; i<guesses.length; i++)
		{
			guesses[i] = new PegArray(4);
		}
	}
	
	/**
	 *	main method
	 */
	public static void main(String [] args)
	{
		MasterMind mm = new MasterMind();
		mm.printIntroduction();
	}
	/**
	 *	Print the introduction screen
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| ___  ___             _              ___  ___ _             _                       |");
		System.out.println("| |  \\/  |            | |             |  \\/  |(_)           | |                      |");
		System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | .  . | _  _ __    __| |                      |");
		System.out.println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| |\\/| || || '_ \\  / _` |                      |");
		System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | |  | || || | | || (_| |                      |");
		System.out.println("| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   \\_|  |_/|_||_| |_| \\__,_|                      |");
		System.out.println("|                                                                                    |");
		System.out.println("| WELCOME TO MASTERMIND!                                                 |");
		System.out.println("|                                                                                    |");
		System.out.println("| The game of MasterMind is played on a four-peg gameboard, and six peg letters can  |");
		System.out.println("| be used.  First, the computer will choose a random combination of four pegs, using |");
		System.out.println("| some of the six letters (A, B, C, D, E, and F).  Repeats are allowed, so there are |");
		System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations.  This \"master code\" is then hidden     |");
		System.out.println("| from the player, and the player starts making guesses at the master code.  The     |");
		System.out.println("| player has 10 turns to guess the code.  Each time the player makes a guess for     |");
		System.out.println("| the 4-peg code, the number of exact matches and partial matches are then reported  |");
		System.out.println("| back to the user. If the player finds the exact code, the game ends with a win.    |");
		System.out.println("| If the player does not find the master code after 10 guesses, the game ends with   |");
		System.out.println("| a loss.                                                                            |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME MASTERMIND!                                                        |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
		runGame();
	}
	
	/**
	 *	Runs the main game
	 */
	public void runGame()
	{
		int exactMatches = 0, partialMatches = 0; // the number of partial and exact matches
		int turnNum = 0; // keeps track of turn number
		boolean gameOver = false; // decides when to end the game
		String response; // the guess entered by the user
		
		for(int i = 0; i<PEGS_IN_CODE; i++)
		{
			int numOfChar; // which char from the user's response is being tested
			char masterChar; // which char from the answer is being tested
			numOfChar = (int)(Math.random()*6) + 65;
			masterChar = (char)numOfChar;
			master.getPeg(i).setLetter(masterChar);
		}
		
		/*master.getPeg(0).setLetter('A');
		master.getPeg(1).setLetter('C');
		master.getPeg(2).setLetter('B');
		master.getPeg(3).setLetter('D');
		
		String s = "";
		for(int i = 0; i<4; i++)
		{
			s+=master.getPeg(i).getLetter();
		}
		System.out.println(s);*/
		
		prompt.getString("Hit the Enter key to start the game");
		
		while(gameOver == false)
		{
			printBoard();
			
			turnNum++;
			System.out.println("\nGuess " + turnNum);
			System.out.println();
			
			response = prompt.getString("Enter the code using (A,B,C,D,E,F). For example, ABCD or abcd from left-to-right");
			response = response.toUpperCase();
			for(int i = 0; i<response.length(); i++)
			{
				if(response.charAt(i)>70 || response.charAt(i)<65)
				{
					response = getValidResponse();
				}
			}
			if(response.length()!=4) response = getValidResponse();
			
			for(int i = 0; i<PEGS_IN_CODE; i++)
			{
				Peg currentPeg; // which peg is being tested
				char currentLet; // which character from the peg is being tested
				currentLet = response.charAt(i);
				currentPeg = guesses[turnNum-1].getPeg(i);
				currentPeg.setLetter(currentLet);
			}
			
			exactMatches = guesses[turnNum-1].getExactMatches(master);
			partialMatches = guesses[turnNum-1].getPartialMatches(master);
			
			if(turnNum == 10) 
			{
				reveal = true;
				printBoard();
				gameOver = true;
			}
			if(exactMatches == 4)
			{
				reveal = true;
				printBoard();
				gameOver = true;
			}
		}
		
		if(exactMatches == 4 && turnNum>1)
		{
			System.out.println("\nNice work! You found the master code in " + turnNum + " guesses.\n");
		}
		else if(exactMatches == 4 && turnNum == 1)
		{
			System.out.println("\nNice work! You found the master code in " + turnNum + " guess.\n");
		}
		else System.out.println("\nOops. You were unable to find the solution in 10 guesses.\n");
	}
	
	/**
	 *  Prompts the user to enter a valid response of pegs
	 *	@return	guess	Valid string entered by user
	 */
	public String getValidResponse()
	{
		boolean bad = true; //decides whether the input is valid
		String guess = ""; // the user's guess
		int counter = 0; //counter variable to help determine whether the input is valid
		while(bad)
		{
			System.out.println("Bad input. try again");
			guess = prompt.getString("Enter the code using (A,B,C,D,E,F). For example, ABCD or abcd from left-to-right");
			guess = guess.toUpperCase();
			
			for(int i = 0; i<guess.length(); i++)
			{
				if(guess.charAt(i)>70 || guess.charAt(i)<65)
				{}
				else counter++;
			}
			if(guess.length()!=4) {}
			else counter++;
			
			if(counter == 5) bad = false;
			else counter = 0;
		}
		return guess;
	}
	/**
	 *	Print the peg board to the screen
	 */
	public void printBoard() {
		// Print header
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
		System.out.print("| MASTER |");
		for (int a = 0; a < PEGS_IN_CODE; a++)
			if (reveal)
				System.out.printf("   %c   |", master.getPeg(a).getLetter());
			else
				System.out.print("  ***  |");
		System.out.println(" Exact Partial |");
		System.out.print("|        +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("               |");
		// Print Guesses
		System.out.print("| GUESS  +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------|");
		for (int g = 0; g < MAX_GUESSES - 1; g++) {
			printGuess(g);
			System.out.println("|        +-------+-------+-------+-------+---------------|");
		}
		printGuess(MAX_GUESSES - 1);
		// print bottom
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
	}
	
	/**
	 *	Print one guess line to screen
	 *	@param t	the guess turn
	 */
	public void printGuess(int t) {
		System.out.printf("|   %2d   |", (t + 1));
		// If peg letter in the A to F range
		char c = guesses[t].getPeg(0).getLetter();
		if (c >= 'A' && c <= 'F')
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("   " + guesses[t].getPeg(p).getLetter() + "   |");
		// If peg letters are not A to F range
		else
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("       |");
		System.out.printf("   %d      %d    |\n",
							guesses[t].getExact(), guesses[t].getPartial());
	}

}
