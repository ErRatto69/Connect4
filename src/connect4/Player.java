package connect4;

import utility.ConsoleColors;

/**
 * The Player class represents a player in the Connect 4 game.
 * It stores the player's username, color, symbol, whether the player is a bot, and the bot's difficulty.
 */
public class Player{
    private String username;
    private ConsoleColors.Colors color;
    private char symbol;
    private boolean bot;
    private byte difficulty;

    /**
     * Creates a new Player with the specified username.
     * @param username The username of the player.
     */
    public Player(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the player.
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player.
     * @param username The new username of the player.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the color of the player.
     * @return The color of the player.
     */
    public ConsoleColors.Colors getColor() {
        return color;
    }

    /**
     * Sets the color of the player.
     * @param color The new color of the player.
     */
    public void setColor(ConsoleColors.Colors color) {
        this.color = color;
    }

    /**
     * Returns the symbol of the player.
     * @return The symbol of the player.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the player.
     * @param symbol The new symbol of the player.
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns true if the player is a bot, false otherwise.
     * @return True if the player is a bot, false otherwise.
     */
    public boolean isBot() {
        return bot;
    }

    /**
     * Sets whether the player is a bot or not.
     * @param bot True if the player is a bot, false otherwise.
     */
    public void setBot(boolean bot) {
        this.bot = bot;
    }

    /**
     * Returns the difficulty of the bot player.
     * @return The difficulty of the bot player.
     */
    public byte getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the bot player.
     * @param difficulty The new difficulty of the bot player.
     */
    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }
}