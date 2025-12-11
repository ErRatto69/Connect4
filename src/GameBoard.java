import Utility.ConsoleColors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoard{

    private int columns;
    private int rows;

    private List<List<Player>> board;
    private int turn;

    public GameBoard(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            this.board.add(new ArrayList<>());
        }

        this.turn = 0;
    }

    public boolean isColumnFull(int column) {
        if (column >= this.columns) {
            return false;
        }
        return this.board.get(column).size() >= this.rows;
    }

    public void addChecker(Player player, int column) {
        this.board.get(column).add(player);
        this.turn++;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public String getBoardString() {
        String boardString = "╔" + "═══╦".repeat(this.columns - 1) + "═══╗\n";

        for (int i = 0; i < this.rows; i++) {

            boardString += "║";

            int rowIndex = (this.rows - 1) - i;

            for (int j = 0; j < this.columns; j++) {

                List<Player> columnList = board.get(j);

                if (rowIndex < columnList.size()) {
                    Player p = columnList.get(rowIndex);

                    boardString += " " + p.getColor() + p.getSymbol() + ConsoleColors.Colors.RESET + " ║";
                } else {
                    boardString += "   ║";
                }
            }

            boardString += "\n";

            if (i < this.rows - 1) {
                boardString += "╠" + "═══╬".repeat(this.columns - 1) + "═══╣\n";
            } else {
                boardString += "╚" + "═══╩".repeat(this.columns - 1) + "═══╝\n";
            }
        }
        return boardString;
    }
}