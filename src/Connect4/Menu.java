package Connect4;

import Utility.ConsoleColors;
import Utility.Animation;

public class Menu {
    private final InputManager inputManager;
    private final Game game;

    public Menu() {
        this.inputManager = new InputManager();
        this.game = new Game();
        this.show();
    }

    public void show() {
        boolean running = true;

        while (running) {
//            Animation.clearScreen();
            printHeader();
            printOptions();

            int choice = inputManager.readInteger(1, 3);

            switch (choice) {
                case 1:
//                    Animation.clearScreen();
                    startNewGame();
                    break;
                case 2:
                    Animation.clearScreen();
                    showCredits();
                    break;
                case 3:
                    running = false;
                    ConsoleColors.println("Bye.", ConsoleColors.Colors.CYAN);
                    break;
            }
        }

        inputManager.release();
    }

    private void startNewGame() {
        ConsoleColors.println("\n--- GAME START ---", ConsoleColors.Colors.GREEN);
        game.initialize();
//        game.initializeTEST();
        game.play();
    }

    private void showCredits() {
        System.out.println("\n---------------------------------");
        ConsoleColors.println("        CREDITS Developers", ConsoleColors.Colors.YELLOW);
        System.out.println("---------------------------------");
        System.out.println("Designed & Developed by: \n\tValerio Ratti\n\tEdoardo Ratti");
        System.out.println("Version: 1.0.0");
        System.out.println("---------------------------------");
    }

    private void printHeader() {
        Animation.typeWriter("\n╔══════════════════════════════╗\n║         CONNECT FOUR         ║\n╚══════════════════════════════╝", ConsoleColors.Colors.BLUE,30);
    }

    private void printOptions() {
        Animation.typeWriter("Select an option\n1. Play\n2. Credits\n3. Quit\n> ", ConsoleColors.Colors.WHITE,7,false);
    }
}