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
        /*
        Node result = new Node(0, Expressions.pow);
        result.left = new Node(3, Expressions.digit);
        result.right = new Node(0, Expressions.x);
        */
        /*
        Node result = new Node(0, Expressions.sin);
        result.right = new Node(3, Expressions.x);
        */
        Node result = new Node(0, Expressions.plus);
        result.left = new Node(3, Expressions.sin);
        result.left.right = new Node(3, Expressions.x);
        result.right = new Node(3, Expressions.cos);
        result.right.right = new Node(3, Expressions.x);
        return result;
    }
    }