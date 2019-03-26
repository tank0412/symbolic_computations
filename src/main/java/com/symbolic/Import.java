package com.symbolic;

import java.util.ArrayList;
import java.util.Arrays;

public class Import {
    private int ptr = 0;
    private boolean isPowAgain = false;
    private Node sinNode;
    private Node previousNode;
    private int ptr2 = 0;
    public static ArrayList<Node> rules;
    public Node converttoSymbolic(char[] text) {
        Node node = convertAsciMathToSymbolic(text);
        return node;

    }
    private Node convertAsciMathToSymbolic(char[] text) {
        Node powNode;
        Expressions expr = null;
        Node left = null;
        for(int i = ptr; i < text.length; ++i) {
            if(i < ptr){
                i=ptr;
            }
            if(i > text.length -1 || text[i] == 0 ) {
                break;
            }
            expr = checkInput(text,i);
            if(expr!= null) {
                if (expr == Expressions.context) {
                    char[] expr2 = new char[100];
                    expr2 = Arrays.copyOfRange(text, ptr+2, text.length);
                    Import contextImport = new Import();
                    Node context = contextImport.convertAsciMathToSymbolic(expr2);
                    ptr+=expr2.length +1;
                    if (context != null) {
                        Parse.context = context;
                    }
                    Node node = new Node(expr);
                    node.arguments.add(context);
                    return node;
                }
                if(expr==Expressions.digit) {
                    if (Character.isDigit(text[i])) {
                        Digit digit = new Digit((int) (text[i])-'0');
                        previousNode = digit;
                    }
                }
                if (expr == Expressions.sin || expr == Expressions.cos || expr == Expressions.ctg) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i += 4;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.tg) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i += 3;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.arcsin || expr == Expressions.arccos || expr == Expressions.arcctg) {
                    sinNode = new Node(expr);
                    previousNode = sinNode;
                    i += 7;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.arctg) {
                    sinNode = new Node(expr);
                    i += 6;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.sqrt) {
                    sinNode = new Node(expr);
                    i += 5;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.ln) {
                    sinNode = new Node(expr);
                    i += 3;
                    checkX(text, i);
                    ++i;
                }
                if (expr == Expressions.log) {
                    Node logNode = new Node(expr);
                    i += 4;
                    Node aNode = null;
                    if (checkInput(text, i) == Expressions.a) {
                        aNode = new Node(Expressions.a, logNode);
                    }
                    i += 2;
                    sinNode = logNode;
                    checkX(text, i);
                    ++i;
                    if(aNode != null) {
                        logNode.arguments.add(aNode);
                    }
                    previousNode = logNode;
                }
                if (expr == Expressions.exponent) {
                    Node exprNode = new Node(Expressions.pow);
                    i += 3;
                    exprNode.arguments.add(new Node(Expressions.exponent, exprNode));
                    if (checkInput(text, i) == Expressions.x) {
                        exprNode.arguments.add(new Node(Expressions.x, exprNode));
                        i++;
                        if (checkInput(text, i) == Expressions.closeBracket) {
                            ;
                        }

                    } else {
                        hardArgument(text, i, exprNode);
                    }
                    previousNode = exprNode;
                }
                if (expr == Expressions.plus) {
                    doJobForMathSymbol(expr, i, text);
                    i = ptr;
                    break;
                }
                if (expr == Expressions.minus) {
                    doJobForMathSymbol(expr, i, text);
                    i = ptr;
                    break;

                }
                if (expr == Expressions.div) {
                    doJobForMathSymbol(expr, i, text);
                    i = ptr;
                    break;

                }
                if (expr == Expressions.mul) {
                    doJobForMathSymbol(expr, i, text);
                    i = ptr;
                    break;

                }
                if (expr == Expressions.x) {
                    return new Node(Expressions.x);

                }
                if (expr == Expressions.pow) {
                    int BracketCountOpen = 0, BracketCountClose = 0;
                    int q = 0;
                    for (q = i; q < text.length; --q) {
                        if ((BracketCountOpen == BracketCountClose && (BracketCountOpen != 0)) || q < 0 || text[q] == 0) {
                            break;
                        }
                        if (checkInput(text, q) == Expressions.openBracket) {
                            BracketCountOpen++;
                        }
                        if (checkInput(text, q) == Expressions.closeBracket) {
                            BracketCountClose++;
                        }
                    }
                    if (isPowAgain) {
                        i = i + 1;
                        continue;
                    }
                    if (((i - 1) - (q + 1)) == 2) {  // значит это (x)
                        powNode = new Node(Expressions.pow);
                        ptr+=2;
                        powNode.arguments.add(new Node(Expressions.x, powNode));
                        if ((text[i] == '^' && text[i + 1] != '(' && text[i+1] !='C' )|| (text[i + 1] != '(' && text[i + 2] != 'd') && (text[i+1] !='C')) {
                            left = new Digit((int) (text[i + 1] - '0'), powNode);
                            ptr+=2;
                        } else {
                            if(checkInput(text, i+1) == Expressions.Compute) {
                                int v = ptr;
                                v-=7;
                                char[] subCompute = new char[100];
                                int index = 0;
                                while(v < text.length && text[v] != 0)  {
                                    subCompute[index] = text[v];
                                    v++;
                                    index++;
                                }
                                Import myImport = new Import();
                                Node arg = myImport.convertAsciMathToSymbolic(subCompute);
                                ptr+=v -10;
                                left = arg;
                                //System.out.println("YES");
                            }
                            else {
                                if(checkInput(text, i+1) == Expressions.digitParse || checkInput(text, i+2) == Expressions.digitParse ) {
                                    left = new Node(Expressions.digitParse, powNode);
                                }
                                else {
                                    left = new Node(Expressions.digit, powNode);
                                }
                            }
                        }
                        powNode.arguments.add(left);
                        previousNode = powNode;
                    } else {
                        powNode = new Node(Expressions.pow);
                        ptr = q + 1;
                        isPowAgain = true;
                        char subFunc[] = new char[100];
                        int index = 0;
                        for (int c = q + 2; c < (i - 1); ++c, ++index) {
                            subFunc[index] = text[c];
                        }
                        Import importPow = new Import();
                        powNode.arguments.add(importPow.convertAsciMathToSymbolic(subFunc));
                        ptr+=index+1;
                        isPowAgain = false;
                        left = new Digit((int) (text[i + 1] - '0'), powNode);
                        ptr+=2;
                        powNode.arguments.add(left);
                        previousNode = powNode;
                        ptr++;
                    }
                }
                if (expr == Expressions.Assume) {
                    char[] firstArg = new char[100];
                    char[] secondArg = new char[100];
                    char[] thirdArg = new char[100];
                    int index = 0;
                    int z = i + 2;
                    do {
                        if (text[z] != ' ') {
                            firstArg[index] = text[z];
                            index++;
                        }
                        z++;
                    }
                    while (checkInput(text, z) != Expressions.separator);
                    index = 0;
                    z++;
                    if(text[z+1] != 'x' || text[z+3] == '^') {
                        do {
                            if (text[z] != ' ') {
                                secondArg[index] = text[z];
                                index++;
                            }
                            z++;
                        }
                        while (checkInput(text, z) != Expressions.openBracket && checkInput(text, z) != Expressions.digitParse);
                    }
                    else {
                        secondArg[index] = 'x';
                        index++;
                        z+=2;
                    }
                    secondArg[index] = '(';
                    z++;
                    do {
                        z++;
                    }
                    while (checkInput(text, z) != Expressions.arrrow && checkInput(text, z) != Expressions.digitParse);
                    z += 2;
                    //z+= firstArg.length + 1;
                    index = 0;
                    do {
                        if (text[z] != ' ') {
                            thirdArg[index] = text[z];
                            index++;
                        }
                        z++;
                    }
                    while (z < text.length && text[z] != 0 && checkInput(text, z) != Expressions.Assume);
                    secondArg = addArgument(secondArg, firstArg);
                    Import importMy = new Import();
                    Node first = importMy.converttoSymbolic(secondArg);
                    Import importMy2 = new Import();
                    Node second = importMy2.converttoSymbolic(thirdArg);
                    Node rule = new Node(Expressions.Rule);
                    first.parent = rule;
                    second.parent = rule;
                    rule.arguments.add(first);
                    rule.arguments.add(second);
                    rules.add(rule);
                    z--;
                    ptr = z;
                    continue;

                }
                if (expr == Expressions.Compute) {
                    Node compute = new Node(Expressions.Compute);
                    char[] computeArg = new char[100];
                    int c = ptr;
                    int index = 0;
                    c++;
                    do {
                        if(text[c] == '(' || text[c] == ')') {
                            c++;
                            continue;
                        }
                        if (text[c] != ' ' ) {
                            computeArg[index] = text[c];
                            index++;
                            c++;
                        }
                    }
                    while (c < text.length && text[c] != 0);
                    Import myImport = new Import();
                    Node arg = myImport.convertAsciMathToSymbolic(computeArg);
                    compute.arguments.add(arg);
                    ptr+=c-1;
                    previousNode=compute;
                }
                if(expr == Expressions.plot) {
                    Node plotNode = new Node(Expressions.plot);
                    char[] x = new char[4];
                    char[] y = new char[4];
                    char[] z = new char[4];
                    int index = 0;
                    i+=5;
                    if(checkInput(text, i) == Expressions.List) {
                        while (checkInput(text, i) == Expressions.List) {
                            Node listNode = new Node(Expressions.List);
                            i += 2;
                            while (checkInput(text, i) == Expressions.Pack) {
                                Node packNode = new Node(Expressions.Pack);
                                ptr2 = 0;
                                i += 2;
                                x = parseDigit(text, i);
                                i++;
                                i += ptr2;
                                y = parseDigit(text, i);
                                i += ptr2;
                                z = parseDigit(text, i);
                                i += ptr2;
                                int temp = 0;
                                temp = charDigitToInt(x);
                                packNode.arguments.add(new Digit(temp));
                                temp = charDigitToInt(y);
                                packNode.arguments.add(new Digit(temp));
                                temp = charDigitToInt(z);
                                packNode.arguments.add(new Digit(temp));
                                packNode.parent = listNode;
                                listNode.arguments.add(packNode);
                            }
                            listNode.parent = plotNode;
                            plotNode.arguments.add(listNode);
                        }
                    }
                    else {
                        Import importPlot = new Import();
                        char[] context = Arrays.copyOfRange(text, i, text.length);
                        Node plotArg = importPlot.convertAsciMathToSymbolic(context);
                        ptr+=importPlot.ptr;
                        plotArg.parent=plotNode;
                        plotNode.arguments.add(plotArg);
                    }
                    previousNode = plotNode;
                    }
                if(expr == Expressions.Range) {
                    Parse.range = new double[2];
                    Node rangeNode = new Node(Expressions.Range);
                    i+=6;
                    double to, from = 0;
                    char[] toSymb, fromSymb;
                    fromSymb = parseDigit(text, i);
                    i+=2;
                    toSymb = parseDigit(text, i);
                    i+=2;
                    from = charDigitToInt(fromSymb);
                    to = charDigitToInt(toSymb);
                    Parse.range[0] = from;
                    Parse.range[1] = to;
                    previousNode = rangeNode;
                }
                }
        }
             return previousNode;
    }
    private void hardArgument(char[] text,int i, Node node) {
        int startBracket, endBracket;
        char subFunc[] = new char[100];
        startBracket = 1; endBracket = 0;
        for(int c = i, index = 0; ; ++c,++index){
            if(checkInput(text,c) == Expressions.closeBracket){
                endBracket++;
            }
            if(checkInput(text,c) == Expressions.openBracket){
                startBracket++;
            }

            if(checkInput(text,c) != Expressions.closeBracket || (startBracket!=endBracket)) {
                subFunc[index] = text[c];
            }

            if(c >= text.length||text[c] == 0 || (startBracket==endBracket && startBracket != 0)) {
                //subFunc[index] = text[c];
                //index++;
                ptr = c;
                break;
            }
        }
        Import import2 = new Import();
        Node argumentNode = import2.convertAsciMathToSymbolic(subFunc);
        argumentNode.parent = node;
        node.arguments.add(argumentNode);
        previousNode = node;
    }
    private Expressions checkInput(char[] text, int i) {
        if(i >= text.length || text[i] == 0) {
            return null;
        }
        if(text[i] == 's' && text[i + 1] == 'i' && text[i + 2] == 'n') { //sin
            ptr+=2;
            return Expressions.sin;
        }

        if(i +2 < text.length) {
            if (text[i] == 'x' && text[i + 1] != '*' && text[i + 1] != 't' && text[i + 2] != '^' ) { //x only
                return Expressions.x;
            }
        }
        else {
            if (text[i] == 'x') { //x only
                return Expressions.x;
            }
        }

        if(text[i] == '^') {//pow
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
        if (text[i] == 'p' && text[i + 1] == 'l' && text[i + 2] == 'o'&& text[i + 3] == 't') {//plot
            ptr+=3;
            return Expressions.plot;
        }
        if (text[i] == 'l' && text[i + 1] == '(') {//list
            ptr+=1;
            return Expressions.List;
        }
        if (text[i] == 'p' && text[i + 1] == '(') {//plot
            ptr+=1;
            return Expressions.Pack;
        }
        if(text[i] == 'd' && text[i + 1] == '(') { //d(
            ptr+=1;
            return Expressions.Assume;
        }
        if (text[i] == 'r' && text[i + 1] == 'a' && text[i + 2] == 'n'&& text[i + 3] == 'g'&& text[i + 4] == 'e') {//plot
            ptr+=4;
            return Expressions.Range;
        }
        if(i+6 < text.length) {
            if (text[i] == 'c' && text[i + 1] == 'o' && text[i + 2] == 'n' && text[i + 3] == 't' && text[i + 4] == 'e' && text[i + 5] == 'x' && text[i + 6] == 't') {//context
                ptr += 6;
                return Expressions.context;
            }
            if (text[i] == 'C' && text[i + 1] == 'o' && text[i + 2] == 'm' && text[i + 3] == 'p' && text[i + 4] == 'u' && text[i + 5] == 't' && text[i + 6] == 'e') {//compute
                ptr += 6;
                return Expressions.Compute;
            }
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
        if(i+9 < text.length) {
            if (text[i] == 'd' && text[i + 1] == 'i' && text[i + 2] == 'g' && text[i + 3] == 'i' && text[i + 4] == 't' && text[i + 5] == 'P' && text[i + 6] == 'a' && text[i + 7] == 'r' && text[i + 8] == 's' && text[i + 9] == 'e') {//digitParse
                ptr += 9;
                return Expressions.digitParse;
            }
        }
        if (text[i] == 'd' && text[i + 1] == 'i' && text[i + 2] == 'g'&& text[i + 3] == 'i'&& text[i + 4] == 't') {//digit
            ptr+=4;
            return Expressions.digitSymbol;
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
            ptr+=3;

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
        i += 1;
        Node node2 = null;
        if (previousNode != null) {
            node2 = previousNode;
        } else {
            if (checkInput(text, i - 1) == Expressions.div || checkInput(text, i - 1) == Expressions.mul || checkInput(text, i - 1) == Expressions.plus || checkInput(text, i - 1) == Expressions.minus) {
                if (i - 2 >= 0) {
                    if (Character.isDigit(text[i - 2]) == true) {
                        Digit digit = new Digit(text[i - 2] - '0'); // for div
                        node.arguments.add(digit);
                    } else {
                        char[] array = new char[1];
                        array[0] = text[i - 2];
                        node2 = null;
                        if (text[i - 2] != 'e') {
                            node2 = new Node(checkInput(array, 0));
                        } else {
                            node2 = new Node(Expressions.digitParse);
                        }
                    }
                }
            }
        }
        if(node2 != null) {
            node.arguments.add(node2);
        }
        ptr = i;
        char[] argument = Arrays.copyOfRange(text,i, text.length);
        Import importArg = new Import();
        node.arguments.add(importArg.convertAsciMathToSymbolic(argument));
        ptr+=importArg.ptr;
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
    public int charDigitToInt(char[] text) {
        int decimals = 1000;
        int digit = 0;
        for(int q = text.length-1; q >=0; q--) {
            if(text[q] != 0) {
                digit = Character.getNumericValue(text[q]) * decimals;
            }
            decimals/=10;
        }
        return digit;
    }
    public char[] parseDigit(char[] text, int i) {
        char[] digit = new char[4];
        int index = 0;
        while(checkInput(text, i) != Expressions.separator && checkInput(text, i) != Expressions.closeBracket ) {
            digit[index] = text[i];
            i++;
            index++;
            ptr2++;
        }
        return digit;
    }
}
