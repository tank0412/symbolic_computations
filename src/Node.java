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
    public void traverseInOrder(Object object) {
        if(object == null) {
                return;
    }
        if( ((Node)object).id != Expressions.digit) {
            System.out.print(" " + ((Node) object).id.name());
            for(Node node2: ((Node)object).arguments) {
                traverseInOrder(node2);
            }
            return;
        }
        else {
            System.out.print(" " + ((Digit) object).value);
            for(Node node2: ((Node)object).arguments) {
                traverseInOrder(node2);
            }
            return;
        }

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
