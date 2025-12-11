import Utility.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class Game {

    // Matches list
    private List<Match> matches;

    // Current match index
    private int currentMatch;

    // Players list
    private List<Player> players;

    private InputManager inputManager;


    public Game()
    {
        this.matches = new ArrayList<>();
        this.players = new ArrayList<>();
        this.inputManager = new InputManager();
    }

    public void play()
    {

    }

    public void initialize(){
        boolean addingPlayers = true;

        while(addingPlayers){
            System.out.println("Player "+(players.size()+1)+":");
            String name = inputManager.getPlayerName();

            if(name.isEmpty()){
                if (players.size() >= 2){
                    addingPlayers = false;
                    break;
                }else{
                    System.err.println("There must be at least 2 players");
                }
            }

            if (isPlayerUsernameTaken(name)) {
                ConsoleColors.println("Username '" + name + "' already used. Choose another one.", ConsoleColors.Colors.RED);
            } else {
                Player player = new Player(name);
                players.add(player);
                ConsoleColors.println("Player " + name + " added!", ConsoleColors.Colors.GREEN);
            }

        }
        for(Player player : players){
            System.out.print("Setting symbol and color for ");
            ConsoleColors.print(player.getUsername()+"\n", ConsoleColors.Colors.MAGENTA);

            char symbol = inputManager.getPlayerCharacter();
            while(isPlayerSymbolTaken(symbol)){
                ConsoleColors.println("Symbol '" + symbol + "' already used. Choose another one.", ConsoleColors.Colors.RED);
                symbol = inputManager.getPlayerCharacter();
            }

            ConsoleColors.Colors color = ConsoleColors.Colors.fromInt(inputManager.getPlayerColor());
            while (isPlayerColorTaken(color)){
                ConsoleColors.println("Color '" + color.name() + "' already used. Choose another one.", ConsoleColors.Colors.RED);
                color = ConsoleColors.Colors.fromInt(inputManager.getPlayerColor());
            }

            player.setSymbol(symbol);
            player.setColor(color);
        }
    }

    private boolean isPlayerUsernameTaken(String name) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerSymbolTaken(char symbol) {
        for (Player player : players) {
            if (player.getSymbol() == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerColorTaken(ConsoleColors.Colors color) {
        for (Player player : players) {
            if (player.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}