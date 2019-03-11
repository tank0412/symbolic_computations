public class Node {
    Expressions id;
    Node left;
    Node right;
    double value;

    Node(int value, Expressions id) {
        this.id = id;
        this.value = value;
        right = null;
        left = null;
    }
    public void traverseInOrder(Node node) {
            if (node != null) {
                traverseInOrder(node.left);
                if(node.value == 0.0) {
                    System.out.print(" " + node.id.name());
                }
                else {
                    System.out.print(" " + node.value);
                }
                traverseInOrder(node.right);
            }
    }
    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.value + node.id.name());
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }
    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(" " + node.value + node.id.name());
        }
    }
}