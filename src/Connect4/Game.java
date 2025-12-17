package Connect4;

import Utility.Animation;
import Utility.ConsoleColors;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Match> matches;
    private List<Player> players;
    private InputManager inputManager;
    private int matchesNumber;
    private int columns;
    private int rows;

    public Game() {
        this.matches = new ArrayList<>();
        this.players = new ArrayList<>();
        this.inputManager = new InputManager();
    }

    public void play() {
        for (int i = 0; i < matchesNumber; i++) {
            ConsoleColors.println("\n=== MATCH " + (i + 1) + " of " + matchesNumber + " ===", ConsoleColors.Colors.CYAN);
            Match match = new Match(this.columns, this.rows, players, inputManager);
            matches.add(match);
        }
        printFinalResults();
    }

    private void printFinalResults() {
        Animation.typeWriter("\n╔══════════════════════════════╗\n║        FINAL RESULTS         ║\n╚══════════════════════════════╝", ConsoleColors.Colors.YELLOW,50);
//        ConsoleColors.println("\n╔══════════════════════════════╗", ConsoleColors.Colors.YELLOW);
//        ConsoleColors.println("║        FINAL RESULTS         ║", ConsoleColors.Colors.YELLOW);
//        ConsoleColors.println("╚══════════════════════════════╝", ConsoleColors.Colors.YELLOW);

        Player overallWinner = null;
        int maxWins = -1;

        for (Player player : players) {
            int wins = 0;
            for (Match m : matches) {
                if (m.getWinner() == player) {
                    wins++;
                }
            }
            System.out.println(player.getUsername() + ": " + wins + " wins");

            if (wins > maxWins) {
                maxWins = wins;
                overallWinner = player;
            }
        }

        System.out.println("--------------------------------");

        if (overallWinner != null) {
            ConsoleColors.println("THE GRAND WINNER IS: " + overallWinner.getUsername().toUpperCase() + "!", ConsoleColors.Colors.GREEN);
        }
    }

    public void initializeTEST(){
        players.clear();
        players.add(new Player("Albert"));
        players.add(new Player("Hermano"));
        players.get(0).setColor(ConsoleColors.Colors.YELLOW);
        players.get(0).setSymbol('X');
        players.get(1).setColor(ConsoleColors.Colors.BLUE);
        players.get(1).setSymbol('#');

        matchesNumber = 1;
        columns = 7;
        rows = 6;
    }
    public void initialize() {
        players.clear();

        boolean addingPlayers = true;
        System.out.println("Setting up players...");
        System.out.println("Leave blank to stop creating players!");

        while (addingPlayers) {
            System.out.println("Player " + (players.size() + 1) + ":");
            String name = inputManager.getPlayerName();

            if (name.isEmpty()) {
                if (players.size() >= 2) {
                    addingPlayers = false;
                    break;
                } else {
                    ConsoleColors.println("There must be at least 2 players",ConsoleColors.Colors.RED);
                    continue;
                }
            }

            if (isPlayerUsernameTaken(name)) {
                ConsoleColors.println("Username '" + name + "' already used.", ConsoleColors.Colors.RED);
            } else {
                players.add(new Player(name));
                ConsoleColors.println("Player " + name + " added!", ConsoleColors.Colors.GREEN);
            }
        }

        for (Player player : players) {
            System.out.print("Configuration for ");
            ConsoleColors.println(player.getUsername(), ConsoleColors.Colors.MAGENTA);

            char symbol = inputManager.getPlayerCharacter();
            while (isPlayerSymbolTaken(symbol)) {
                ConsoleColors.println("Symbol '" + symbol + "' used. Choose another.", ConsoleColors.Colors.RED);
                symbol = inputManager.getPlayerCharacter();
            }
            player.setSymbol(symbol);

            int colorIdx = inputManager.getPlayerColor();
            ConsoleColors.Colors color = ConsoleColors.Colors.fromInt(colorIdx);
            while (isPlayerColorTaken(color)) {
                ConsoleColors.println("Color used. Choose another.", ConsoleColors.Colors.RED);
                colorIdx = inputManager.getPlayerColor();
                color = ConsoleColors.Colors.fromInt(colorIdx);
            }
            player.setColor(color);
        }

        this.matchesNumber = inputManager.getMatchesNumber();
        System.out.println("Board size:");
        this.columns = inputManager.getIntValue("Columns", 7, 5, 30, true);
        this.rows = inputManager.getIntValue("Rows", 6, 5, 30, true);
    }

    private boolean isPlayerUsernameTaken(String name) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private boolean isPlayerSymbolTaken(char symbol) {
        for (Player player : players) {
            if (player.getSymbol() == symbol) return true;
        }
        return false;
    }

    private boolean isPlayerColorTaken(ConsoleColors.Colors color) {
        for (Player player : players) {
            if (player.getColor() == color) return true;
        }
        return false;
    }
}