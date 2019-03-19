import java.util.ArrayList;
import java.util.Scanner;

public class Parse {
    static Node context = null;

    char[] getInput() {
        System.out.println("Enter  an expression Or Symbolic Algo");
        Scanner reader = new Scanner(System.in);
        String expr = reader.nextLine();
        Import.rules=  new ArrayList<Node>();
        return expr.toCharArray();
    }

    char[] getInputAgain() {
        System.out.println("Context is changed. Enter algo now");
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
            Node result = new Node(Expressions.arccos);
            Node sqrt = new Node(Expressions.sqrt, result);
            sqrt.arguments.add(new Node(Expressions.x, sqrt));
            sqrt.parent=result;
            result.arguments.add(sqrt);

            /*
            Node result = new Node(Expressions.arccos);
            result.arguments.add(new Node(Expressions.x, result));
            */
            /*
            Node result = new Node(Expressions.log);
            result.arguments.add(new Node(Expressions.a, result));
            result.arguments.add(new Node(Expressions.x, result));
            */
            return result;
        } else {
            return context;
        }
    }
}