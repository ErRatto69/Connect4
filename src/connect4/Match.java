package connect4;

import bot.BotAlgorithm;
import utility.Animation;
import utility.ConsoleColors;
import java.util.List;

/**
 * The Match class represents a single match between multiple players.
 */
public class Match {
    public GameBoard board;
    private List<Player> players;
    private InputManager inputManager;
    private boolean running;
    private Player winner;

    public Match(int columns, int rows, List<Player> players, InputManager inputManager) {
        this.board = new GameBoard(columns, rows);
        this.players = players;
        this.inputManager = inputManager;
        this.running = true;
        playMatch();
    }

    /**
     * Plays a match between multiple players.
     * The match is played until there is a winner or the board is full.
     */
    private void playMatch() {

        // Keep playing until there is a winner or the board is full
        while (running) {
            // Cycle through the players
            for (Player player : players) {
                System.out.println(board.getBoardString());
                int column = 0;

                // Obtains the next move
                if(!player.isBot()) {
                    System.out.println("Turn of " + player.getUsername() + " (" + player.getSymbol() + ")");
                    boolean validMove = false;

                    while (!validMove) {
                        // Retrieves the column from the current player
                        int inputCol = inputManager.getIntValue("Column", 1, 1, board.getColumns(), true);
                        column = inputCol - 1;

                        if (!board.isColumnFull(column)) {
                            validMove = true;
                        } else {
                            System.err.println("That column is full! Choose another one.");
                        }
                    }
                }else{
                    // Retrieves the column from the bot
                    column = BotAlgorithm.getBestMove(board, players, player, player.getDifficulty());
                }

                // Shows the animation of the checker dropping on the board
                Animation.dropChecker(board, column+1, player);

                // Adds the checker to the board
                board.addChecker(player, column);

                // Checks if the player current player won after the move
                if (board.checkWinConditions(column)){
                    this.winner = player;
                    ConsoleColors.println(player.getUsername()+" VINCE IL MATCH!", ConsoleColors.Colors.GREEN);
                    System.out.println(this.board.getBoardString());
                    this.running = false;
                    break;
                }

                // Checks if the board is full
                boolean full = true;
                for(int c = 0; c < board.getColumns(); c++){
                    if(!board.isColumnFull(c)) {
                        full = false;
                        break;
                    }
                }
                if(full){
                    System.out.println(board.getBoardString());
                    ConsoleColors.println("DRAW! No more moves.", ConsoleColors.Colors.YELLOW);
                    running = false;
                    break;
                }
            }
        }
    }


    /**
     * Get the winner of the match.
     *
     * @return The Player object representing the winner of the match, returns null if the match is still running or no player has won yet.
     */
    public Player getWinner() {
        return winner;
    }
}