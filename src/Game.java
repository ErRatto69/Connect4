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
    }

    public void initialize() {
        players.clear();

        boolean addingPlayers = true;
        System.out.println("Setting up players...");

        while (addingPlayers) {
            System.out.println("Player " + (players.size() + 1) + ":");
            String name = inputManager.getPlayerName();

            if (name.isEmpty()) {
                if (players.size() >= 2) {
                    addingPlayers = false;
                    break;
                } else {
                    System.err.println("There must be at least 2 players");
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