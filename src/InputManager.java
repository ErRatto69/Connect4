import java.util.Scanner;

public class InputManager{
    public static final String[] COLOR_LABELS = {"Red", "Green", "Yellow", "Blu", "Mangenta","Cyan","White"};
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
    public int getColor(){
        int color;
        while (!scanner.hasNext() || (color =  scanner.nextInt()) < 1 ||  color > 7 ) {
            System.err.println("Insert a number relative one color");
            System.out.println("Please insert a number between 1 and seven");
            scanner.nextLine();
        }
        return color;
    }
    public void showColors(){
        //Colors goes between 41 and 47, (change color \u001b[31m) (reset \u001b[0m) everytime reset
        for (int i = 0; i < COLOR_LABELS.length; i++) {
            System.out.println(COLOR_LABELS[i] + i);
        }
    }

    public void release (){
        scanner.close();
    }
}