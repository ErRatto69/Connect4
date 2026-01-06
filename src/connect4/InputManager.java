package connect4;

import utility.Animation;
import utility.ConsoleColors;
import java.util.Scanner;

public class InputManager {

    public static final int TYPE_SPEED = 20;

    public static final String[] COLOR_LABELS = {
            "Red", "\tGreen", "Yellow", "Blue", "Magenta", "Cyan", "White"
    };

    public static final String[] DIFFICULTY_LABELS = {
            "Easy", "Medium", "Hard", "Impossible"
    };

    private final Scanner scanner;

    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a player's symbol from the user input.
     *
     * @return The player's symbol entered by the user as a character.
     */
    public char getPlayerSymbol() {
        String input;
        while (true) {
            // Ask the user to insert a character (symbol) for this player
            Animation.typeWriter("Please insert a character (symbol) for this player: ", ConsoleColors.Colors.WHITE,TYPE_SPEED,false);
            input = scanner.nextLine().trim();
            // If the input is exactly one character, return it
            if (input.length() == 1) {
                return input.charAt(0);
            }
            // If the input is not exactly one character, print an error message
            Animation.typeWriter("Please insert exactly one character.", ConsoleColors.Colors.RED,TYPE_SPEED,true);
        }
    }

    /**
     * Reads a player's name from the user input.
     *
     * @return The player's name entered by the user.
     */
    public String getPlayerName() {
        Animation.typeWriter("\tChoose your username: ", ConsoleColors.Colors.WHITE,TYPE_SPEED,false);
        return scanner.nextLine().trim();
    }

    /**
     * Reads an integer value from the user input for the player's color.
     *
     * @return The player's color entered by the user (1-7).
     */
    public int getPlayerColor() {
        System.out.println("Please choose your color [1-7]:");
        showColors();
        System.out.print("\nColor: ");
        return readInteger(1, 7);
    }

    /**
     * Reads an integer value from the user input for the bot difficulty.
     *
     * @return The bot difficulty entered by the user (1-4)
     */
    public int getBotDifficulty() {
        System.out.println("Please choose a difficulty [1-4]:");
        showDifficulties();
        System.out.print("\nDifficulty: ");
        return readInteger(1, 4);
    }

    /**
     * Displays the color labels to the user.
     */
    public void showColors() {
        String colors = "";
        for (int i = 0; i < COLOR_LABELS.length; i++) {
            colors += ConsoleColors.Colors.fromInt(i+1)+COLOR_LABELS[i] + "[" + (i + 1) + "]"+ConsoleColors.Colors.RESET+"\t"+(i%2==1?"\n":"");
        }
        Animation.typeWriter(colors, ConsoleColors.Colors.RESET,7,false);
    }

    /**
     * Displays the difficulty labels to the user.
     */
    public void showDifficulties() {
        String difficulties = "";
        for (int i = 0; i < DIFFICULTY_LABELS.length; i++) {
            difficulties += DIFFICULTY_LABELS[i] + "[" + (i + 1) + "]"+"\n";
        }
        Animation.typeWriter(difficulties, ConsoleColors.Colors.RESET,7,false);
    }

    /**
     * Reads an integer value from the user input.
     *
     * @param min the minimum value allowed
     * @param max the maximum value allowed
     * @return the integer value entered by the user, between min and max, inclusive
     */
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
                // If the value is out of range, print an error message
                System.err.print("Insert a number between " + min + " and " + max+": ");
            } catch (NumberFormatException e) {
                // If the input is not a number, print an error message
                System.err.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Reads an integer value from the user input for the number of matches.
     *
     * @return an odd integer value entered by the user between min and max, inclusive
     */
    public int getMatchesNumber() {
        final int MAX_MATCHES = 15;
        final int MIN_MATCHES = 1;

        while (true) {
            System.out.print("Please enter an odd number of matches [MAX " + MAX_MATCHES + "]: ");
            int val = readInteger(MIN_MATCHES, MAX_MATCHES);
            if (val % 2 != 0){
                return val; // If the value is odd, return it
            }
            System.err.println("Number must be ODD (1, 3, 5...).");
        }
    }

    /**
     * Reads an integer value from the user input.
     *
     * @param name the name of the value to read
     * @param defaultValue the default value if the user does not enter anything
     * @param min the minimum value allowed
     * @param max the maximum value allowed
     * @param indent whether the question should be indented or not
     * @return the integer value entered by the user, or the default value if the user does not enter anything
     */
    public int getIntValue(String name, int defaultValue, int min, int max, boolean indent) {
        while (true) {
            // Ask the user the question and print it indented or not
            System.out.print((indent ? "\t" : "") + name + " [Default: " + defaultValue + "]: ");
            String input = scanner.nextLine().trim();

            // If the user does not enter anything, return the default value
            if (input.isEmpty()) {
                return defaultValue;
            }

            try {
                // Parse the input to an integer and check if it is in the allowed range
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
                // If the number is not in the allowed range, print an error message
                System.err.println((indent ? "\t" : "") + "Insert a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                // If the input is not a number, print an error message
                System.err.println((indent ? "\t" : "") + "Invalid input. Please enter a numeric value.");
            }
        }
    }

    /**
     * Reads a yes or no answer from the user.
     *
     * @param question the question to ask the user
     * @param defaultValue the default value if the user does not enter anything
     * @param indent whether the question should be indented or not
     * @return true if the user enters "yes", false if the user enters "no"
     */
    public boolean getYesNo(String question, boolean defaultValue, boolean indent){
        // Read the user's answer until it is valid
        String input;
        while (true) {
            // Ask the user the question and print it indented or not
            Animation.typeWriter((indent?"\t":"")+question, ConsoleColors.Colors.WHITE,TYPE_SPEED,false);
            input = scanner.nextLine().trim();

            // If the answer is "yes", return true
            if (input.toLowerCase().equals("yes")) {
                return true;
            } else if (input.toLowerCase().equals("no")) { // If the answer is "no", return false
                return false;
            } else { // If the answer is not valid, print an error message
                Animation.typeWriter("Please choose between yes or no.", ConsoleColors.Colors.RED, TYPE_SPEED, true);
            }
        }
    }

    /**
     * Closes the Scanner object used to read input from the user.
     * This method is used to release system resources.
     */
    public void release() {
        scanner.close();
    }
}