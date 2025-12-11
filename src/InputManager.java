import Utility.ConsoleColors;
import java.util.Scanner;

public class InputManager {
    public static final String[] COLOR_LABELS = {
            "Red", "Green", "Yellow", "Blue", "Magenta", "Cyan", "White"
    };
    private final Scanner scanner;

    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    public char getPlayerCharacter() {
        System.out.println("Please insert a character (symbol) for this game:");
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.length() == 1) {
                return input.charAt(0);
            }
            System.err.println("Please insert exactly one character.");
        }
    }

    public String getPlayerName() {
        System.out.print("\tChoose your username: ");
        return scanner.nextLine().trim();
    }

    public int getPlayerColor() {
        System.out.println("Please choose your color [1-7]:");
        showColors();
        return readInteger(1, 7);
    }

    public void showColors() {
        for (int i = 0; i < COLOR_LABELS.length; i++) {
            if (i % 2 == 0) System.out.println();
            System.out.print(COLOR_LABELS[i] + "[" + (i + 1) + "]\t");
        }
        System.out.print("\nColor: ");
    }

    public int readInteger(int min, int max) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                if (!line.isEmpty()) {
                    int value = Integer.parseInt(line);
                    if (value >= min && value <= max) {
                        return value;
                    }
                }
                System.err.println("Insert a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    public int getMatchesNumber() {
        System.out.println("Please enter an odd number of matches [MAX 15]:");
        while (true) {
            int val = readInteger(1, 15);
            if (val % 2 != 0) return val;
            System.err.println("Number must be ODD (1, 3, 5...).");
        }
    }

    public int getIntValue(String name, int defaultValue, int min, int max, boolean indent) {
        while (true) {
            System.out.print((indent ? "\t" : "") + name + " [Default: " + defaultValue + "]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            try {
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
                System.err.println((indent ? "\t" : "") + "Insert a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.err.println((indent ? "\t" : "") + "Invalid input. Please enter a numeric value.");
            }
        }
    }

    public void release() {
        scanner.close();
    }
}