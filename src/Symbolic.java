class Symbolic {
    public static void main(String[] args) {
        char[] text = new char[100];
        char[] symb = new char[100];
        char[] derivated = new char[100];
        Parse parse = new Parse();
        text = parse.getInput();
        Import import_mine = new Import();
        symb = import_mine.convertToAsciiMath(text);
        Transform transform = new Transform();
        derivated = transform.derivate(symb);
        System.out.println(derivated);
        return;

    }
}
