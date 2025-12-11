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

    public void release (){
        scanner.close();
    }
}