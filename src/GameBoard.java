import java.util.ArrayList;
import java.util.List;

public class GameBoard{

    private final int COLUMNS = 7;
    private final int ROWS = 6;

    private List<List<Player>> board;
    private int turn;

    public GameBoard() {
        board = new ArrayList<>();
        for (int i = 0; i < COLUMNS; i++) {
            board.add(new ArrayList<>());
        }

        this.turn = 0;
    }
}