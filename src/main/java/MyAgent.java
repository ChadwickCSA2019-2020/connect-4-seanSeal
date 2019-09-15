import java.util.Random;
/**
 * Describe your basic strategy here.
 * @author <seanSeal>
 *
 *1) see if Agent can win
 *	if true then move to win
 *2) see if BadAgent can win on their next turn 
 *	if true then block 
 *3)
 *
 */
public class MyAgent extends Agent {
	/**
	 * A random number generator to randomly decide where to place a token.
	 */
	private Random random;
	

	/**
	 * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
	 *
	 * @param game The game the agent will be playing.
	 * @param iAmRed True if the agent is Red, False if the agent is Yellow.
	 */
	public MyAgent(Connect4Game game, boolean iAmRed) {
		super(game, iAmRed);
		random = new Random();
	}

	/**
	 * The move method is run every time it is this agent's turn in the game. You may assume that
	 * when move() is called, the game has at least one open slot for a token, and the game has not
	 * already been won.
	 *
	 * <p>By the end of the move method, the agent should have placed one token into the game at some
	 * point.</p>
	 *
	 * <p>After the move() method is called, the game engine will check to make sure the move was
	 * valid. A move might be invalid if:
	 * - No token was place into the game.
	 * - More than one token was placed into the game.
	 * - A previous token was removed from the game.
	 * - The color of a previous token was changed.
	 * - There are empty spaces below where the token was placed.</p>
	 *
	 * <p>If an invalid move is made, the game engine will announce it and the game will be ended.</p>
	 *
	 */
	public void move() {
		if(iCanWin() > -1) {
			moveOnColumn(iCanWin());
		}else if(theyCanWin() > -1) {
			moveOnColumn(theyCanWin());
		}else {
			checkRandomMove();
		}

	}

