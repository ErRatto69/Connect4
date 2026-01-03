package connect4;

import utility.ConsoleColors;
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
        if (column < 0 || column >= this.columns) {
            return true;
        }
        return this.board.get(column).size() >= this.rows;
    }

    public void addChecker(Player player, int column) {
        this.board.get(column).add(player);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public boolean checkWinConditions(int lastCol) {
        int lastRow = this.board.get(lastCol).size() - 1;
        Player player = this.board.get(lastCol).get(lastRow);

        return checkVertical(lastCol, lastRow, player) || checkHorizontal(lastCol, lastRow, player) || checkDiagonals(lastCol, lastRow, player);
    }

    public Player getPlayerAt(int c, int r) {
        if (c < 0 || c >= this.columns) {
            return null;
        }

        List<Player> columnList = this.board.get(c);
        if (r < 0 || r >= columnList.size()) {
            return null;
        }

        return columnList.get(r);
    }

    private boolean checkVertical(int col, int row, Player player) {
        if (row < 3) {
            return false;
        }

        int count = 1;
        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col, row - i);
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                break;
            }
        }
        return count >= 4;
    }

    private boolean checkHorizontal(int col, int row, Player player) {
        int count = 1;

        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col - i, row);
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col + i, row);
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                break;
            }
        }

        return count >= 4;
    }

    private boolean checkDiagonals(int col, int row, Player player) {
        return checkSingleDiagonal(col, row, player, 1, 1) || checkSingleDiagonal(col, row, player, 1, -1);
    }

    private boolean checkSingleDiagonal(int col, int row, Player player, int deltaCol, int deltaRow) {
        int count = 1;

        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col + (i * deltaCol), row + (i * deltaRow));
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else { break; }
        }

        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col - (i * deltaCol), row - (i * deltaRow));
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else { break; }
        }

        return count >= 4;
    }

    public int getColumnCheckers(int col){
        return this.board.get(col-1).size();
    }

    public String getGhostBoardString(int col, int row, Player ghost) {
        StringBuilder grid = new StringBuilder();
        final int DIGIT_FORMAT = 10;
        grid.append(" ");

        for (int k = 1; k <= this.columns; k++) {
            if (k < DIGIT_FORMAT) {
                grid.append(" ").append(k).append("  ");
            } else {
                grid.append(" ").append(k).append(" ");
            }
        }
        grid.append("\n");

        grid.append("╔").append("═══╦".repeat(Math.max(0, this.columns - 1))).append("═══╗\n");

        for (int i = 0; i < this.rows; i++) {
            grid.append("║");
            int oppositeIndex = (this.rows - 1) - i;

            for (int j = 0; j < this.columns; j++) {
                List<Player> columnList = board.get(j);
                if (oppositeIndex < columnList.size()) {
                    Player p = columnList.get(oppositeIndex);
                    grid.append(" ").append(p.getColor()).append(p.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                } else {
                    if (col == j+1 && row == i+1 ){
                        grid.append(" ").append(ghost.getColor()).append(ghost.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                    }else {
                        grid.append("   ║");
                    }
                }
            }
            grid.append("\n");
            if (i < this.rows - 1) {
                grid.append("╠").append("═══╬".repeat(Math.max(0, this.columns - 1))).append("═══╣\n");
            } else {
                grid.append("╚").append("═══╩".repeat(Math.max(0, this.columns - 1))).append("═══╝\n");
            }
        }
        return grid.toString();
    }

    public String getBoardString() {
        StringBuilder grid = new StringBuilder();
        final int DIGIT_FORMAT = 10;
        grid.append(" ");

        for (int k = 1; k <= this.columns; k++) {
            if (k < DIGIT_FORMAT) {
                grid.append(" ").append(k).append("  ");
            } else {
                grid.append(" ").append(k).append(" ");
            }
        }
        grid.append("\n");

        grid.append("╔").append("═══╦".repeat(Math.max(0, this.columns - 1))).append("═══╗\n");

        for (int i = 0; i < this.rows; i++) {
            grid.append("║");
            int oppositeIndex = (this.rows - 1) - i;

            for (int j = 0; j < this.columns; j++) {
                List<Player> columnList = board.get(j);
                if (oppositeIndex < columnList.size()) {
                    Player p = columnList.get(oppositeIndex);
                    grid.append(" ").append(p.getColor()).append(p.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                } else {
                    grid.append("   ║");
                }
            }
            grid.append("\n");
            if (i < this.rows - 1) {
                grid.append("╠").append("═══╬".repeat(Math.max(0, this.columns - 1))).append("═══╣\n");
            } else {
                grid.append("╚").append("═══╩".repeat(Math.max(0, this.columns - 1))).append("═══╝\n");
            }
        }
        return grid.toString();
    }
}