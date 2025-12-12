import Utility.ConsoleColors;

public class Player{
    private String username;
    private ConsoleColors.Colors color;
    private char symbol;

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
}