import java.util.Scanner;

public class Parse {
    static Node context = null;

    char[] getInput() {
        System.out.println("Enter  an expression");
        Scanner reader = new Scanner(System.in);
        String expr = reader.nextLine();
        return expr.toCharArray();
    }

    Node getContext() {
        if (context == null) {
            System.out.println("Using a default context");
            /*
            Node result = new Node(Expressions.plus);
            Node left = new Node(Expressions.sin, result);
            left.arguments.add(new Node(Expressions.x, left));
            Node right = new Node(Expressions.cos, result);
            right.arguments.add(new Node(Expressions.x, right));
            result.arguments.add(left);
            result.arguments.add(right);
            */
            Node result = new Node(Expressions.sqrt);
            result.arguments.add(new Node(Expressions.x, result));
            return result;
        } else {
            return context;
        }
    }
}