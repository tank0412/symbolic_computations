public class Write {
    public void writeText(char[] Text) {
        System.out.println(Text);
    }
    public void writeNode(Node node) {
        node.traverseInOrder(node);
        System.out.println(" ");
    }
}
