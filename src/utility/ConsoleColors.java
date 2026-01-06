package utility;

public class ConsoleColors {

    /**
     * Enum class representing the colors that can be used to print text to the console.
     */
    public enum Colors {
         // Represents the default console color.
        RESET("\u001B[0m"),

        // Represents the red console color.
        RED("\u001B[31m"),

        // Represents the green console color.
        GREEN("\u001B[32m"),

        // Represents the yellow console color.
        YELLOW("\u001B[33m"),

        // Represents the blue console color.
        BLUE("\u001B[34m"),

        // Represents the magenta console color.
        MAGENTA("\u001B[35m"),

        // Represents the cyan console color.
        CYAN("\u001B[36m"),

        // Represents the white console color.
        WHITE("\u001B[38m");

        private final String code;

        /**
         * Constructs a Colors object with the given color code.
         *
         * @param code The color code string representing the color.
         */
        Colors(String code) {
            this.code = code;
        }

        /**
         * Converts the enum value to a string representation of its color code.
         *
         * @return The color code string representing the color.
         */
        @Override
        public String toString() {
            return code;
        }

        /**
         * Returns the Colors enum value corresponding to the given integer value.
         *
         * @param n The integer value representing the color (1 for RED, 2 for GREEN, 3 for YELLOW, 4 for BLUE, 5 for MAGENTA, 6 for CYAN, 7 for WHITE).
         * @return The Colors enum value corresponding to the given integer value.
         */
        public static Colors fromInt(int n) {
            return switch (n) {
                case 1 -> RED;
                case 2 -> GREEN;
                case 3 -> YELLOW;
                case 4 -> BLUE;
                case 5 -> MAGENTA;
                case 6 -> CYAN;
                default -> WHITE;
            };
        }
    }


    /**
     * Prints a colored text with a newline character at the end.
     *
     * @param text The text to be printed
     * @param color The color of the text
     */
    public static void println(String text, Colors color) {
        System.out.println(color + text + Colors.RESET);
    }

    /**
     * Prints a colored text without a newline character at the end.
     *
     * @param text The text to be printed
     * @param color The color of the text
     */
    public static void print(String text, Colors color) {
        System.out.print(color + text + Colors.RESET);
    }
}