	/**
	 * Drops a token into a particular column so that it will fall to the bottom of the column.
	 * If the column is already full, nothing will change.
	 *
	 * @param columnNumber The column into which to drop the token.
	 */
	public void moveOnColumn(int columnNumber) {
		// Find the top empty slot in the column
		// If the column is full, lowestEmptySlot will be -1
		int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));
		// if the column is not full
		if (lowestEmptySlotIndex > -1) {
			// get the slot in this column at this index
			Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
			// If the current agent is the Red player...
			if (iAmRed) {
				lowestEmptySlot.addRed(); // Place a red token into the empty slot
			} else {
				lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
			}
		}
	}



	public void moveOnColumnTest(int columnNumber, Connect4Game game, boolean oppositColor) {
		// Find the top empty slot in the column
		// If the column is full, lowestEmptySlot will be -1
		int lowestEmptySlotIndex = getLowestEmptyIndex(game.getColumn(columnNumber));
		// if the column is not full
		if (lowestEmptySlotIndex > -1) {
			// get the slot in this column at this index
			Connect4Slot lowestEmptySlot = game.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
			// If the current agent is the Red player...
			if (oppositColor == false && iAmRed == true) {
				lowestEmptySlot.addRed(); // Place a red token into the empty slot
			} else if(oppositColor == false && iAmRed == false) {
				lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
			} else if(oppositColor == true && iAmRed == true) {
				lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot 
			} else if(oppositColor == true && iAmRed == false) {
				lowestEmptySlot.addRed(); // Place a red token into the empty slot
			}
		}
	}


	/**
	 * Returns the index of the top empty slot in a particular column.
	 *
	 * @param column The column to check.
	 * @return
	 *      the index of the top empty slot in a particular column;
	 *      -1 if the column is already full.
	 */
	public int getLowestEmptyIndex(Connect4Column column) {
		int lowestEmptySlot = -1;
		for  (int i = 0; i < column.getRowCount(); i++) {
			if (!column.getSlot(i).getIsFilled()) {
				lowestEmptySlot = i;
			}
		}
		return lowestEmptySlot;
	}

	/**
	 * Returns a random valid move. If your agent doesn't know what to do, making a random move
	 * can allow the game to go on anyway.
	 *
	 * @return a random valid move.
	 */
	public int randomMove() {
		int i = random.nextInt(myGame.getColumnCount());
		while (getLowestEmptyIndex(myGame.getColumn(i)) == -1) {
			i = random.nextInt(myGame.getColumnCount());
		}
		return i;
	}
	
	public int validColumnCount() {
		int invalidColumnCount = 0;
		int validColumnCount = 0;
		for(int c = 0; c < myGame.getColumnCount(); c++) {
			if(getLowestEmptyIndex(myGame.getColumn(c)) == -1) {
				invalidColumnCount++;
			}
		}
		validColumnCount = myGame.getColumnCount() - invalidColumnCount;
		return validColumnCount;
	}
	
	/**
	 * used for elimating columns to move on
	 * 
	 * true means ok to go 
	 */
	
	public void checkRandomMove() {

		boolean[] dumbCheck = theyCanWinAfterMyNextTurn();
		boolean[] blockCheck = iCanWinAfterMyNextTurn();
		boolean[] reject = new boolean[myGame.getColumnCount()]; // false means not rejected and true means rejected
		int ran = randomMove();
		int w = 1;
		int rejectCount = 0;
		
		
		while( w == 1) { 										// this IS be a infedent while loop right now

			ran = randomMove();
			if(validColumnCount() == rejectCount){
				if(dumbCheck[ran] == false) {
					System.out.println("DOES THIS PART WORK???????");
					moveOnColumn(ran);
					w = 0;
				}
			}
			if(reject[ran] == false) {
				//System.out.println("does this part work? " + dumbCheck[ran] + ":" + blockCheck[ran]);
				if(dumbCheck[ran] == false && blockCheck[ran] == false) {
					moveOnColumn(ran);
					w = 0;
				}else {
					System.out.println("DID THIS RUN");
					reject[ran] = true;
					rejectCount++;
				}

			}
			
		}
	}
	
	
	
	/**
	 * Returns the column that would allow the agent to win.
	 *
	 * <p>You might want your agent to check to see if it has a winning move available to it so that
	 * it can go ahead and make that move. Implement this method to return what column would
	 * allow the agent to win.</p>
	 *
	 * @return the column that would allow the agent to win
	 */
	public int iCanWin() {
		/**
		 * Make copied of board 
		 * Place your piece(theoretically) in column 1-ColumnCount if you can
		 */	
		for(int c = 0; c < myGame.getColumnCount();c++) {
			Connect4Game iGame = new Connect4Game(myGame);
			moveOnColumnTest(c,iGame, false);
			if(iGame.gameWon() != 'N') {
				return c;
			}
		}
		return -1;
	}


	/**
	 * Can see the "consequence" of you move.
	 * Does what iCanWin but for the next turn.
	 * VERY IMPORTANT 
	 * ==============
	 * SHOULD ONLY BE USED AFTER iCanWin AND theyCanWin have returned -1(you can't win and they can't win)
	 */
		
		public boolean[] iCanWinAfterMyNextTurn() { // checks if they win if you go here
			boolean[] goHereOk = new boolean[myGame.getColumnCount()];
			for(int c = 0; c < myGame.getColumnCount();c++) {
				Connect4Game iGame = new Connect4Game(myGame);
				moveOnColumnTest(c,iGame, true);					// TRUE = THE OPPOSITE COLOR OF WHAT YOU ARE
				moveOnColumnTest(c,iGame, false);					// FALSE = YOUR COLOR
				
				if(iGame.gameWon() != 'N') 
					{
					if		(iGame.gameWon() == 'R' && iAmRed == true) 			{goHereOk[c] = false;}
					else if	(iGame.gameWon() == 'Y' && iAmRed == false) 		{goHereOk[c] = false;}
					else 														{goHereOk[c] = true;}
					}
				}
			return goHereOk;
		}
	
	
	
	/**
	 * Returns the column that would allow the opponent to win.
	 *
	 * <p>You might want your agent to check to see if the opponent would have any winning moves
	 * available so your agent can block them. Implement this method to return what column should
	 * be blocked to prevent the opponent from winning.</p>
	 *
	 * @return the column that would allow the opponent to win.
	 */
	public int theyCanWin() {
		for(int c = 0; c < myGame.getColumnCount();c++) {
			Connect4Game iGame = new Connect4Game(myGame);
			moveOnColumnTest(c,iGame, true);
			if(iGame.gameWon() != 'N') {
				return c;
			}
		}
		return -1;
	}
/**
 * Can see the "consequence" of you move.
 * Does what theyCanWin but for the next turn.
 * VERY IMPORTANT 
 * ==============
 * SHOULD ONLY BE USED AFTER iCanWin AND theyCanWin have returned -1(you can't win and they can't win)
 */
	
	public boolean[] theyCanWinAfterMyNextTurn() { 								// THEY WIN IF YOU GO HERE
		boolean[] goHereOk = new boolean[myGame.getColumnCount()];
		for(int c = 0; c < myGame.getColumnCount();c++) {
			Connect4Game iGame = new Connect4Game(myGame);
			moveOnColumnTest(c,iGame, false);					// FALSE = YOUR COLOR
			moveOnColumnTest(c,iGame, true);					// TRUE = THE OPPOSITE COLOR OF WHAT YOU ARE
		
			if(iGame.gameWon() != 'N') {
				if(iGame.gameWon() == 'R' && iAmRed == false) {	
					goHereOk[c] = false;
				}else if(iGame.gameWon() == 'Y' && iAmRed == true) {
					goHereOk[c] = false;
				}else {goHereOk[c] = true;}	
			}
		}
		return goHereOk;
	}
	
	/**
	 * Returns the name of this agent.
	 *
	 * @return the agent's name
	 */
	public String getName() {
		return "My Agent";
	}
}
