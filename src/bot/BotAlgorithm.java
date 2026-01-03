package bot;

import connect4.GameBoard;
import connect4.Player;

import java.util.Arrays;
import java.util.List;

public class BotAlgorithm {

    private static byte[] getOptimizedBoard(GameBoard unOptimizedBoard, List<Player> playersList, Player currentBot) {
        int rows = unOptimizedBoard.getRows();
        int columns = unOptimizedBoard.getColumns();
        byte[] board = new byte[rows*columns];
        byte[] players = getOptimizedPlayers(playersList, currentBot);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Player player = unOptimizedBoard.getPlayerAt(j, i);
                int flatIndex = j + i*columns;

                if(player == null){
                    board[flatIndex] = 0;
                } else{
                    if (playersList.contains(player)){
                        board[flatIndex] = players[playersList.indexOf(player)];
                    }
                }
            }
        }

        return board;
    }

    private static byte[] getOptimizedPlayers(List<Player> playersList, Player currentBot) {
        byte[] idMap = new byte[playersList.size()];
        byte nextOpponentId = 2;
        for (int i = 0; i < playersList.size(); i++) {
            Player p = playersList.get(i);
            if (p == currentBot) {
                idMap[i] = 1;
            } else {
                idMap[i] = nextOpponentId++;
            }
        }
        return idMap;
    }

    private static byte getDepth(byte difficulty){
        return switch (difficulty) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 12;
            default -> 2;
        };
    }

    private static int[] getSortedValidMoves(byte[] board, int rows, int cols){
        int[] tempMoves = new int[cols];
        int count = 0;
        int center = cols / 2;

        if (isColumnValid(board, rows, cols, center)) {
            tempMoves[count] = center;
            count++;
        }

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

    private static boolean isColumnValid(byte[] board, int rows, int cols, int col) {
        return board[(rows - 1) * cols + col] == 0;
    }

    private static void makeImaginaryMove(byte[] board, int rows, int cols, int[] heights, int col, byte playerID){
        int r = heights[col];
        board[r * cols + col] = playerID;
        heights[col]++;
    }

    private static void undoImaginaryMove(byte[] board, int rows, int cols, int[] heights, int col) {
        heights[col]--;
        int r = heights[col];
        board[r * cols + col] = 0;
    }

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

    private static int evaluateBoard(byte[] board, int rows, int cols, byte id) {
        int score = 0;
        // Scansiona solo le aree sensate per ottimizzare, es per l'orizzontale non ha senso scansionare le ultime tre colonne
        // Scansione Orizzontale
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, 0, 1, cols, id);
            }
        }

        // Scansione Verticale
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                score += evaluateWinConditions(board, row, col, 1, 0, cols, id);
            }
        }

        // Scansione Diagonale Principale (\)
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, 1, 1, cols, id);
            }
        }

        // Scansione Diagonale Secondaria (/)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWinConditions(board, row, col, -1, 1, cols, id);
            }
        }

        return score;
    }

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

        if (botCount == 4) {
            return 1000000;
        }
        if (oppCount == 4) {
            return -1000000;
        }

        if (botCount == 3 && emptyCount == 1) {
            return 100;
        }
        if (botCount == 2 && emptyCount == 2) {
            return 5;
        }

        if (oppCount == 3 && emptyCount == 1) {
            return -500;
        }

        return 0;
    }

    private static int minimax(byte[] board, int rows, int cols, int[] heights, int depth, int alpha, int beta, boolean isMaximizing) {
        int score = evaluateBoard(board, rows, cols, (byte) 1);

        if (Math.abs(score) >= 1000000) {
            return score;
        }
        if (depth == 0) {
            return score;
        }

        int[] validMoves = getSortedValidMoves(board, rows, cols);
        if (validMoves.length == 0) {
            return 0;
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int col : validMoves) {
                makeImaginaryMove(board, rows, cols, heights, col, (byte) 1);
                int eval = minimax(board, rows, cols, heights, depth - 1, alpha, beta, false);
                undoImaginaryMove(board, rows, cols, heights, col);

                if (eval > maxEval) {
                    maxEval = eval;
                }
                if (eval > alpha) {
                    alpha = eval;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col : validMoves) {
                makeImaginaryMove(board, rows, cols, heights, col, (byte) 2);
                int eval = minimax(board, rows, cols, heights, depth - 1, alpha, beta, true);
                undoImaginaryMove(board, rows, cols, heights, col);

                if (eval < minEval) minEval = eval;
                if (eval < beta) beta = eval;
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    public static int getBestMove(GameBoard gameBoard, List<Player> playersList, Player currentBot, byte difficulty) {
        int rows = gameBoard.getRows();
        int columns = gameBoard.getColumns();
        byte[] board = getOptimizedBoard(gameBoard, playersList, currentBot);
        int[] heights = getColumnHeights(board, rows, columns);
        int depth = getDepth(difficulty);
        int[] validMoves = getSortedValidMoves(board, rows, columns);

        if (validMoves.length == 0) {
            return -1;
        }

        for (int col : validMoves) {
            makeImaginaryMove(board, rows, columns, heights, col, (byte) 1);
            if (evaluateBoard(board, rows, columns, (byte) 1) >= 900000) {
                undoImaginaryMove(board, rows, columns, heights, col);
                return col;
            }
            undoImaginaryMove(board, rows, columns, heights, col);
        }

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

            if (eval > alpha) {
                alpha = eval;
            }
        }

        return bestMove;
    }


}
