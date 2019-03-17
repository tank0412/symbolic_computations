public class Node<T> {
    Expressions id;
    T left;
    Node right;

    Node() {}

    Node(Expressions id) {
        this.id = id;
        right = null;
        left = null;
    }
    public void traverseInOrder(Node node) {
            if (node != null) {
                if( node.id != Expressions.digit) {
                    System.out.print(" " + (node.id.name()));
                }
                else {
                    System.out.print(" " + ((Digit) node).value);
                }
                traverseInOrder(node.left);
                traverseInOrder((Object)node.right);
            }
    }
    public void traverseInOrder(Object object) {
        if(object == null) {
                return;
    }
        if( ((Node)object).id != Expressions.digit) {
            System.out.print(" " + ((Node) object).id.name());
            traverseInOrder(((Node)object).left);
            traverseInOrder(((Node) object).right);
            return;
        }
        else {
            System.out.print(" " + ((Digit) object).value);
            traverseInOrder(((Node)object).left);
            traverseInOrder(((Node) object).right);
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
}
