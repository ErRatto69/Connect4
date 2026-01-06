package bot;

import connect4.GameBoard;
import connect4.Player;
import java.util.Arrays;
import java.util.List;

/**
 * The BotAlgorithm class implements the artificial intelligence for the game.
 * It uses the Minimax algorithm with Alpha-Beta pruning to calculate the best move.
 * To optimize performance, it converts the GameBoard.board object (lists of lists) into a flat byte array.
 */
public class BotAlgorithm {

    /**
     * Converts the complex GameBoard object into an optimized byte array for processing.
     *
     * @param unOptimizedBoard The original game board.
     * @param playersList The list of players.
     * @param currentBot The bot (player) currently calculating the move.
     * @return A byte array representing the game state.
     */
    private static byte[] getOptimizedBoard(GameBoard unOptimizedBoard, List<Player> playersList, Player currentBot) {
        int rows = unOptimizedBoard.getRows();
        int columns = unOptimizedBoard.getColumns();
        byte[] board = new byte[rows * columns];
        byte[] players = getOptimizedPlayers(playersList, currentBot);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Player player = unOptimizedBoard.getPlayerAt(j, i);

                // Convert 2D coords to 1D index
                int flatIndex = j + i * columns;

                if (player == null) {
                    // Empty cell
                    board[flatIndex] = 0;
                } else {
                    if (playersList.contains(player)) {
                        board[flatIndex] = players[playersList.indexOf(player)];
                    }
                }
            }
        }
        return board;
    }

    /**
     * Creates an ID map for the players.
     * The ID 1 is assigned to the current bot (Maximizing player), and the others get incrementals IDs.
     *
     * @param playersList The list of players.
     * @param currentBot The bot (player) currently calculating the move.
     * @return A byte array representing the player IDs.
     */
    private static byte[] getOptimizedPlayers(List<Player> playersList, Player currentBot) {
        byte[] idMap = new byte[playersList.size()];
        byte nextOpponentId = 2;
        for (int i = 0; i < playersList.size(); i++) {
            Player p = playersList.get(i);
            if (p == currentBot) {
                // Bot ID
                idMap[i] = 1;
            } else {
                // Opponent IDs
                idMap[i] = nextOpponentId++;
            }
        }
        return idMap;
    }

    /**
     * Determines the search depth of the Minimax tree based on difficulty.
     * @param difficulty The difficulty level (1-4).
     * @return The maximum recursion depth.
     */
    private static byte getDepth(byte difficulty){
        return switch (difficulty) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 12; // Very deep, requires high optimization
            default -> 2;
        };
    }

    /**
     * Returns valid moves sorted by strategic importance.
     * The order prioritizes the center column and expands outwards, because in Connect 4 the center is more valuable than other columns.
     * This the helps Alpha-Beta pruning to cut off branches faster.
     *
     * @param board The byte board
     * @param rows The number of rows
     * @param cols The number of columns
     */
    private static int[] getSortedValidMoves(byte[] board, int rows, int cols){
        int[] tempMoves = new int[cols];
        int count = 0;
        int center = cols / 2;

        // Add center if is valid
        if (isColumnValid(board, rows, cols, center)) {
            tempMoves[count] = center;
            count++;
        }

        // Expand left and right
        for (int offset = 1; offset <= center; offset++) {
            int left = center - offset;
            int right = center + offset;

            if (left >= 0 && isColumnValid(board, rows, cols, left)) {
                tempMoves[count] = left;
                count++;
            }

            if (right < cols && isColumnValid(board, rows, cols, right)) {
                tempMoves[count] = right;
                count++;
            }
        }
        return Arrays.copyOf(tempMoves, count);
    }

    /**
     * Checks if a column is not full
     *
     * @param board The byte board
     * @param rows The number of rows
     * @param cols The number of columns
     * @param col The column to check
     * @return A boolean indicating if the column is valid (not full)
     */
    private static boolean isColumnValid(byte[] board, int rows, int cols, int col) {
        return board[(rows - 1) * cols + col] == 0;
    }

    /**
     * Simulates a move on the byte array (without creating new objects)
     *
     * @param board The byte board
     * @param rows The number of rows
     * @param cols The number of columns
     * @param heights The heights of the columns
     * @param col The column to check
     * @param playerID The ID of the player to simulate
     */
    private static void makeImaginaryMove(byte[] board, int rows, int cols, int[] heights, int col, byte playerID){
        int r = heights[col];
        board[r * cols + col] = playerID;
        heights[col]++;
    }

    /**
     * Undoes the simulated move
     *
     * @param board The byte board
     * @param rows The number of rows
     * @param cols The number of columns
     * @param heights The heights of the columns
     * @param col The column to check
     */
    private static void undoImaginaryMove(byte[] board, int rows, int cols, int[] heights, int col) {
        heights[col]--;
        int r = heights[col];
        board[r * cols + col] = 0;
    }

    /**
     * Calculates the current height of checkers for each column.
     * @param board The byte board
     * @param rows The number of rows
     * @param cols The number of columns
     * @return An array of heights of checkers for each column
     */
    private static int[] getColumnHeights(byte[] board, int rows, int cols){
        int[] heights = new int[cols];
        for (int i = 0; i < cols; i++) {
            int height = 0;
            while (height < rows && board[height * cols + i] != 0) {
                height++;
            }
            heights[i] = height;
        }
        return heights;
    }

    /**
     * Heuristic function to evaluate the board state.
     * Assigns positive scores for configurations favorable to the bot (ID 1)
     * and negative scores for configurations favorable to opponents.
     */
    private static int evaluateBoard(byte[] board, int rows, int cols, byte id) {
        int score = 0;
        // Horizontal Scan
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, 0, 1, cols, id);
            }
        }
        // Vertical Scan
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                score += evaluateWinConditions(board, row, col, 1, 0, cols, id);
            }
        }
        // Main Diagonal Scan (\)
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, 1, 1, cols, id);
            }
        }
        // Secondary Diagonal Scan (/)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, -1, 1, cols, id);
            }
        }
        return score;
    }

    /**
     * Evaluates a window of 4 cells.
     * Scoring:
     * - 4 in a row (Win): +/- 1'000'000
     * - 3 in a row with empty space: Medium score (+100/-500)
     * - 2 in a row with empty space: Low score (+/- 5)
     *
     * @param board The game board
     * @param row The row of the window
     * @param col The column of the window
     * @param deltaRow The difference in row between cells in the window
     * @param deltaCol The difference in column between cells in the window
     * @param cols The number of columns in the board
     * @param id The ID of the bot
     * @return The score of the window
     */
    private static int evaluateWinConditions(byte[] board, int row, int col, int deltaRow, int deltaCol, int cols, byte id) {
        int botCount = 0;
        int oppCount = 0;
        int emptyCount = 0;

        for (int i = 0; i < 4; i++) {
            int currentRow = row + (deltaRow * i);
            int currentCol = col + (deltaCol * i);
            byte cell = board[currentRow * cols + currentCol];

            if (cell == id) {
                botCount++;
            } else if (cell == 0) {
                emptyCount++;
            } else {
                oppCount++;
            }
        }

        if (botCount == 4){
            return 1000000; // Win for the bot
        }
        if (oppCount == 4){
            return -1000000; // Win for an opponent
        }
        if (botCount == 3 && emptyCount == 1){
            return 100; // Medium priority
        }
        if (botCount == 2 && emptyCount == 2){
            return 5; // Low priority
        }
        if (oppCount == 3 && emptyCount == 1){
            return -500; // Blocking opponent is high priority
        }

        return 0;
    }

    /**
     * Recursive Minimax algorithm with Alpha-Beta Pruning.
     *
     * @param board The game board
     * @param rows The number of rows
     * @param cols The number of columns
     * @param heights The heights of the columns
     * @param depth The depth of the search
     * @param alpha The alpha value for pruning
     * @param beta The beta value for pruning
     * @param isMaximizing True if it is the bot's turn, False if it is the opponent's.
     * @return The evaluated score of the board state
     */
    private static int minimax(byte[] board, int rows, int cols, int[] heights, int depth, int alpha, int beta, boolean isMaximizing) {
        // Evaluate the board state
        int score = evaluateBoard(board, rows, cols, (byte) 1);

        // Base case, Win/Loss detected or max depth reached
        if (Math.abs(score) >= 1000000){
            // Subtracts the depth so the bot prefers moves to win faster
            return score-depth;
        }
        if (depth == 0){
            return score;
        }

        // Get valid moves
        int[] validMoves = getSortedValidMoves(board, rows, cols);

        // Checks if its a draw
        if (validMoves.length == 0){
            return 0;
        }

        if (isMaximizing) { // Bot turn
            int maxEval = Integer.MIN_VALUE;
            // Cycles every valid move
            for (int col : validMoves) {
                // Tries the move
                makeImaginaryMove(board, rows, cols, heights, col, (byte) 1);
                // Recursive call
                int eval = minimax(board, rows, cols, heights, depth - 1, alpha, beta, false);
                // Undoes the move
                undoImaginaryMove(board, rows, cols, heights, col);

                // Updates alpha
                if (eval > maxEval){
                    maxEval = eval;
                }
                if (eval > alpha){
                    alpha = eval;
                }
                if (beta <= alpha){
                    break; // Pruning
                }
            }
            return maxEval;
        } else { // Opponent turn
            int minEval = Integer.MAX_VALUE;
            // Cycles every valid move
            for (int col : validMoves) {
                // Tries the move
                makeImaginaryMove(board, rows, cols, heights, col, (byte) 2);
                // Recursive call
                int eval = minimax(board, rows, cols, heights, depth - 1, alpha, beta, true);
                // Undoes the move
                undoImaginaryMove(board, rows, cols, heights, col);

                if (eval < minEval){
                    minEval = eval;
                }
                if (eval < beta){
                    beta = eval;
                }
                if (beta <= alpha){
                    break; // Pruning
                }
            }
            return minEval;
        }
    }

    /**
     * Main method called externally to get the best move.
     *
     * @param gameBoard The current game board
     * @param playersList The list of players
     * @param currentBot The bot (player) currently calculating the move
     * @param difficulty The difficulty level of the bot
     * @return The column index of the best move
     */
    public static int getBestMove(GameBoard gameBoard, List<Player> playersList, Player currentBot, byte difficulty) {
        // Get board info
        int rows = gameBoard.getRows();
        int columns = gameBoard.getColumns();
        byte[] board = getOptimizedBoard(gameBoard, playersList, currentBot);
        int[] heights = getColumnHeights(board, rows, columns);
        int depth = getDepth(difficulty);
        int[] validMoves = getSortedValidMoves(board, rows, columns);

        // Handling no valid moves
        if (validMoves.length == 0){
            return -1;
        }

        // Immediate Win Check
        for (int col : validMoves) {
            makeImaginaryMove(board, rows, columns, heights, col, (byte) 1);
            if (evaluateBoard(board, rows, columns, (byte) 1) >= 900000) {
                undoImaginaryMove(board, rows, columns, heights, col);
                return col;
            }
            undoImaginaryMove(board, rows, columns, heights, col);
        }

        // Immediate Loss Check
        for (int col : validMoves) {
            for (byte opponentID = 2; opponentID <= playersList.size(); opponentID++) {
                makeImaginaryMove(board, rows, columns, heights, col, opponentID);
                if (evaluateBoard(board, rows, columns, opponentID) >= 900000) {
                    undoImaginaryMove(board, rows, columns, heights, col);
                    return col;
                }
                undoImaginaryMove(board, rows, columns, heights, col);
            }
        }

        // Minimax Execution
        int bestMove = validMoves[0];
        int maxEval = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int col : validMoves) {
            makeImaginaryMove(board, rows, columns, heights, col, (byte) 1);
            int eval = minimax(board, rows, columns, heights, depth - 1, alpha, beta, false);
            undoImaginaryMove(board, rows, columns, heights, col);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = col;
            }
            if (eval > alpha){
                alpha = eval;
            }
        }

        return bestMove;
    }
}