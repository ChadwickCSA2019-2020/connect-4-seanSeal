import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyAgentTest {

    Connect4Game game;


    @Before
    public void setUp() throws Exception {
        game = new Connect4Game(7, 6);
    }

    @Test // tests for winning vertically on the first column
    public void testICanWinVerticallySimple() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        for (int i = 0; i < 3; i++) {
            redAgent.moveOnColumn(1);
            yellowAgent.moveOnColumn(2);
        }

        assertEquals(1, redAgent.iCanWin(game));

    }

    @Test // tests if can win vertically on any of the top columns
    public void testICanWinVerticallyTop4() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        for (int i = 0; i < 2; i++) {
            redAgent.moveOnColumn(1);
            yellowAgent.moveOnColumn(2);
        }

        for (int i = 0; i < 3; i++) {
            redAgent.moveOnColumn(2);
            yellowAgent.moveOnColumn(1);
        }

        assertEquals(2 , redAgent.iCanWin(game));

    }

    // TODO: Write 2 test cases for testICanWinHorizontally
    @Test
    public void testICanWinHorizontally() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        for(int i = 0; i < 3; i++) {
            redAgent.moveOnColumn(i);
            yellowAgent.moveOnColumn(i);
        }
        assertEquals(3 , redAgent.iCanWin(game));

    }

    @Test
    public void testICanWinHorizontallyTopRow() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for(int i = 0; i < 3; i++) {
          redAgent.moveOnColumn(i);
          yellowAgent.moveOnColumn(i);
      }
      for(int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
    }
      for(int i = 0; i < 3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
    }
      for(int i = 0; i<3; i++) {
        redAgent.moveOnColumn(i);
        yellowAgent.moveOnColumn(i);
      }
      assertEquals(3 , redAgent.iCanWin(game));
    }




    // TODO: Write 2 test cases for testICanWinDiagonally
    @Test
    public void testICanWinDiagonally() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        redAgent.moveOnColumn(1);
        yellowAgent.moveOnColumn(2);
        redAgent.moveOnColumn(2);
        yellowAgent.moveOnColumn(3);
        redAgent.moveOnColumn(3);
        yellowAgent.moveOnColumn(4);
        redAgent.moveOnColumn(3);
        yellowAgent.moveOnColumn(4);
        redAgent.moveOnColumn(4);


        assertEquals(4 , redAgent.iCanWin(game));
    }
    @Test
    public void testICanWinDiagonallyOpposite() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        redAgent.moveOnColumn(4);
        yellowAgent.moveOnColumn(3);
        redAgent.moveOnColumn(3);
        yellowAgent.moveOnColumn(2);
        redAgent.moveOnColumn(2);
        yellowAgent.moveOnColumn(1);
        redAgent.moveOnColumn(2);
        yellowAgent.moveOnColumn(1);
        redAgent.moveOnColumn(1);


        assertEquals(1 , redAgent.iCanWin(game));
    }


    //Tests if ICanWinAfterMyNextTurn works
@Test
public void testICanWinAfterMyNextTurn() {
	  MyAgent redAgent = new MyAgent(game, true);
    MyAgent yellowAgent = new MyAgent(game, false);
    game.clearBoard();
    redAgent.moveOnColumn(1);
    yellowAgent.moveOnColumn(2);
    redAgent.moveOnColumn(2);
    yellowAgent.moveOnColumn(4);
    redAgent.moveOnColumn(4);
    yellowAgent.moveOnColumn(4);
    redAgent.moveOnColumn(4);
    yellowAgent.moveOnColumn(3);

    boolean[] convert = redAgent.iCanWinAfterMyNextTurn();
    assertEquals(true , convert[3]);
}


    @Test
    public void testTheyCanWin() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        for (int i = 0; i < 3; i++) {
            redAgent.moveOnColumn(1);
            yellowAgent.moveOnColumn(2);
        }

        assertEquals(2, redAgent.theyCanWin(game));
    }

    // TODO: Write testTheyCanWinHorizontally

    @Test
    public void testTheyCanWinHorizontally() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        for(int i = 0; i < 3; i++) {
            yellowAgent.moveOnColumn(i);
            redAgent.moveOnColumn(i);
        }

        assertEquals(3 , redAgent.theyCanWin(game));

    }


    // TODO: Write testTheyCanWinDiagonally
    @Test
    public void testTheyCanWinDiagonally() {
        MyAgent redAgent = new MyAgent(game, true);
        MyAgent yellowAgent = new MyAgent(game, false);
        game.clearBoard();
        yellowAgent.moveOnColumn(1);
        redAgent.moveOnColumn(2);
        yellowAgent.moveOnColumn(2);
        redAgent.moveOnColumn(3);
        yellowAgent.moveOnColumn(3);
        redAgent.moveOnColumn(4);
        yellowAgent.moveOnColumn(3);
        redAgent.moveOnColumn(4);
        yellowAgent.moveOnColumn(4);

        assertEquals(4 , redAgent.theyCanWin(game));
    }

    @Test
  public void testTheyCanWinAfterMyNextTurn() {
	  MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(3);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(3);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(4);

      boolean[] convert = redAgent.theyCanWinAfterMyNextTurn();
      assertEquals(true, convert[2]);
  }
