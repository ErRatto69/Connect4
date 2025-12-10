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
        System.out.println("Setting up players...");
        System.out.println("Type '$' to stop creating players");
        boolean addingPlayers = true;
        while(addingPlayers){
            System.out.println("Player "+(players.size()+1));
            String name = inputManager.getPlayerName();
            while(name.equals("$")){
                if (players.size()>=2){
                    break;
                }
                System.out.println("Name must be different from '$'");
                name = inputManager.getPlayerName();
            }
            if(players.size() >= 2 && name.equals("$")){
                addingPlayers = false;
            }
            Player player = new Player(name);
            players.add(player);
        }
        for(Player player : players){
            System.out.println("Setting symbol and color for "+player.getUsername());
            char symbol = inputManager.getPlayerCharacter();
//            String color = inputManager.getPlayerColor();

            player.setCheckerCharacter(symbol);
        }
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}