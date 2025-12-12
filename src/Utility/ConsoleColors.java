package Utility;

public class ConsoleColors {

    public enum Colors {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        MAGENTA("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        Colors(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }

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

    public static void println(String text, Colors color) {
        System.out.println(color + text + Colors.RESET);
    }

    public static void print(String text, Colors color) {
        System.out.print(color + text + Colors.RESET);
    }
}