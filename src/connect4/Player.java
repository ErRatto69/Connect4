package connect4;
import utility.ConsoleColors;

public class Player{
    private String username;
    private ConsoleColors.Colors color;
    private char symbol;
    private boolean bot;
    private byte difficulty;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ConsoleColors.Colors getColor() {
        return color;
    }

    public void setColor(ConsoleColors.Colors color) {
        this.color = color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }
}