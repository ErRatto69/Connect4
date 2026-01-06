package connect4;

import utility.ConsoleColors;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Connect 4 game grid.
 * Manages checker placement and checks win conditions.
 */
public class GameBoard {

    private int columns;

    private int rows;

    // Board is represented as a list of columns (each column is a list of Players)
    private List<List<Player>> board;

    public GameBoard(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            this.board.add(new ArrayList<>());
        }
    }

    /**
     * Checks if a column is full.
     *
     * @param column Column index
     * @return true if the column cannot accept more checkers
     */
    public boolean isColumnFull(int column) {
        if (column < 0 || column >= this.columns) {
            return true;
        }
        return this.board.get(column).size() >= this.rows;
    }

    /**
     * Adds a checker to the specified column.
     *
     * @param player The player to add as a checker.
     * @param column The index of the column where the checker should be added.
     */
    public void addChecker(Player player, int column) {
        this.board.get(column).add(player);
    }


    /**
     * Get the number of columns in the game board.
     *
     * @return the number of columns in the game board
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Get the number of rows in the game board.
     *
     * @return the number of rows in the game board
     */
    public int getRows() {
        return rows;
    }

    /**
     * Checks if the last move resulted in a win, checks only around the last placed checker.
     *
     * @param lastCol Index of the column of the last move
     * @return true if the player won, false otherwise
     */
    public boolean checkWinConditions(int lastCol) {
        int lastRow = this.board.get(lastCol).size() - 1;
        Player player = this.board.get(lastCol).get(lastRow);

        return checkVertical(lastCol, lastRow, player) ||
                checkHorizontal(lastCol, lastRow, player) ||
                checkDiagonals(lastCol, lastRow, player);
    }

    /**
     * Retrieves the player at a given column and row index.
     *
     * @param col The column index
     * @param row The row index
     * @return The player at the given column and row index, or null if the indices are out of bounds
     */
    public Player getPlayerAt(int col, int row) {
        // Check column index bounds
        if (col < 0 || col >= this.columns) {
            return null;
        }
        // Get column list
        List<Player> columnList = this.board.get(col);
        // Check row index bounds
        if (row < 0 || row >= columnList.size()) {
            return null;
        }
        // Return player at the given column and row index
        return columnList.get(row);
    }

    /**
     * Checks if the player has 4 checkers in a vertical line from the given column and row index.
     *
     * @param col The column index
     * @param row The row index
     * @param player The player to check
     * @return true if the player has 4 checkers in a vertical line, false otherwise
     */
    private boolean checkVertical(int col, int row, Player player) {
        // Check if the row is too low to form a vertical line
        if (row < 3) {
            return false;
        }

        int count = 1;
        // Check downwards
        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col, row - i);
            // If the neighbor is not null and has the same symbol as the player, increment the count
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                // If the neighbor is null or has a different symbol, break the loop
                break;
            }
        }
        // Return true if the count is greater than or equal to 4, false otherwise
        return count >= 4;
    }

    /**
     * Checks if the player has 4 checkers in a horizontal line from the given column and row index.
     *
     * @param col The column index
     * @param row The row index
     * @param player The player to check
     * @return true if the player has 4 checkers in a horizontal line, false otherwise
     */
    private boolean checkHorizontal(int col, int row, Player player) {
        int count = 1;
        // Check left
        for (int i = 1; i < 4; i++) {
            // Get the player to the left of the current cell
            Player neighbor = getPlayerAt(col - i, row);
            // If the neighbor is not null and has the same symbol as the player, increment the count
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                // If the neighbor is null or has a different symbol, break the loop
                break;
            }
        }
        // Check right
        for (int i = 1; i < 4; i++) {
            // Get the player to the right of the current cell
            Player neighbor = getPlayerAt(col + i, row);
            // If the neighbor is not null and has the same symbol as the player, increment the count
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                // If the neighbor is null or has a different symbol, break the loop
                break;
            }
        }
        // Return true if the count is greater than or equal to 4, false otherwise
        return count >= 4;
    }

    /**
     * Checks if the player has 4 checkers in a diagonal line from the given column and row index.
     *
     * @param col The column index
     * @param row The row index
     * @param player The player to check
     * @return true if the player has 4 checkers in a diagonal line, false otherwise
     */
    private boolean checkDiagonals(int col, int row, Player player) {
        // Checks both diagonals
        return checkSingleDiagonal(col, row, player, 1, 1) || checkSingleDiagonal(col, row, player, 1, -1);
    }

    /**
     * Checks if the player has 4 checkers in a diagonal line from the given column and row index, going in the direction specified by deltaCol and deltaRow.
     *
     * @param col The column index
     * @param row The row index
     * @param player The player to check
     * @param deltaCol The column direction in the diagonal line
     * @param deltaRow The row direction in the diagonal line
     * @return true if the player has 4 checkers in a diagonal line, false otherwise
     */
    private boolean checkSingleDiagonal(int col, int row, Player player, int deltaCol, int deltaRow) {
        int count = 1;
        // Check the diagonal going in the positive direction
        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col + (i * deltaCol), row + (i * deltaRow));
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                break;
            }
        }
        // Check the diagonal going in the negative direction
        for (int i = 1; i < 4; i++) {
            Player neighbor = getPlayerAt(col - (i * deltaCol), row - (i * deltaRow));
            if (neighbor != null && neighbor.getSymbol() == player.getSymbol()) {
                count++;
            } else {
                break;
            }
        }
        return count >= 4;
    }

    /**
     * Returns the number of checkers in a given column.
     *
     * @param col The column index (1-based index).
     * @return The number of checkers in the column.
     */
    public int getColumnCheckers(int col){
        // Subtract 1 from the column index because the list is 0-based indexed.
        return this.board.get(col-1).size();
    }

    /**
     * @return the visual string representation of the board for the console.
     */
    public String getBoardString() {
        return buildBoardString(null, -1, -1);
    }

    /**
     * Returns the visual string with a "ghost" checker falling (for animation).
     *
     * @param col The column index where the ghost checker is falling.
     * @param row The row index where the ghost checker is falling.
     * @param ghost The ghost checker player.
     * @return The visual string with the ghost checker falling.
     */
    public String getGhostBoardString(int col, int row, Player ghost) {
        return buildBoardString(ghost, col, row);
    }

    /**
     * Internal helper to build the grid string.
     *
     * @param ghost The ghost checker player, can be null if no ghost checker is present.
     * @param ghostCol The column index where the ghost checker is falling, can be -1 if no ghost checker is present.
     * @param ghostRow The row index where the ghost checker is falling, can be -1 if no ghost checker is present.
     * @return The visual string with the ghost checker falling or with the board.
     */
    private String buildBoardString(Player ghost, int ghostCol, int ghostRow) {
        StringBuilder grid = new StringBuilder();
        final int DIGIT_FORMAT = 10;
        grid.append(" ");

        // Print header numbers
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

                // If there is a placed checker at this height
                if (oppositeIndex < columnList.size()) {
                    Player p = columnList.get(oppositeIndex);
                    grid.append(" ").append(p.getColor()).append(p.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                }
                // Else check if we need to draw the ghost checker here
                else if (ghost != null && ghostCol == j + 1 && ghostRow == i + 1) {
                    grid.append(" ").append(ghost.getColor()).append(ghost.getSymbol()).append(ConsoleColors.Colors.RESET).append(" ║");
                }
                // Otherwise empty cell
                else {
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