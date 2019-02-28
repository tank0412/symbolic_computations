import java.util.Scanner;

public class Parse {
    char[] getInput() {
        System.out.println("Enter  an expression");
        Scanner reader = new Scanner(System.in);
        String expr = reader.nextLine();
        //System.out.println(expr);
        return expr.toCharArray();
    }
    }