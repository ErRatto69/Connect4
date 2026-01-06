package utility;
import connect4.*;

/**
 * Utility class to handle animations and console manipulation.
 * Relies heavily on ANSI escape sequences.
 */
public class Animation {

    /**
     * Clears the console screen.
     * Note: May not work in some IDE internal consoles (e.g., IntelliJ, Eclipse).
     * Works best in real terminals (CMD, Bash).
     * Found on StackOverflow.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Typewriter effect for multi-line text.
     *
     * @param text The text to be animated.
     * @param color The color of the text.
     * @param speed The delay between each character in milliseconds.
     */
    public static void typeWriter(String text, ConsoleColors.Colors color, int speed) {
        String[] lines = text.split("\n");

        int maxLength = 0;
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        for (int i = 0; i < maxLength; i++) {
            System.out.print(color);
            for (String line : lines) {
                if (i < line.length()) {
                    System.out.println(line.substring(0, i + 1));
                } else {
                    System.out.println(line);
                }
            }
            System.out.print(ConsoleColors.Colors.RESET);

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i < maxLength - 1) {
                System.out.print("\033[" + lines.length + "A");
            }
        }
    }

    /**
     * Typewriter effect char by char.
     *
     * @param text The text to be animated.
     * @param color The color of the text.
     * @param speed The delay between each character in milliseconds.
     * @param newLine Whether to start a new line or not after the animation.
     */
    public static void typeWriter(String text, ConsoleColors.Colors color, int speed, boolean newLine) {
        System.out.print(color);
        System.out.flush();

        char[] characters = text.toCharArray();
        for (char c : characters) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(ConsoleColors.Colors.RESET + (newLine ? "\n" : ""));
        System.out.flush();
    }

    /**
     * Animates a checker dropping into a specific column.
     * @param board The current game board.
     * @param column The column (1-based index) where the checker drops.
     * @param player The player who made the move.
     */
    public static void dropChecker(GameBoard board, int column, Player player){
        // Calculate the cursor height needed to move back up after printing the board
        int outputHeight = 2 + (board.getRows() * 2) + 1;

        // Drop row by row until hitting a checker or the bottom
        for (int i = 1; i < board.getRows() - board.getColumnCheckers(column); i++) {
            // Print temporary board with the "ghost" checker falling
            System.out.println(board.getGhostBoardString(column, i, player));
            try {
                Thread.sleep(200); // Animation pause
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Move cursor up to overwrite board on next frame
            System.out.print("\033[" + outputHeight + "A");
            System.out.flush();
        }
    }
}