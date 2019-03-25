package com.symbolic;

class Symbolic {
    public static void main(String[] args) {
        char[] text = new char[100];
        Node importedNode;
        Node transofmedNode;
        Node exportedNode;
        Write write = new Write();
        Parse parse = new Parse();
        text = parse.getInput(); // get char array after input
        Import import_mine = new Import();
        importedNode = import_mine.converttoSymbolic(text);// convert char to node (internal symbolic view)
        if(importedNode.id == Expressions.context) {
            Parse.context = importedNode.arguments.get(0);
            text = parse.getInputAgain();
            Import import_mine2 = new Import();
            importedNode = import_mine2.converttoSymbolic(text);
        }
        if(Import.rules == null) {
            write.writeNode(importedNode);
        }
        Transform transform = new Transform();
        transofmedNode = transform.derivate(importedNode); //transform node with expression to node with derivated expression
        Export export = new Export();
        exportedNode = export.getOutput(transofmedNode);
        if(exportedNode != null) {
            write.writeNode(exportedNode);
        }
        return;

    }
}