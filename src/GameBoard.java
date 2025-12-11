import Utility.ConsoleColors;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private int columns;
    private int rows;
    private List<List<Player>> board;

    public GameBoard(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            this.board.add(new ArrayList<>());
        }
    }

    public boolean isColumnFull(int column) {
        if (column < 0 || column >= this.columns) return true; // Safety check
        return this.board.get(column).size() >= this.rows;
    }

    public void addChecker(Player player, int column) {
        this.board.get(column).add(player);
    }

    public int getColumns() {
        return columns;
    }

    public boolean checkWinConditions(Player player) {
        return checkRows(player) || checkColumns(player) || checkDiagonals(player);
    }
    public boolean checkRows(Player player) {
        return true;
    }
    public boolean checkColumns(Player player) {
        return true;
    }
    public boolean checkDiagonals(Player player) {
        return true;
    }

    public String getBoardString() {
        StringBuilder sb = new StringBuilder();

        sb.append(" ");

        for (int k = 1; k <= this.columns; k++) {
            if (k < 10) {
                sb.append(" ").append(k).append("  ");
            } else {
                sb.append(" ").append(k).append(" ");
            }
        }
        sb.append("\n");

        sb.append("╔").append("═══╦".repeat(Math.max(0, this.columns - 1))).append("═══╗\n");

        for (int i = 0; i < this.rows; i++) {
            sb.append("║");
            int rowIndex = (this.rows - 1) - i;

            for (int j = 0; j < this.columns; j++) {
                List<Player> columnList = board.get(j);
                if (rowIndex < columnList.size()) {
                    Player p = columnList.get(rowIndex);
                    sb.append(" ").append(p.getColor()).append(p.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                } else {
                    sb.append("   ║");
                }
            }
            sb.append("\n");
            if (i < this.rows - 1) {
                sb.append("╠").append("═══╬".repeat(Math.max(0, this.columns - 1))).append("═══╣\n");
            } else {
                sb.append("╚").append("═══╩".repeat(Math.max(0, this.columns - 1))).append("═══╝\n");
            }
        }
        return sb.toString();
    }
}