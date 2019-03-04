class Symbolic {
    public static void main(String[] args) {
        char[] text = new char[100];
        char[] symb = new char[100];
        char[] derivated = new char[100];
        char[] exported = new char[100];
        Write write = new Write();
        Parse parse = new Parse();
        text = parse.getInput();
        Import import_mine = new Import();
        symb = import_mine.convertToAsciiMath(text);
        Transform transform = new Transform();
        derivated = transform.derivate(symb);
        write.Write(derivated);
        Export export = new Export();
        String derivatedString = String.valueOf(derivated);
        exported = export.infixToPostfix(derivatedString);
        write.Write(exported);
        return;

    }
}
