import Utility.ConsoleColors;

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
            printHeader();
            printOptions();

            int choice = inputManager.readInteger(1, 3);

            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
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
        System.out.println("\n");
        ConsoleColors.println("╔══════════════════════════════╗", ConsoleColors.Colors.BLUE);
        ConsoleColors.println("║         CONNECT FOUR         ║", ConsoleColors.Colors.BLUE);
        ConsoleColors.println("╚══════════════════════════════╝", ConsoleColors.Colors.BLUE);
    }

    private void printOptions() {
        System.out.println("Seleziona un'opzione:");
        System.out.println("1. Play");
        System.out.println("2. Credits");
        System.out.println("3. Quit");
        System.out.print("> ");
    }
}