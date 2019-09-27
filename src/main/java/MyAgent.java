import java.util.Random;
/**
 * Describe your basic strategy here.
 * @author seenSeal
 *
 *1) Test if I can win
 *  if I can, move there
 *2) Test if they can win
 *  if they can, move there
 *3) If neither can win next turn, enter checkRandomMove
 *  test that moving here will not allow enemy to win in next turn
 *  test that moving here will not allow me to win in next turn
 *  if both are false move here (or any other applicable column)
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
    if (iCanWin(myGame) > -1) {
        moveOnColumn(iCanWin(myGame));
    } else if (theyCanWin(myGame) > -1) {
        moveOnColumn(theyCanWin(myGame));
    } else if (dbThreatDetector() > -1) {
      moveOnColumn(dbThreatDetector());
    } else if (ourDBThreatDetector() > -1) {
       moveOnColumn(ourDBThreatDetector());
    } else {
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
  /**
   * does same thing as move on column but take more parameters
   * takes what game it should move on and if it should do the opposite color of who you are.
   *
   * @param columnNumber The column into which to drop the token.
   * @param game What game the method should take place in.
   * @param oppositColor If you want to use and opposit color.
   */
  public void moveOnColumnTest(int columnNumber, Connect4Game game, boolean oppositColor) {
    // Find the top empty slot in the column
    // If the column is full, lowestEmptySlot will be -1
    int lowestEmptySlotIndex = getLowestEmptyIndex(game.getColumn(columnNumber));
    // if the column is not full
    if (lowestEmptySlotIndex > -1) {
      // get the slot in this column at this index
      Connect4Slot lowestEmptySlot = game.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
      // If the current agent is the Red player...
      if (!oppositColor && iAmRed) {
        lowestEmptySlot.addRed(); // Place a red token into the empty slot
      } else if (!oppositColor && !iAmRed) {
        lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
      } else if (oppositColor && iAmRed) {
        lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
      } else if (oppositColor && !iAmRed) {
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

  /**
   * counts the amount of valid columns.
   *@return  gives int of valid columns.
   */
  public int validColumnCount() { // this can be a boolean array further down the road so that we can reuse the data
    int invalidColumnCount = 0;
    int validColumnCount = 0;
    for (int c = 0; c < myGame.getColumnCount(); c++) {
      if (getLowestEmptyIndex(myGame.getColumn(c)) == -1) {
        invalidColumnCount++;
      }
    }
    validColumnCount = myGame.getColumnCount() - invalidColumnCount;
    return validColumnCount;
  }

  /**
   * used for eliminating columns to move on.
   * true means OK to go
   */

  public void checkRandomMove() {

    boolean[] dumbCheck = theyCanWinAfterMyNextTurn();
    boolean[] blockCheck = iCanWinAfterMyNextTurn();
    boolean[] reject = new boolean[myGame.getColumnCount()]; // false means not rejected and true means rejected
    int ran = randomMove();
    int w = 1;
    int rejectCount = 0;
    int rejectRejectCount = 0;


    while (w == 1) {
      ran = randomMove();
      if (validColumnCount() == rejectCount) { // this if statement is a secondary gate
        if (!dumbCheck[ran] || rejectRejectCount == validColumnCount()) {
          moveOnColumn(ran);
          w = 0;
        } else {
          rejectRejectCount++;
        }

      }
      if (!reject[ran]) {
        if (!dumbCheck[ran] && !blockCheck[ran]) {
          moveOnColumn(ran);
          w = 0;
        } else {
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
   * @param game the game it will run on
   * @return the column that would allow the agent to win.
   */
  public int iCanWin(Connect4Game game) {
    /**
     * Make copied of board.
     * Place your piece(theoretically) in column 1-ColumnCount if you can.
     */
    for (int c = 0; c < myGame.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(game);
      moveOnColumnTest(c, iGame, false);
      if (iGame.gameWon() != 'N') {
        return c;
      }
    }
    return -1;
  }

  /**
   * This is just iCanWin but returns an array.
   * @param game which game you what to use for the method
   * @return columns that they can win on
   */
  public boolean[] iCanWinArray(Connect4Game game) {
    boolean[] dbLocations = new boolean[game.getColumnCount()];
    for (int c = 0; c < game.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(game);
      moveOnColumnTest(c, iGame, false);
      if (iGame.gameWon() != 'N') {
        if (iGame.gameWon() == 'R' && iAmRed) {
          dbLocations[c] = true; // I can win
        } else if (iGame.gameWon() == 'Y' && !iAmRed) {
          dbLocations[c] = true; // I can win
        } else {
          dbLocations[c] = false; // cannot win
         }
      }
    }
    return dbLocations;
  }
  /**
   * Can see the "consequence" of you move.
   * Does what iCanWin but for the next turn.
   * VERY IMPORTANT.
   * ==============
   * SHOULD ONLY BE USED AFTER iCanWin AND theyCanWin have returned -1(you can't win and they can't win)
   * This code is blockCheck
   * goHereNotOk when true means don't go there
   * @return boolean array of witch columns a agent should go on.
   */
  public boolean[] iCanWinAfterMyNextTurn() { // checks if they win if you go here
    boolean[] goHereNotOk = new boolean[myGame.getColumnCount()];
    for (int c = 0; c < myGame.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(myGame);
      moveOnColumnTest(c, iGame, true); // TRUE = THE OPPOSITE COLOR OF WHAT YOU ARE
      moveOnColumnTest(c, iGame, false); // FALSE = YOUR COLOR

      if (iGame.gameWon() != 'N') {
        if (iGame.gameWon() == 'R' && iAmRed) {
          goHereNotOk[c] = true; // don't go here
        } else if (iGame.gameWon() == 'Y' && !iAmRed) {
          goHereNotOk[c] = true; // don't go here
        } else {
          goHereNotOk[c] = false; // you can go here
      }
    }
   }
    return goHereNotOk;
  }



  /**
   * Returns the column that would allow the opponent to win.
   *
   * <p>You might want your agent to check to see if the opponent would have any winning moves
   * available so your agent can block them. Implement this method to return what column should
   * be blocked to prevent the opponent from winning.</p>
   * @param game the game it will run on.
   * @return the column that would allow the opponent to win.
   */
  public int theyCanWin(Connect4Game game) {
    for (int c = 0; c < game.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(game);
      moveOnColumnTest(c, iGame, true);
      if (iGame.gameWon() != 'N') {
        return c;
      }
    }
    return -1;
  }
  /**
   * This is just theyCanWin but returns an array.
   * @param game witch game you what to use for the method
   * @return columns that they can win on
   */
  public boolean[] theyCanWinArray(Connect4Game game) {
    boolean[] dbLocations = new boolean[game.getColumnCount()];
    for (int c = 0; c < game.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(game);
      moveOnColumnTest(c, iGame, true);
      if (iGame.gameWon() != 'N') {
        if (iGame.gameWon() == 'R' && !iAmRed) {
          dbLocations[c] = true; // they can win
        } else if (iGame.gameWon() == 'Y' && iAmRed) {
          dbLocations[c] = true; // they can win
        } else {
          dbLocations[c] = false; // cannot win
         }
      }
    }
    return dbLocations;
  }
  /**
   * Can see the "consequence" of you move.
   * Does what theyCanWin but for the next turn.
   * VERY IMPORTANT
   * ==============
   * SHOULD ONLY BE USED AFTER iCanWin AND theyCanWin have returned -1(you can't win and they can't win)
   * this is dumbCheck
   * @return boolean array
   */

  public boolean[] theyCanWinAfterMyNextTurn() { // THEY WIN IF YOU GO HERE
    boolean[] goHereNotOk = new boolean[myGame.getColumnCount()];
    for (int c = 0; c < myGame.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(myGame);

      moveOnColumnTest(c, iGame, false); // FALSE = YOUR COLOR
      moveOnColumnTest(c, iGame, true); // TRUE = THE OPPOSITE COLOR OF WHAT YOU ARE

      if (iGame.gameWon() != 'N') {
        if (iGame.gameWon() == 'R' && !iAmRed) {
          goHereNotOk[c] = true; // don't go here
        } else if (iGame.gameWon() == 'Y' && iAmRed) {
          goHereNotOk[c] = true; // don't go here
        } else {
          goHereNotOk[c] = false; // it is OK to go here
         }

      }
    }
    return goHereNotOk;
  }
  /**
   * double threat detector
   * there are 2 ways to win unless you go here to block.
   * @return returns column to move on to block.
   */
 public int dbThreatDetector() {
   int winSpot1 = -1;
   int winSpot2 = -1;
   int columnReturn = -1;
   boolean didNotRun = false;
   for (int c = 0; c < myGame.getColumnCount(); c++) {
     // System.out.println("does the for loop work  " +  c);
      Connect4Game iGame = new Connect4Game(myGame);
        moveOnColumnTest(c, iGame, true);
        boolean[] dbColumns = theyCanWinArray(iGame);

        for (int i = 0; i < dbColumns.length; i++) {
          // System.out.println("does the second for loop work  " + i);
          if (dbColumns[i] && winSpot1 == -1) {
            winSpot1 = i;
            } else if (dbColumns[i] && winSpot2 == -1) {
              winSpot2 = i;
            }
          }


        if (winSpot1 > -1 && winSpot2 > -1) {
          if (winSpot1 == c) {
            columnReturn = winSpot2;
          } else if (winSpot2 == c) {
            columnReturn = winSpot1;
          } else {
            didNotRun = true;
          }
          if (columnReturn == -1 && didNotRun) {
            columnReturn = c;
          }
        }
      }
  return columnReturn;
}
/**
 * detects if we can make a double threat.
 * @return returns column to move to set up double threat
 */
 public int ourDBThreatDetector() {
   int winSpot1 = -1;
   int winSpot2 = -1;
   int columnReturn = -1;
   boolean stop = false;
   for (int c = 0; c < myGame.getColumnCount(); c++) {
      Connect4Game iGame = new Connect4Game(myGame);
        moveOnColumnTest(c, iGame, false);
        boolean[] dbColumns = iCanWinArray(iGame);

        for (int i = 0; i < dbColumns.length; i++) {
          if (dbColumns[i] && winSpot1 == -1) {
            winSpot1 = i;
            } else if (dbColumns[i] && winSpot2 == -1) {
              winSpot2 = i;
            }
          }
          if (winSpot1 > -1 && winSpot2 > -1) {
            if (!stop) {
              stop = true;
              columnReturn = c;
            }
          }
       }
   return columnReturn;
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