@Test
public void testTheyCanWinArray() {
  MyAgent redAgent = new MyAgent(game, true);
  MyAgent yellowAgent = new MyAgent(game, false);
  game.clearBoard();
  yellowAgent.moveOnColumn(2);
  redAgent.moveOnColumn(0);
  yellowAgent.moveOnColumn(3);
  redAgent.moveOnColumn(6);
  yellowAgent.moveOnColumn(4);

    boolean[] convert = redAgent.theyCanWinArray(game);
  assertEquals(true, convert[1]);
  assertEquals(true, convert[5]);
}

    @Test
    public void testTheyCanWinArray2() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(3);
      boolean[] convert = redAgent.theyCanWinArray(game);


      assertEquals(true, convert[1]);
      assertEquals(true, convert[3]);

    }
    @Test
    public void testDBThreatDetector() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(4);

      
      boolean[] convert = redAgent.theyCanWinArray(game);
      assertEquals(1, redAgent.dbThreatDetector());
    }
    
    @Test
    public void test2DBThreatDetector() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      for (int i = 2; i < 4; i++) {
        yellowAgent.moveOnColumn(i);
        redAgent.moveOnColumn(i);
      }
      System.out.println("Test2");
      assertEquals(0, redAgent.dbThreatDetector());
    }

    @Test
    public void testOurDBThreatDetector() {
      MyAgent redAgent = new MyAgent(game, true);
      MyAgent yellowAgent = new MyAgent(game, false);
      game.clearBoard();
      yellowAgent.moveOnColumn(0);
      redAgent.moveOnColumn(1);
      yellowAgent.moveOnColumn(1);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(3);
      yellowAgent.moveOnColumn(4);
      redAgent.moveOnColumn(2);
      yellowAgent.moveOnColumn(2);
      redAgent.moveOnColumn(4);
      yellowAgent.moveOnColumn(4);

      assertEquals(1, yellowAgent.ourDBThreatDetector());

    }

    // Tests you can win against a Beginner agent as Red
    @Test
    public void testRedWinningBeginnerAgent() {
        Agent redAgent = new MyAgent(game, true);
        Agent yellowAgent = new BeginnerAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard();
            while (!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }
            if (game.gameWon() == 'R') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Red against Beginner");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // Tests you can win against a Beginner agent as Yellow
    @Test
    public void testYellowWinningBeginnerAgent() {
        Agent redAgent = new BeginnerAgent(game, true);
        Agent yellowAgent = new MyAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard();
            while (!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'Y') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Yellow against Beginner");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // Tests you can win against a Random agent as Red
    @Test
    public void testRedWinningRandomAgent() {
        Agent redAgent = new MyAgent(game, true);
        Agent yellowAgent = new RandomAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard();
            while (!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'R') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Red against Random");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // Tests you can win against a Random agent as Yellow
    @Test
    public void testYellowWinningRandomAgent() {
        Agent redAgent = new RandomAgent(game, true);
        Agent yellowAgent = new MyAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'Y') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Yellow against Random");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // BONUS TODO: Write testCases to play against IntermediateAgent
    // Tests you can win against a Intermediate agent as Red
    @Test
    public void testRedWinningIntermediateAgent() {
        Agent redAgent = new MyAgent(game, true);
        Agent yellowAgent = new IntermediateAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'R') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Red against IntermediateAgent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // Tests you can win against a Intermediate agent as Yellow
    @Test
    public void testYellowWinningIntermediateAgent() {
        Agent redAgent = new IntermediateAgent(game, true);
        Agent yellowAgent = new MyAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'Y') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Yellow against Intermediate Agent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }
    // SUPER BONUS TODO: Write testCases to playAgainst AdvancedAgent
 // Tests you can win against a Intermediate agent as Red
    @Test
    public void testRedWinningAdvancedAgent() {
        Agent redAgent = new MyAgent(game, true);
        Agent yellowAgent = new AdvancedAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'R') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Red against AdvancedAgent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    //Tests you can win against a Intermediate agent as Yellow
    @Test
    public void testYellowWinningAdvancedAgent() {
        Agent redAgent = new AdvancedAgent(game, true);
        Agent yellowAgent = new MyAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'Y') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Yellow against AdvancedAgent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    // SUPER BONUS TODO: Write testCases to playAgainst BrilliantAgent
 // Tests you can win against a Brilliant agent as Red
    @Test
    public void testRedWinningBrilliantAgent() {
        Agent redAgent = new MyAgent(game, true);
        Agent yellowAgent = new BrilliantAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'R') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Red against BrilliantAgent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }

    //Tests you can win against a Brilliant agent as Yellow
    @Test
    public void testYellowWinningBrilliantAgent() {
        Agent redAgent = new BrilliantAgent(game, true);
        Agent yellowAgent = new MyAgent(game, false);
        int numberOfWins = 0;
        for (int i = 0; i < 50; i++) {
            game.clearBoard(); 
            while(!game.boardFull() && game.gameWon() == 'N') {
                redAgent.move();
                if (game.gameWon() != 'R') {
                    yellowAgent.move();
                }
            }

            if (game.gameWon() == 'Y') {
                numberOfWins++;
            }
        }
        System.out.println("You won: " + numberOfWins + " games as Yellow against BrilliantAgent");
        // Test that you win over 90% of your games
        assertTrue(numberOfWins >= 45);
    }
}
