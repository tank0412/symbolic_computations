public class Import {
    private int ptr = 0;
    private boolean isPowAgain = false;
    private Node sinNode;
    private Node previousNode;
    public Node converttoSymbolic(char[] text) {
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
                        powNode.arguments.add(convertAsciMathToSymbolic(subFunc));
                        isPowAgain = false;
                        left = new Digit((int) (text[i+1] - '0'), powNode);
                        powNode.arguments.add(left);
                        previousNode = powNode;
                    }
                }
                }
        }

        return  previousNode;
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
            if(startBracket==endBracket && startBracket != 0 ) {
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

        if(text[i] == 'x' ) { //x only
            return Expressions.x;
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

        if(text[i] == '+') {
            return Expressions.plus;
        }
        if(text[i] == '-') {
            return Expressions.minus;
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
        return null;
    
    }
    private void checkX(char[] text, int i) {
        if(checkInput(text,i) == Expressions.x) {
            sinNode.arguments.add(new Node(Expressions.x,sinNode ));

        }
        else{
            hardArgument(text,i,sinNode);
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
        ptr = i;
        node.arguments.add(convertAsciMathToSymbolic(text));
        previousNode = node;
    }
}
