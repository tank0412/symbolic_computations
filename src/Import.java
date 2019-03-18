import java.util.ArrayList;

public class Import {
    private int ptr = 0;
    private boolean isPowAgain = false;
    private Node sinNode;
    private Node previousNode;
    public static ArrayList<Node> rules;
    public Node converttoSymbolic(char[] text) {
        rules = new ArrayList<Node>();
        Node node = convertAsciMathToSymbolic(text);
        return node;

    }
    private Node convertAsciMathToSymbolic(char[] text) {
        Node powNode;
        Expressions expr;
        Node left;
        for(int i = ptr; i < text.length; ++i) {
            if(i < ptr){
                i=ptr;
            }
            if(i >= text.length || text[i] == 0 ) {
                break;
            }
            expr = checkInput(text,i);
            if(expr!= null) {
                if(expr == Expressions.sin ||expr == Expressions.cos||expr == Expressions.ctg) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i +=4;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.tg) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i +=3;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.arcsin ||expr == Expressions.arccos||expr == Expressions.arcctg ) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i +=7;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.arctg ) {
                    sinNode = new Node(expr);
                    i +=6;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.sqrt ) {
                    sinNode = new Node(expr);
                    i +=5;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.ln ) {
                    sinNode = new Node(expr);
                    i +=3;
                    checkX(text,i);
                    ++i;
                }
                if(expr == Expressions.log ) {
                    Node logNode = new Node(expr);
                    i +=4;
                    if(checkInput(text,i) == Expressions.a) {
                        logNode.arguments.add(new Node(Expressions.a, logNode ));
                    }
                    i+=2;
                    sinNode=logNode;
                    checkX(text,i);
                    ++i;
                    previousNode = logNode;
                }
                if(expr == Expressions.exponent ) {
                    Node exprNode = new Node(Expressions.pow);
                    i +=3;
                    exprNode.arguments.add(new Node(Expressions.exponent, exprNode ));
                    if(checkInput(text,i) == Expressions.x) {
                        exprNode.arguments.add(new Node(Expressions.x, exprNode ));
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,exprNode);
                    }
                    previousNode = exprNode;
                }
                if(expr == Expressions.plus ) {
                    doJobForMathSymbol(expr,i,text);
                    i = ptr;
                    break;
                }
                if(expr == Expressions.minus ) {
                    doJobForMathSymbol(expr,i,text);
                    i = ptr;
                    break;

                }
                if(expr == Expressions.div ) {
                    doJobForMathSymbol(expr,i,text);
                    i = ptr;
                    break;

                }
                if(expr == Expressions.mul ) {
                    doJobForMathSymbol(expr,i,text);
                    i = ptr;
                    break;

                }
                if(expr == Expressions.x ) {
                    return new Node(Expressions.x);

                }
                if(expr == Expressions.pow) {
                   int BracketCountOpen = 0, BracketCountClose = 0;
                   int q = 0;
                   for(q = i; q < text.length; --q){
                       if((BracketCountOpen==BracketCountClose && (BracketCountOpen !=0)  ) || text[q] == 0) {
                           break;
                       }
                       if(checkInput(text,q) == Expressions.openBracket) {
                           BracketCountOpen++;
                       }
                       if(checkInput(text,q) == Expressions.closeBracket) {
                           BracketCountClose++;
                       }
                   }
                    if(isPowAgain) {
                        i=i+1;
                        continue;
                    }
                    if(((i-1) - (q+1)) == 2){  // значит это (x)
                        powNode = new Node(Expressions.pow );
                        powNode.arguments.add(new Node(Expressions.x, powNode ));
                        left = new Digit((int) (text[i+1] - '0'), powNode);
                        powNode.arguments.add(left);
                        previousNode = powNode;
                    }
                    else{
                        powNode = new Node(Expressions.pow );
                        ptr = q+1;
                        isPowAgain = true;
                        char subFunc[] = new char[100];
                        for(int c = q+2, index = 0; c <(i-1); ++c,++index){
                            subFunc[index] = text[c];
                        }
                        Import myImp = new Import();
                        powNode.arguments.add(myImp.convertAsciMathToSymbolic(subFunc));
                        isPowAgain = false;
                        left = new Digit((int) (text[i+1] - '0'), powNode);
                        powNode.arguments.add(left);
                        previousNode = powNode;
                    }
                }
                if(expr == Expressions.Assume) {
                    char[] firstArg = new char[100];
                    char[] secondArg = new char[100];
                    char[] thirdArg = new char[100];
                    int index = 0;
                    int z = i+2;
                    do{
                        if(text[z] != ' ') {
                            firstArg[index] = text[z];
                            index++;
                        }
                        z++;
                    }
                    while(checkInput(text,z) != Expressions.separator);
                    index = 0;
                    z++;
                    do{
                        if(text[z] != ' ') {
                            secondArg[index] = text[z];
                            index++;
                        }
                        z++;
                    }
                    while(checkInput(text,z) != Expressions.openBracket);
                    secondArg[index] = '(';
                    z++;
                    for(int x = z, myindex=0; x < text.length; ++ x, ++myindex) {
                        if(firstArg[index] != 0) {
                            z++;
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    do {
                        z++;
                    }
                    while(checkInput(text,z) != Expressions.arrrow);
                    z+=3;
                    //z+= firstArg.length + 1;
                    index = 0;
                    do {
                        if(text[z] != ' ') {
                            thirdArg[index] = text[z];
                            index++;
                        }
                        z++;
                    }
                    while(z < text.length&& text[z] != 0);
                    z++;
                    secondArg = addArgument(secondArg,firstArg);
                    Import importMy = new Import();
                    Node first = importMy.converttoSymbolic(secondArg);
                    Import importMy2 = new Import();
                    Node second = importMy2.converttoSymbolic(thirdArg);
                    Node rule = new Node(Expressions.Rule);
                    first.parent=rule;
                    second.parent=rule;
                    rule.arguments.add(first);
                    rule.arguments.add(second);
                    rules.add(rule);

                }
                }
        }
             return previousNode;
    }
    private void hardArgument(char[] text,int i, Node node) {
        int startBracket, endBracket;
        char subFunc[] = new char[100];
        startBracket = 0; endBracket = 0;
        for(int c = i, index = 0; ; ++c,++index){
            if(checkInput(text,c) == Expressions.closeBracket){
                endBracket++;
            }
            if(checkInput(text,c) == Expressions.openBracket){
                startBracket++;
            }
            if((startBracket==endBracket && startBracket != 0) || text[c] == 0 ) {
                //subFunc[index] = text[c];
                //index++;
                break;
            }
            subFunc[index] = text[c];
        }
        Import import2 = new Import();
        Node argumentNode = import2.convertAsciMathToSymbolic(subFunc);
        argumentNode.parent = node;
        node.arguments.add(argumentNode);
        previousNode = node;
    }
    private Expressions checkInput(char[] text, int i) {
        if(i >= text.length || text[i] == 0) return null;
        if(text[i] == 's' && text[i + 1] == 'i' && text[i + 2] == 'n') { //sin
            ptr+=2;
            return Expressions.sin;
        }

        if(i +1 < text.length) {
            if (text[i] == 'x' && text[i + 1] != '*') { //x only
                return Expressions.x;
            }
        }
        else {
            if (text[i] == 'x') { //x only
                return Expressions.x;
            }
        }

        if(text[i] == '^' && Character.isDigit(text[i + 1]) ) {//pow
            ptr+=1;
            return Expressions.pow;
        }
        if(text[i] == 'c' && text[i + 1] == 'o' && text[i + 2] == 's') { //cos
            ptr+=2;
            return Expressions.cos;
        }
        if(text[i] == 't' && text[i + 1] == 'g') { //tg
            ptr+=1;
            return Expressions.tg;
        }
        if(text[i] == 'c' && text[i + 1] == 't'&& text[i + 2] == 'g') { //ctg
            ptr+=2;
            return Expressions.ctg;
        }
        if(text[i] == 's' && text[i + 1] == 'q'&& text[i + 2] == 'r'&& text[i + 3] == 't') { //sqrt
            ptr+=3;
            return Expressions.sqrt;
        }
        if(text[i] == 'l' && text[i + 1] == 'n') { //ln
            ptr+=1;
            return Expressions.ln;
        }
        if(text[i] == 'e' && text[i + 1] == '^') { //exponent
            ptr+=1;
            return Expressions.exponent;
        }
        if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c'&& text[i + 3] == 's'&& text[i + 4] == 'i'&& text[i + 5] == 'n') {//arcsin
            ptr+=5;
            return Expressions.arcsin;
        }
        if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c'&& text[i + 3] == 'c'&& text[i + 4] == 'o'&& text[i + 5] == 's') {//arccos
            ptr+=5;
            return Expressions.arccos;
        }
        if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c'&& text[i + 3] == 't'&& text[i + 4] == 'g') {//arctg
            ptr+=4;
            return Expressions.arctg;
        }
        if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c'&& text[i + 3] == 'c'&& text[i + 4] == 't'&& text[i + 5] == 'g') {//arcctg
            ptr+=5;
            return Expressions.arcctg;
        }
        if(text[i] == 'l' && text[i + 1] == 'o' && text[i + 2] == 'g') { //log
            ptr+=2;
            return Expressions.log;
        }
        if(text[i] == 'd' && text[i + 1] == '(') { //d(
            ptr+=1;
            return Expressions.Assume;
        }

        if(text[i] == '-' && text[i + 1] == '>') { //->
            ptr+=1;
            return Expressions.arrrow;
        }

        if(text[i] == '+') {
            return Expressions.plus;
        }
        if(text[i] == '-') {
            return Expressions.minus;
        }
        if(text[i] == '/') {
            return Expressions.div;
        }
        if(text[i] == '*') {
            return Expressions.mul;
        }
        if(text[i] == '(') {
            return Expressions.openBracket;
        }
        if(text[i] == ')') {
            return Expressions.closeBracket;
        }
        if(Character.isDigit(text[i])) { //x only
            return Expressions.digit;
        }
        if(text[i] == 'a') {
            return Expressions.a; //a
        }
        if(text[i] == ',') {
            return Expressions.separator; //,
        }
        return null;
    
    }
    private void checkX(char[] text, int i) {
        if(checkInput(text,i) == Expressions.x) {
            sinNode.arguments.add(new Node(Expressions.x,sinNode ));

        }
        else{
            if(checkInput(text,i) == Expressions.a) {
                sinNode.arguments.add(new Node(Expressions.a,sinNode ));

            }
            else {
                hardArgument(text, i, sinNode);
            }
        }
        previousNode = sinNode;
    }
    private void doJobForMathSymbol(Expressions expr, int i, char[] text) {
        Node node = new Node(expr);
        i +=1;
        if(previousNode != null) {
            previousNode.parent=node;
            node.arguments.add(previousNode);
        }
        else {
            if(checkInput(text, i-1) == Expressions.div || checkInput(text, i-1) == Expressions.mul ) {
                if(Character.isDigit(text[i - 2] ) == true) {
                    Digit digit = new Digit(text[i - 2] - '0'); // for div
                    node.arguments.add(digit);
                }
                else{
                    char[] array = new char[1];
                    array[0] = text[i-2];
                    Node node2 = new Node(checkInput(array, 0));
                    node.arguments.add(node2);
                }
            }
        }
        ptr = i;
        node.arguments.add(convertAsciMathToSymbolic(text));
        previousNode = node;
    }
    public char[] addArgument(char[] func, char[] arg) {
        int i;
        for(i= 0; i < func.length; ++i) {
            if(func[i] == 0) {
                for(int j = 0; j < arg.length; ++j) {
                    if(arg[j] != 0) {
                        func[i] = arg[j];
                        i++;
                    }
                    else {
                        break;
                    }
                }
                break;
            }
        }
        func[i] = ')';
        return  func;
    }
}
