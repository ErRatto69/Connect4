import java.util.Scanner;

public class InputManager{
    public Scanner scanner;
    InputManager(){
        scanner = new Scanner(System.in);
    }
    
    public char getPlayerCharacter(){
        System.out.println("Please insert a character who you wanna play in this game");
        String input;
        while(!scanner.hasNext() || (input = scanner.next()).length() != 1 || input == " "){
            System.err.println("Cannot insert a word longer than a character");
            System.out.println("Again: please insert a single character othr than space");
            scanner.nextLine();
        }
        return input.charAt(0);
    }
    public String getPlayerName(){
        return scanner.next();
    }

    public void release (){
        scanner.close();
    }
}