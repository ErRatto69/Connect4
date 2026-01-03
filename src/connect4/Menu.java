package connect4;

import utility.ConsoleColors;
import utility.Animation;

/**
 * The Menu class is responsible for displaying the game menu.
 * It allows the user to start a new game, view the credits or quit the game.
 */
public class Menu {
    private final InputManager inputManager;
    private final Game game;

    public Menu() {
        this.inputManager = new InputManager();
        this.game = new Game();
        this.show();
    }

    /**
     * The show method displays the game menu and handles the program cycle.
     * It continues to display the menu until the user chooses to quit.
     */
    public void show() {
        boolean running = true;

        while (running) {
            // Prints the header and the menu options
            printHeader();
            printOptions();

            // Retrieves the user choice from the class
            int choice = inputManager.readInteger(1, 3);

            // Handles the user choice
            switch (choice) {
                case 1: // Starts a new game
                    startNewGame();
                    break;
                case 2: // Shows the credits
                    Animation.clearScreen();
                    showCredits();
                    break;
                case 3: // Quits the game
                    running = false;
                    ConsoleColors.println("Bye.", ConsoleColors.Colors.CYAN);
                    break;
            }
        }
        // Closes the input stream
        inputManager.release();
    }

    /**
     * The startNewGame method initializes the game and allows the user to play.
     */
    private void startNewGame() {
        ConsoleColors.println("\n--- GAME START ---", ConsoleColors.Colors.GREEN);
        game.initialize();
//        game.initializeTEST();
//        game.initializeBotsTEST();
        game.play();
    }

    /**
     * The showCredits method displays the credits of the game.
     */
    private void showCredits() {
        System.out.println("\n---------------------------------");
        ConsoleColors.println("        CREDITS Developers", ConsoleColors.Colors.YELLOW);
        System.out.println("---------------------------------");
        System.out.println("Designed & Developed by: \n\tValerio Ratti\n\tEdoardo Ratti");
        System.out.println("Version: 1.0.0");
        System.out.println("---------------------------------");
    }

    /**
     * The printHeader method displays the game header.
     */
    private void printHeader() {
        Animation.typeWriter("\n╔══════════════════════════════╗\n║         CONNECT FOUR         ║\n╚══════════════════════════════╝", ConsoleColors.Colors.BLUE,30);
    }

    /**
     * The printOptions method displays the game menu options.
     */
    private void printOptions() {
        Animation.typeWriter("Select an option\n1. Play\n2. Credits\n3. Quit\n> ", ConsoleColors.Colors.WHITE,7,false);
    }
}