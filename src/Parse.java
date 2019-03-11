import java.util.Scanner;

public class Parse {
    char[] getInput() {
        System.out.println("Enter  an expression");
        Scanner reader = new Scanner(System.in);
        String expr = reader.nextLine();
        /*
        Write write = new Write();
        write.writeText(expr.toCharArray());
        */
        return expr.toCharArray();
    }
    Node getContext() {
        Node result = new Node(0, Expressions.pow);
        result.left = new Node(3, Expressions.digit);
        result.right = new Node(0, Expressions.x);
        return result;
    }
    }