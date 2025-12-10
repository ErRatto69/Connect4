import java.util.ArrayList;
import java.util.List;

public class GameBoard{

    private final int COLUMNS = 7;
    private final int ROWS = 6;

    private List<List<Player>> board;
    private Player[] players;
    private int turn;

    public GameBoard(Player... players) {
        board = new ArrayList<>();
        for (int i = 0; i < COLUMNS; i++) {
            board.add(new ArrayList<>());
        }

        this.players = players;
        this.turn = 0;
    }
}