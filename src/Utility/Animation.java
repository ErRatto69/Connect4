package Utility;
import Connect4.*;

public class Animation {

    // ATTENZIONE metodo da internet non funziona su certe IDE
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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

    public static void DropChecker(GameBoard board, int column, Player player){
        int outputHeight = 2 + (board.getRows() * 2) + 1;

        for (int i = 1; i < board.getColumns()-board.getColumnCheckers(column)-1; i++) {
            System.out.println(board.getGhostBoardString(column, i, player));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[" + outputHeight + "A");
            System.out.flush();
        }
    }

}
