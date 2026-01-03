package connect4;

import utility.Animation;
import utility.ConsoleColors;

import java.awt.*;
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

    // The play method creates the matches and prints the final results.
    public void play() {
        for (int i = 0; i < matchesNumber; i++) {
            ConsoleColors.println("\n=== MATCH " + (i + 1) + " of " + matchesNumber + " ===", ConsoleColors.Colors.CYAN);
            Match match = new Match(this.columns, this.rows, players, inputManager);
            matches.add(match);
        }
        printFinalResults();
    }

    // The printFinalResults method prints the final results of the game.
    private void printFinalResults() {
        Animation.typeWriter("\n╔══════════════════════════════╗\n║        FINAL RESULTS         ║\n╚══════════════════════════════╝", ConsoleColors.Colors.YELLOW,50);

        Player overallWinner = null;
        int maxWins = -1;

        // Cycles each player to check the wins
        for (Player player : players) {
            int wins = 0;
            // Cycles the matches to check if the player won
            for (Match m : matches) {
                if (m.getWinner() == player) {
                    wins++;
                }
            }
            System.out.println(player.getUsername() + ": " + wins + " wins");

            // Sets the current player as overall winner if he has the most wins
            if (wins > maxWins) {
                maxWins = wins;
                overallWinner = player;
            }
        }

        System.out.println("--------------------------------");

        // Print the overall winner if there is one
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

    public void initializeBotsTEST(){
        players.clear();
        players.add(new Player("Albert"));
        players.add(new Player("Hermano"));
        players.get(0).setColor(ConsoleColors.Colors.YELLOW);
        players.get(0).setSymbol('X');
        players.get(0).setBot(true);
        players.get(0).setDifficulty((byte)4);
        players.get(1).setColor(ConsoleColors.Colors.BLUE);
        players.get(1).setSymbol('#');
        players.get(1).setBot(true);
        players.get(1).setDifficulty((byte)4);
        matchesNumber = 1;
        columns = 7;
        rows = 6;
    }

    /**
     * The initialize method is responsible for initializing the game, setting up players and configuring the game board.
     */
    public void initialize() {
        players.clear();

        boolean addingPlayers = true;
        System.out.println("Setting up players...");
        ConsoleColors.println("Leave blank to stop creating players!", ConsoleColors.Colors.YELLOW);
        ConsoleColors.println("[2-7]", ConsoleColors.Colors.CYAN);

        // loop to create players
        while (addingPlayers) {

            // check if there are already 7 players
            if (players.size() == 7) {
                addingPlayers = false;
                ConsoleColors.println("Cant have more than 7 players!", ConsoleColors.Colors.YELLOW);
                break;
            }
            System.out.println("Player " + (players.size() + 1) + ":");
            String name = inputManager.getPlayerName();

            // check if player's name is empty, in that case stops creating players
            if (name.isEmpty()) {
                if (players.size() >= 2) {
                    addingPlayers = false;
                    break;
                } else {
                    ConsoleColors.println("There must be at least 2 players",ConsoleColors.Colors.RED);
                    continue;
                }
            }

            // check if player's name is already used
            if (isPlayerUsernameTaken(name)) {
                ConsoleColors.println("Username '" + name + "' already used.", ConsoleColors.Colors.RED);
            } else {
                // add player to the list
                players.add(new Player(name));
                ConsoleColors.println("Player " + name + " added!", ConsoleColors.Colors.GREEN);
            }
        }

        // loop to configure each player
        for (Player player : players) {
            System.out.print("Configuration for ");
            ConsoleColors.println(player.getUsername(), ConsoleColors.Colors.MAGENTA);

            // configure player's symbol
            char symbol = inputManager.getPlayerCharacter();
            while (isPlayerSymbolTaken(symbol)) {
                ConsoleColors.println("Symbol '" + symbol + "' used. Choose another.", ConsoleColors.Colors.RED);
                symbol = inputManager.getPlayerCharacter();
            }
            player.setSymbol(symbol);

            // configure player's color
            int colorIdx = inputManager.getPlayerColor();
            ConsoleColors.Colors color = ConsoleColors.Colors.fromInt(colorIdx);
            while (isPlayerColorTaken(color)) {
                ConsoleColors.println("Color used. Choose another.", ConsoleColors.Colors.RED);
                colorIdx = inputManager.getPlayerColor();
                color = ConsoleColors.Colors.fromInt(colorIdx);
            }
            player.setColor(color);

            // configure player as bot and difficulty
            boolean isBot = inputManager.getYesNo("This player is a bot [yes/no]: ", false,true);
            int difficulty = 1;
            if (isBot) {
                player.setBot(true);
                difficulty = inputManager.getBotDifficulty();
            }

            player.setDifficulty((byte)difficulty);
        }

        // configure matches number
        this.matchesNumber = inputManager.getMatchesNumber();

        // configure board size
        System.out.println("Board size:");
        this.columns = inputManager.getIntValue("Columns", 7, 5, 30, true);
        this.rows = inputManager.getIntValue("Rows", 6, 5, 30, true);
    }

    // The isPlayerUsernameTaken returns true if there is already a player with the specified username.
    private boolean isPlayerUsernameTaken(String name) {
        // loop through the list of players
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    // The isPlayerSymbolTaken returns true if there is already a player with the specified symbol.
    private boolean isPlayerSymbolTaken(char symbol) {
        // loop through the list of players
        for (Player player : players) {
            if (player.getSymbol() == symbol){
                return true;
            }
        }
        return false;
    }

    // The isPlayerColorTaken returns true if there is already a player with the specified color.
    private boolean isPlayerColorTaken(ConsoleColors.Colors color) {
        // loop through the list of players
        for (Player player : players) {
            if (player.getColor() == color) {
                return true;
            }
        }
        return false;
    }
}