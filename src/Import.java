import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;

public class Import {
    private int ptr = 0;
    private int ptrBreak = 0;
    boolean isPowAgain = false;
    boolean needAppend = false;
    Node left;
    Node right;
    Node powNode;
    Node sinNode;
    Node previousNode;
    Node appendNode = null;
    int startBracket, endBracket;
    public Node converttoSymbolic(char[] text) {
        ptrBreak = text.length;
        Node node = convertAsciMathToSymbolic(text);
        return node;

    }
    public Node convertAsciMathToSymbolic(char[] text) {
        Node tempnode = null;
        ArrayList symb = new ArrayList();
        Expressions expr;
        for(int i = ptr; i < text.length; ++i) {
            if(i < ptr){
                i=ptr;
            }
            if(i >= text.length || text[i] == 0 || i == ptrBreak ) {
                break;
            }
            expr = checkInput(text,i);
            if(expr!= null) {
                if(expr == Expressions.context) {
                    char[] expr2 = new char[100];
                    expr2 = Arrays.copyOfRange(text, i, text.length);
                    Node node = convertAsciMathToSymbolic(expr2);
                    if(node != null) {
                        Parse.context = node;
                    }
                    /*
                    int backup = ptr;
                    ptr = i;
                    Node node = convertAsciMathToSymbolic(text);
                    ptr = backup;
                    if(node != null) {
                        Parse.context = node;
                    }
                    */
                    return new Node(0, expr);
                }
                if(expr == Expressions.sin ||expr == Expressions.cos||expr == Expressions.ctg) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    previousNode = sinNode;
                    i +=4;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                    }
                    else{
                        hardArgument(text,i,sinNode);
                    }
                }
                if(expr == Expressions.tg) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    previousNode = sinNode;
                    i +=3;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,sinNode);
                    }
                }
                if(expr == Expressions.arcsin ||expr == Expressions.arccos||expr == Expressions.arcctg ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    previousNode = sinNode;
                    i +=7;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else {
                        hardArgument(text,i,sinNode);
                    }
                }
                if(expr == Expressions.arctg ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    i +=6;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,sinNode);
                    }
                    previousNode = sinNode;
                }
                if(expr == Expressions.sqrt ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    i +=5;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,sinNode);
                    }
                    previousNode = sinNode;
                }
                if(expr == Expressions.ln ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    sinNode = new Node(0,expr);
                    i +=3;
                    if(checkInput(text,i) == Expressions.x) {
                        sinNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,sinNode);
                    }
                    previousNode = sinNode;
                }
                if(expr == Expressions.log ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    Node logNode = new Node(0,expr);
                    i +=4;
                    if(checkInput(text,i) == Expressions.a) {
                        logNode.left = new Node(0, Expressions.a);
                    }
                    i+=2;
                    if(checkInput(text,i) == Expressions.x) {
                        logNode.right=new Node(0, Expressions.x );
                        i++;
                        if(checkInput(text,i) == Expressions.closeBracket ) {
                            ;
                        }

                    }
                    else{
                        hardArgument(text,i,logNode);
                    }
                    previousNode = logNode;
                }
                if(expr == Expressions.exponent ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    Node exprNode = new Node(0,Expressions.pow);
                    i +=3;
                    exprNode.left = new Node(0,Expressions.exponent);
                    if(checkInput(text,i) == Expressions.x) {
                        exprNode.right=new Node(0, Expressions.x );
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
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    Node plusNode = new Node(0,expr);
                    plusNode = new Node(0,expr);
                    i +=1;
                    if(previousNode != null) {
                        plusNode.left = previousNode;
                    }
                    ptr = i;
                    plusNode.right= convertAsciMathToSymbolic(text);
                    previousNode = plusNode;
                    i = ptr;
                    continue;
                }
                if(expr == Expressions.minus ) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                    Node minusNode = new Node(0,expr);
                    i +=1;
                    if(previousNode != null) {
                        minusNode.left = previousNode;
                    }
                    ptr = i;
                    minusNode.right= convertAsciMathToSymbolic(text);
                    previousNode = minusNode;
                    i = ptr;
                    continue;

                }
                if(expr == Expressions.pow) {
                    if(checkIsCase(expr) == true){
                        continue;
                    }
                   int BracketCountOpen = 0, BracketCountClose = 0;
                   int q = 0;
                   for(q = i; q < text.length; --q){
                       if((BracketCountOpen==BracketCountClose && (BracketCountOpen !=0 && BracketCountClose !=0)  ) || text[q] == 0) {
                           break;
                       }
                       if(checkInput(text,q) == Expressions.openBracket) {
                           BracketCountOpen++;
                       }
                       if(checkInput(text,q) == Expressions.closeBracket) {
                           BracketCountClose++;
                       }
                   }
                   /*
                   System.out.println(BracketCountOpen);
                   System.out.println(BracketCountClose);
                   System.out.println(i); // -1
                   System.out.println(q); // +1
                   */
                    if(isPowAgain) {
                        i=i+1;
                        continue;
                    }
                    if(((i-1) - (q+1)) == 2){  // значит это (x)
                        powNode = new Node(0, Expressions.pow );
                        powNode.right = new Node(0,Expressions.x);
                        left = new Node((int) (text[i+1] - '0'), Expressions.digit );
                        powNode.left = left;
                        previousNode = powNode;
                    }
                    else{
                        powNode = new Node(0, Expressions.pow );
                        ptr = q+1;
                        isPowAgain = true;
                        char subFunc[] = new char[100];
                        for(int c = q+2, index = 0; c <(i-1); ++c,++index){
                            subFunc[index] = text[c];
                        }
                        powNode.right = convertAsciMathToSymbolic(subFunc);
                        isPowAgain = false;
                        left = new Node((int) (text[i+1] - '0'), Expressions.digit );
                        powNode.left = left;
                        previousNode = powNode;
                    }
                    //ptr = i;
                    //i=ptr;
                }
                if(expr == Expressions.Switch ) {
                    Node switchNode = new Node(0,expr);
                    previousNode = switchNode;
                    continue;
                }
                if(expr == Expressions.NodeId ) {
                    Node switchCondNode = new Node(0,expr);
                    Node node = findLastNotFullNode(previousNode);
                    if(node.id == Expressions.Switch) {
                        node.left = switchCondNode;
                    }
                    continue;
                }
                if(expr == Expressions.Case ) {
                    Node caseNode = new Node(0,expr);
                    Node node = findLastNotFullNode(previousNode);
                    if(needAppend == true) {
                        caseNode.right = appendNode;
                    }
                    if(node.id == Expressions.Switch || node.id == Expressions.body ) {
                        node.right = caseNode;
                    }
                    continue;
                }
                if(expr == Expressions.body ) {
                    Node bodyNode = new Node(0,expr);
                    Node node = findLastNotFullNode(previousNode);
                    if(node.id == Expressions.Case) {
                        node.right = bodyNode;
                    }
                    continue;
                }
                if(expr == Expressions.assign ) {
                    Node assignNode = new Node(0,expr);
                    Node node = findLastNotFullNode(previousNode);
                    if(node.id == Expressions.body) {
                        node.left = assignNode;
                    }
                    continue;
                }
                if(expr == Expressions.resultNode ) {
                    Node node = findLastNotFullNode(previousNode);
                    Node resultNodeNode = new Node(0,expr);
                    if(node.id == Expressions.assign) {
                        node.left = resultNodeNode;
                    }
                    if(node.left.id == Expressions.assign) {
                        node.left.left = resultNodeNode;
                    }
                    continue;
                }
                if(expr == Expressions.derivPow  || expr == Expressions.derivSin|| expr == Expressions.derivCos|| expr == Expressions.derivTg|| expr == Expressions.derivCtg|| expr == Expressions.derivArcsin|| expr == Expressions.derivArccos|| expr == Expressions.derivArctg || expr == Expressions.derivArcctg|| expr == Expressions.derivSqrt || expr == Expressions.derivLn|| expr == Expressions.derivLog || expr == Expressions.derivMinus|| expr == Expressions.derivPlus) {
                    appendFunc(expr);
                    continue;
                }
                if(expr == Expressions.context) {
                    Node exprFromContext = convertAsciMathToSymbolic(text);
                    Parse.context = exprFromContext;
                    return new Node(0, expr);
                }
                }
        }

        return  previousNode;
    }
    public void hardArgument(char[] text,int i, Node node) {
        char subFunc[] = new char[100];
        startBracket = 0; endBracket = 0;
        startBracket++;
        for(int c = i, index = 0; ; ++c,++index){
            if(checkInput(text,c) == Expressions.closeBracket){
                endBracket++;
            }
            if(checkInput(text,c) == Expressions.openBracket){
                startBracket++;
            }
            if(startBracket==endBracket && startBracket != 0 && endBracket != 0 ) {
                //subFunc[index] = text[c];
                //index++;
                break;
            }
            subFunc[index] = text[c];
        }
        Import import2 = new Import();
        Node argumentNode = import2.convertAsciMathToSymbolic(subFunc);
        node.right = argumentNode;
        previousNode = node;
    }
    public Expressions checkInput(char[] text, int i) {
        try {
            if (i >= text.length || text[i] == 0) {
                return null;
            }
            if (text[i] == 'S' && text[i + 1] == 'w' && text[i + 2] == 'i' && text[i + 3] == 't' && text[i + 4] == 'c' && text[i + 5] == 'h') {//Switch
                ptr += 5;
                return Expressions.Switch;
            }
            if (text[i] == 'c' && text[i + 1] == 'o' && text[i + 2] == 'n' && text[i + 3] == 't' && text[i + 4] == 'e' && text[i + 5] == 'x'&& text[i + 6] == 't') {//context
                ptr += 6;
                return Expressions.context;
            }
            if (text[i] == 's' && text[i + 1] == 'i' && text[i + 2] == 'n') { //sin
                ptr += 2;
                return Expressions.sin;
            }

            if (text[i] == 'x' && text[i+1] != 't'  ) { //x only
                return Expressions.x;
            }

            if (text[i] == '^' && Character.isDigit(text[i + 1])) {//pow
                ptr += 1;
                return Expressions.pow;
            }
            if (text[i] == 'p' && text[i + 1] == 'o' && text[i + 2] == 'w') { //pow
                ptr += 2;
                return Expressions.pow;
            }
            if (text[i-1] != 'v' && text[i] == 'c' && text[i + 1] == 'o' && text[i + 2] == 's') { //cos
                ptr += 2;
                return Expressions.cos;
            }
            if (text[i] == 't' && text[i + 1] == 'g') { //tg
                ptr += 1;
                return Expressions.tg;
            }
            if (text[i] == 'c' && text[i + 1] == 't' && text[i + 2] == 'g') { //ctg
                ptr += 2;
                return Expressions.ctg;
            }
            if (text[i] == 's' && text[i + 1] == 'q' && text[i + 2] == 'r' && text[i + 3] == 't') { //sqrt
                ptr += 3;
                return Expressions.sqrt;
            }
            if (text[i] == 'l' && text[i + 1] == 'n') { //ln
                ptr += 1;
                return Expressions.ln;
            }
            if (text[i] == 'e' && text[i + 1] == '^') { //exponent
                ptr += 1;
                return Expressions.exponent;
            }
            if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c' && text[i + 3] == 's' && text[i + 4] == 'i' && text[i + 5] == 'n') {//arcsin
                ptr += 5;
                return Expressions.arcsin;
            }
            if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c' && text[i + 3] == 'c' && text[i + 4] == 'o' && text[i + 5] == 's') {//arccos
                ptr += 5;
                return Expressions.arccos;
            }
            if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c' && text[i + 3] == 't' && text[i + 4] == 'g') {//arctg
                ptr += 4;
                return Expressions.arctg;
            }
            if (text[i] == 'a' && text[i + 1] == 'r' && text[i + 2] == 'c' && text[i + 3] == 'c' && text[i + 4] == 't' && text[i + 5] == 'g') {//arcctg
                ptr += 5;
                return Expressions.arcctg;
            }
            if (text[i] == 'l' && text[i + 1] == 'o' && text[i + 2] == 'g') { //log
                ptr += 2;
                return Expressions.log;
            }
            if (text[i] == '+') {
                return Expressions.plus;
            }
            if (text[i] == 'p' && text[i + 1] == 'l' && text[i + 2] == 'u'&& text[i + 3] == 's') { //plus
                ptr += 3;
                return Expressions.plus;
            }
            if (text[i] == '-') {
                return Expressions.minus;
            }
            if (text[i] == 'm' && text[i + 1] == 'i' && text[i + 2] == 'n'&& text[i + 3] == 'u'&& text[i + 4] == 's') { //minus
                ptr += 4;
                return Expressions.minus;
            }
            if (text[i] == '(') {
                return Expressions.openBracket;
            }
            if (text[i] == ')') {
                return Expressions.closeBracket;
            }
            if (Character.isDigit(text[i])) { //x only
                return Expressions.digit;
            }
            if (text[i] == 'a' && text[i + 1] != 's') {
                return Expressions.a; //a
            }
            if (text[i] == 'N' && text[i + 1] == 'o' && text[i + 2] == 'd' && text[i + 3] == 'e' && text[i + 4] == 'I' && text[i + 5] == 'd') {//nodeId
                ptr += 5;
                return Expressions.NodeId;
            }
            if (text[i] == 'C' && text[i + 1] == 'a' && text[i + 2] == 's' && text[i + 3] == 'e') {//Case
                ptr += 3;
                return Expressions.Case;
            }
            if (text[i] == 'b' && text[i + 1] == 'o' && text[i + 2] == 'd' && text[i + 3] == 'y') {//body
                ptr = i;
                ptr += 3;
                return Expressions.body;
            }
            if (text[i] == 'a' && text[i + 1] == 's' && text[i + 2] == 's' && text[i + 3] == 'i' && text[i + 4] == 'g' && text[i + 5] == 'n') {//assign
                ptr += 5;
                return Expressions.assign;
            }
            if (text[i] == 'R' && text[i + 1] == 'e' && text[i + 2] == 't' && text[i + 3] == 'u' && text[i + 4] == 'r' && text[i + 5] == 'n') {//Return
                ptr += 5;
                return Expressions.Return;
            }
            if (text[i] == 'P' && text[i + 1] == 'u' && text[i + 2] == 'b' && text[i + 3] == 'l' && text[i + 4] == 'i' && text[i + 5] == 'c') {//Public
                ptr += 5;
                return Expressions.Public;
            }
            if (text[i] == 'r' && text[i + 1] == 'e' && text[i + 2] == 's' && text[i + 3] == 'u' && text[i + 4] == 'l' && text[i + 5] == 't' && text[i + 6] == 'N' && text[i + 7] == 'o' && text[i + 8] == 'd' && text[i + 9] == 'e') {//ResultNode
                ptr += 9;
                return Expressions.resultNode;
            }
            if (text[i] == 'n' && text[i + 1] == 'o' && text[i + 2] == 'd' && text[i + 3] == 'e') {//node
                ptr += 3;
                return Expressions.node;
            }
            if (text[i - 1] != 't' && text[i] == 'N' && text[i + 1] == 'o' && text[i + 2] == 'd' && text[i + 3] == 'e') {//node
                ptr += 3;
                return Expressions.Node;
            }
            if (text[i] == 'd' && text[i + 1] == 'e' && text[i + 2] == 'r' && text[i + 3] == 'i' && text[i + 4] == 'v') {//deriv
                if (text[i + 5] == 'P' && text[i + 6] == 'o' && text[i + 7] == 'w') {//Pow
                    ptr += 7;
                    return Expressions.derivPow;
                }
                if (text[i + 5] == 'S' && text[i + 6] == 'i' && text[i + 7] == 'n') {//Sin
                    ptr += 7;
                    return Expressions.derivSin;
                }
                if (text[i + 5] == 'C' && text[i + 6] == 'o' && text[i + 7] == 's') {//Cos
                    ptr += 7;
                    return Expressions.derivCos;
                }
                if (text[i + 5] == 'T' && text[i + 6] == 'g') {//Tg
                    ptr += 6;
                    return Expressions.derivTg;
                }
                if (text[i + 5] == 'C' && text[i + 6] == 't' && text[i + 7] == 'g') {//Ctg
                    ptr += 7;
                    return Expressions.derivCtg;
                }
                if (text[i + 5] == 'A' && text[i + 6] == 'r' && text[i + 7] == 'c' && text[i + 8] == 'S' && text[i + 9] == 'i' && text[i + 10] == 'n') {//ArcSin
                    ptr += 10;
                    return Expressions.derivArcsin;
                }
                if (text[i + 5] == 'A' && text[i + 6] == 'r' && text[i + 7] == 'c' && text[i + 8] == 'C' && text[i + 9] == 'o' && text[i + 10] == 's') {//ArcCos
                    ptr += 10;
                    return Expressions.derivArccos;
                }
                if (text[i + 5] == 'A' && text[i + 6] == 'r' && text[i + 7] == 'c' && text[i + 8] == 'T' && text[i + 9] == 'g') {//ArcTg
                    ptr += 9;
                    return Expressions.derivArctg;
                }
                if (text[i + 5] == 'A' && text[i + 6] == 'r' && text[i + 7] == 'c' && text[i + 8] == 'C' && text[i + 9] == 't' && text[i + 10] == 'g') {//ArcCtg
                    ptr += 10;
                    return Expressions.derivArcctg;
                }
                if (text[i + 5] == 'S' && text[i + 6] == 'q' && text[i + 7] == 'r' && text[i + 8] == 't') {//Sqrt
                    ptr += 8;
                    return Expressions.derivSqrt;
                }
                if (text[i + 5] == 'L' && text[i + 6] == 'n') {//Ln
                    ptr += 6;
                    return Expressions.derivLn;
                }
                if (text[i + 5] == 'L' && text[i + 6] == 'o' && text[i + 7] == 'g') {//Log
                    ptr += 7;
                    return Expressions.derivLog;
                }
                if (text[i + 5] == 'M' && text[i + 6] == 'i' && text[i + 7] == 'n' && text[i + 8] == 'u' && text[i + 9] == 's') {//Minus
                    ptr += 9;
                    return Expressions.derivMinus;
                }
                if (text[i + 5] == 'P' && text[i + 6] == 'l' && text[i + 7] == 'u' && text[i + 8] == 's') {//Plus
                    ptr += 8;
                    return Expressions.derivPlus;
                }
            }
        }
        catch (Exception ArrayIndexOutOfBoundsException) {
            return null;
        }
        return null;
    
    }
    public void appendFunc(Expressions expr) {
        Node node = findLastNotFullNode(previousNode);
        Node funcNode = new Node(0,expr);
        if(node.id == Expressions.assign) {
            node.right = funcNode;
        }
        if(node.left.id == Expressions.assign) {
            node.left.right = funcNode;
        }
    }
    public boolean checkIsCase(Expressions expr) {
        Node node = null;
        if(previousNode != null) {
            node = findLastNotFullNode(previousNode);
        }
        else {
            return false;
        }
        if(node.id == Expressions.body || node.id == Expressions.assign ) { //TODO: УБРАТЬ КОСТЫЛЬ
        return true;
        }
        if(node.id == Expressions.Case) {
            Node caseCondNode = new Node(0,expr);
            node.left = caseCondNode;
            return true;
        }
        else return false;
    }
    public Node findLastNotFullNode(Node node) {
        if(node == null) {
            return null;
        }
        if(node.right == null || node.left == null) {
            return node;
        }
        else{
            Node node1 = findLastNotFullNode(node.right);
            Node node2 = findLastNotFullNode(node.left);
            if(node1 != null) {
                return node1;
            }
            else{
                if(node2 != null) {
                    return node2;
                }
                else {
                    return null;
                }
            }
        }
        //return null;
    }
}
