import java.util.ArrayList;

public class Node {
    Expressions id;
    Node parent;
    ArrayList<Node> arguments = new ArrayList<Node>();

    Node() {}

    Node(Expressions id) {
        this.id = id;
        parent = null;
    }
    Node(Expressions id, Node parentNode) {
        this.id = id;
        parent = parentNode;
    }
    Node(Node parentNode) {
        parent = parentNode;
    }
    public void traverseInOrder(Node node) {
            if (node != null) {
                if( node.id != Expressions.digit) {
                    System.out.print(" " + (node.id.name()));
                }
                else {
                    System.out.print(" " + ((Digit) node).value);
                }
                for(Node node2: node.arguments) {
                    traverseInOrder(node2);
                }
            }
    }
    public Node traverseInOrderCopy(Node focusNode) {
        if (focusNode == null) {
            return null;
        }
        Node copy = new Node(focusNode.id, focusNode.parent);
        for(Node node2: focusNode.arguments) {
            copy.arguments.add(traverseInOrderCopy(node2));
        }
        return copy;
    }
}
class Digit extends Node {
    public double value;
    Digit(double value) {
        this.value = value;
        id = Expressions.digit;
    }
    Digit(double value, Node parentNode) {
        this.value = value;
        id = Expressions.digit;
        parent = parentNode;
    }
}
