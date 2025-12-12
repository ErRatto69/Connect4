import Utility.ConsoleColors;
import java.util.List;

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

    private void playMatch() {
        while (running) {
            for (Player player : players) {
                System.out.println(board.getBoardString());
                System.out.println("Turn of " + player.getUsername() + " (" + player.getSymbol() + ")");

                int column = 0;
                boolean validMove = false;

                while (!validMove) {
                    int inputCol = inputManager.getIntValue("Column", 1, 1, board.getColumns(), true);
                    column = inputCol - 1;

                    if (!board.isColumnFull(column)) {
                        validMove = true;
                    } else {
                        System.err.println("That column is full! Choose another one.");
                    }
                }

                board.addChecker(player, column);

                if (board.checkWinConditions(column)){
                    this.winner = player;
                    ConsoleColors.println(player.getUsername()+" VINCE IL MATCH!", ConsoleColors.Colors.GREEN);
                    System.out.println(this.board.getBoardString());
                    this.running = false;
                    break;
                }

                boolean full = true;
                for(int c=0; c<board.getColumns(); c++){
                    if(!board.isColumnFull(c)) { full = false; break; }
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

    public Player getWinner() {
        return winner;
    }
}