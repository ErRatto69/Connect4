import Utility.ConsoleColors;

import java.util.Scanner;

public class InputManager{
    public static final String[] COLOR_LABELS = {
        "Red",
        "Green",
        "Yellow",
        "Blue",
        "Mangenta",
        "Cyan",
        "White"
    };
    public Scanner scanner;
    InputManager(){
        scanner = new Scanner(System.in);
    }
    
    public char getPlayerCharacter(){
        System.out.println("Please insert a character who you wanna play in this game:");
        String input;
        while(!scanner.hasNext() || (input = scanner.next()).length() != 1 || input == " "){
            System.err.println("Cannot insert a word longer than a character");
            System.out.println("Again: please insert a single character othr than space");
            scanner.nextLine();
        }
        return input.charAt(0);
    }
    public String getPlayerName(){
        System.out.print("\tChoose your username: ");
        return scanner.nextLine();
    }
    public int getPlayerColor(){
        int color;
        System.out.println("Please choose your color [1-7]:");
        showColors();
        while (!scanner.hasNext() || (color =  scanner.nextInt()) < 1 ||  color > 7 ) {
            System.err.println("Insert a number relative one color");
            System.out.println("Please choose your color [1-7]:");
            scanner.nextLine();
        }
        return color;
    }
    public void showColors(){
        //Colors goes between 41 and 47, (change color \u001b[31m) (reset \u001b[0m) everytime reset
        for (int i = 0; i < COLOR_LABELS.length; i++) {
            if(i%2==0){
                System.out.println();
            }
            System.out.print(COLOR_LABELS[i] + "["+(i+1)+"]\t" );
        }
        System.out.print("\nColor: ");
    }
    public int readInteger(int min, int max) {
        int value;
        while (true) {
            while (!scanner.hasNextInt()) {
                System.err.println("Per favore, inserisci un numero valido.");
                scanner.next();
            }
            value = scanner.nextInt();
            scanner.nextLine();

            if (value >= min && value <= max) {
                return value;
            } else {
                System.err.println("Inserisci un numero tra " + min + " e " + max);
            }
        }
    }

    public int getMatchesNumber(){
        int number;
        System.out.println("Please enter an odd number of matches [MAX 15]:");
        while (!scanner.hasNext() || (number =  scanner.nextInt()) < 1 ||  number > 15 || number%2==0 ) {
            System.err.println("Insert a valid number, must be between 1 and 15 and must be odd");
            System.out.println("Please enter an odd number of matches [MAX 15]:");
            scanner.nextLine();
        }
        return number;
    }

    public int getIntValue(String name,int defaultValue, int max, int min, boolean indent) {
        int number;

        while (true) {
            System.out.print((indent ? "\t" : "") + name+ " [Default: " + defaultValue + "]:");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            if (input.matches("[0-9]+")) {

                number = Integer.parseInt(input);

                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println((indent ? "\t" : "") + "Insert a valid number between included "+min+" and "+max+".");
                }
            } else {
                System.err.println((indent ? "\t" : "") + "Invalid input. Please enter a numeric value (no letters).");
            }
        }
    }

    public void release (){
        scanner.close();
    }
}