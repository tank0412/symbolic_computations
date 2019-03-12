import java.util.Scanner;

public class Parse {
    static Node context = null;
    char[] getInput() {
        System.out.println("Enter  an expression Or Symbolic Algo");
        System.out.println("To change context print context before expression");
        Scanner reader = new Scanner(System.in);
        String expr = reader.nextLine();
        /*
        Write write = new Write();
        write.writeText(expr.toCharArray());
        */
        return expr.toCharArray();
    }
    char[] getInputAgain() {
        System.out.println("Context is changed. Enter algo now");
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
        if(context == null) {
            System.out.println("Using a default context");
            Node result = new Node(0, Expressions.plus);
            result.left = new Node(0, Expressions.sin);
            result.left.right = new Node(0, Expressions.x);
            result.right = new Node(0, Expressions.cos);
            result.right.right = new Node(0, Expressions.x);
            return result;
        }
        else {
            return context;
        }
    }
    }