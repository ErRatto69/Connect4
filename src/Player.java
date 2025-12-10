import java.awt.Color;

public class Player{
    private String username;
    private Color color;
    private char checkerCharacter;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public char getCheckerCharacter() {
        return checkerCharacter;
    }

    public void setCheckerCharacter(char checkerCharacter) {
        this.checkerCharacter = checkerCharacter;
    }
}