import java.util.List;

public class Match {
    public GameBoard board;
    private Player winner;
    private List<Player> players;
    private InputManager inputManager;

    public Match(int columns, int rows, List<Player> players) {
        this.board = new  GameBoard(columns, rows);
        this.players = players;
        this.inputManager = new InputManager();
        playTurn();
    }

    public void playTurn() {
        for(Player player : players) {
            System.out.println(board.getBoardString());
            System.out.println(player.getUsername()+" enter your move:");
            int column = inputManager.getIntValue("Column",0, board.getColumns(), 0,true);
            while(board.isColumnFull(column)) {
                System.err.println("That column is full");
                column = inputManager.getIntValue("Column",0, board.getColumns(), 0,true);
            }
            board.addChecker(player, column);
        }
    }
}
