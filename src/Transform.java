import java.util.Arrays;

public class Transform {
    private int charptr = 0;
    private boolean isSymbol = false;
    private boolean hardDerivative = false;
    private char[] saveresult = new char[100];
    public char[] derivate(char[] symb) {
        char[] derivresult = new char[100];
        for(; charptr < symb.length; ++charptr) {
            if(symb[charptr] == 0 ) break;
            if(symb[charptr] == 's' && symb[charptr + 1] == 'i' && symb[charptr + 2] == 'n') { //sin
                //derivresult =  sin(symb,charptr+3);
                char[] first = {'c','o','s','('};
                char[] second = {')'};
                derivresult= genericDerivate(symb, charptr+3, first,second);
            }
            if(symb[charptr] == 'x' ) { //x only
                for(int j = 0; j < derivresult.length; ++ j) {
                    if(derivresult[j] == 0) {
                        if(hardDerivative == true){
                            derivresult[j] = 'x';
                        }
                        else {
                            derivresult[j] = '1';
                        }
                        break;
                    }
                }
            }

            if(symb[charptr] == '^' && Character.isDigit(symb[charptr + 1]) ) {//pow
                derivresult =  pow(symb, charptr);
            }
            if(symb[charptr] == 'c' && symb[charptr + 1] == 'o' && symb[charptr + 2] == 's') { //cos
                char[] first = {'-','s','i','n','('};
                char[] second = {')'};
                derivresult= genericDerivate(symb, charptr+3, first,second);
            }
            if(symb[charptr] == 't' && symb[charptr + 1] == 'g') { //tg
                char[] first = {'1','/','(','c','o','s','^','2','('};
                char[] second = {')',')'};
                derivresult= genericDerivate(symb, charptr+2, first,second);
            }
            if(symb[charptr] == 'c' && symb[charptr + 1] == 't'&& symb[charptr + 2] == 'g') { //ctg
                char[] first = {'-','1','/','(','s','i','n','^','2','('};
                char[] second = {')',')'};
                derivresult= genericDerivate(symb, charptr+3, first,second);
            }
            if(symb[charptr] == 's' && symb[charptr + 1] == 'q'&& symb[charptr + 2] == 'r'&& symb[charptr + 3] == 't') { //sqrt
                char[] first = {'1','/','(','2','s','q','r','t','('};
                char[] second = {')',')'};
                derivresult= genericDerivate(symb, charptr+4, first,second);
            }
            if(symb[charptr] == 'l' && symb[charptr + 1] == 'n') { //ln
                char[] first = {'1','/','('};
                char[] second = {')',};
                derivresult= genericDerivate(symb, charptr+2, first,second);
            }
            if(symb[charptr] == 'e' && symb[charptr + 1] == '^') { //epsilon
                char[] first = {'e','^','('};
                char[] second = {')',};
                derivresult= genericDerivate(symb, charptr+2, first,second);
            }
            if (symb[charptr] == 'a' && symb[charptr + 1] == 'r' && symb[charptr + 2] == 'c'&& symb[charptr + 3] == 's'&& symb[charptr + 4] == 'i'&& symb[charptr + 5] == 'n') {//arcsin
                char[] first = {'1','/','(','s','q','r','t','(','1','-','('};
                char[] second = {')','^','2',')',')'};
                derivresult= genericDerivate(symb, charptr+6, first,second);
            }
            if (symb[charptr] == 'a' && symb[charptr + 1] == 'r' && symb[charptr + 2] == 'c'&& symb[charptr + 3] == 'c'&& symb[charptr + 4] == 'o'&& symb[charptr + 5] == 's') {//arccos
                char[] first = {'-','1','/','(','s','q','r','t','(','1','-','('};
                char[] second = {')','^','2',')',')'};
                derivresult= genericDerivate(symb, charptr+6, first,second);
            }
            if (symb[charptr] == 'a' && symb[charptr + 1] == 'r' && symb[charptr + 2] == 'c'&& symb[charptr + 3] == 't'&& symb[charptr + 4] == 'g') {//arctg
                char[] first = {'1','/','(','1','+','('};
                char[] second = {')','^','2',')'};
                derivresult= genericDerivate(symb, charptr+5, first,second);
            }
            if (symb[charptr] == 'a' && symb[charptr + 1] == 'r' && symb[charptr + 2] == 'c'&& symb[charptr + 3] == 'c'&& symb[charptr + 4] == 't'&& symb[charptr + 5] == 'g') {//arcctg
                char[] first = {'-','1','/','(','1','+','('};
                char[] second = {')','^','2',')'};
                derivresult= genericDerivate(symb, charptr+6, first,second);
            }
            if(symb[charptr] == 'l' && symb[charptr + 1] == 'o' && symb[charptr + 2] == 'g') { //log
                derivresult =  log(symb,charptr+3);
            }

            if(symb[charptr] == '+') {
                for(int j = 0; j < derivresult.length; ++ j) {
                    if(derivresult[j] == 0) {
                        derivresult[j] = '+';
                        saveresult = Combine(saveresult, derivresult,true);
                        isSymbol = true;
                        break;
                    }
                }
            }
            if(symb[charptr] == '-') {
                for(int j = 0; j < derivresult.length; ++ j) {
                    if(derivresult[j] == 0) {
                        derivresult[j] = '-';
                        saveresult = Combine(saveresult, derivresult,true);
                        isSymbol = true;
                        break;
                    }
                }
            }
        }
        if(isSymbol == true && saveresult != derivresult && hardDerivative != true ) {
            isSymbol= false;
            return Combine(saveresult, derivresult,true);
        }
        else {
            return derivresult;
        }
    }
    public char[] genericDerivate(char[] symb, int i, char[] firstpart, char[] secondPart) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0, firstPartIndex, secondPartIndex;
        //get argument;
        for (z = i + 1; ; z++) {
            if (symb[z] != ')') {
                argument[index] = symb[z];
                index++;
            } else {
                argument[index] = symb[z];
                index++;
                break;
            }

        }
        hardDerivative = true;
        charptr = 0;
        derivArgument = derivate(argument);
        charptr = z;
        hardDerivative = false;
        int c;
        for (z = 0; ; ++z) {
            if (result[z] == 0) {
                for (firstPartIndex = 0; firstPartIndex<firstpart.length; firstPartIndex++, ++z) {
                    result[z] = firstpart[firstPartIndex];
                }
                break;
            }
        }
        index = 0;
        for (c = z; c < argument.length; ++c, ++index) {
            if (argument[index] == 0) break;
            if (argument[0] == 'x'&&argument[1] == ')') {
                result[c] = argument[index];
                c++;
                break;
            }
            result[c] = argument[index];
        }
        for (z = 0; ; ++z) {
            if (result[z] == 0) {
                for (secondPartIndex = 0; secondPartIndex < secondPart.length; secondPartIndex++, ++z) {
                    result[z] = secondPart[secondPartIndex];
                }
                break;
            }
        }
        index = 0;
        if((derivArgument[index] != '1' && derivArgument[index] != 'x')|| derivArgument[index+1] != 0) {
            c++;
            result[c] = '*';
            c++;
            for (; c < derivArgument.length; ++c, ++index) {
                if (derivArgument[index] == 0) break;
                result[c] = derivArgument[index];
            }
        }
        return result;

    }

    public char[] pow(char[] symb, int i){
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0, backupcharptr=0;
        for (z = i - 2; ; z--) {
            if(z-1 >= 0) {
                if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n'|| symb[z - 1] == 'g'||(symb[z - 2] == 'r'&& symb[z - 1] == 't'||(symb[z - 2] == 'e'&& symb[z - 1] == '^')||(symb[z - 5] == 'l'&& symb[z - 4] == 'o'&& symb[z - 3] == 'g'&& symb[z - 2] == '_'))) {
                    index++;
                } else {
                    break;
                }

            }
            else {
                break;
            }
        }
        index = index - 1;
        for (z = i - 2; ; z--) {
            if(z-1 >= 0) {
                if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n'|| symb[z - 1] == 'g'||(symb[z - 2] == 'r'&& symb[z - 1] == 't'||(symb[z - 2] == 'e'&& symb[z - 1] == '^')||(symb[z - 5] == 'l'&& symb[z - 4] == 'o'&& symb[z - 3] == 'g'&& symb[z - 2] == '_'))) {//e^(x)
                    argument[index] = symb[z];
                    index--;
                } else {
                    break;
                }

            }
            else {
                break;
            }
        }
        backupcharptr = charptr;
        charptr = 0;
        hardDerivative = true;
        Transform transform = new Transform();
        derivArgument = transform.derivate(argument);
        charptr = backupcharptr;
        hardDerivative = false;
        int digit = Character.getNumericValue(symb[i + 1]);
        int c;
        for (z = 0; ; ++z) {
            if (result[z] == 0) {
                result[z] = (char)( digit + '0');
                result[z+1] = '(';
                break;
            }
        }
        index = 0;
        for (c = z + 2; c < argument.length; ++c, ++index) {
            if (argument[index] == 0) break;
            if(argument[index] == '_') {
                c--;
                continue;
            }
            result[c] = argument[index];
        }
        result[c] = ')';
        digit -= 1;
        if(digit != 1) {
            result[c + 1] = '^';
            result[c + 2] = (char) (digit + '0');
            c += 3;
        }
        else{
            c +=1;
        }
        index = 0;
        if(derivArgument[index] != '1'|| derivArgument[index+1] != 0) {
            result[c] = '*';
            c++;
            for (; c < derivArgument.length; ++c, ++index) {
                if (derivArgument[index] == 0) break;
                result[c] = derivArgument[index];
            }
        }
        int add = 1;
        char[] copyresult = new char[100];
        for(int k = 0; k <=result.length; ++k){
            if(result[k] == '-' && (result[k-4] != 'r' && result[k-3] != 't' )){
                result[k]=' ';
                copyresult=Arrays.copyOf(result, result.length);;
                result[1] = result[0];

                for(int x = 2; x < result.length; ++x){
                    if(result[x] == 0) {
                        result[0]='-';
                        break;
                    }
                    if(copyresult[x - add] == ' ') {
                        add--;
                        result[x] = copyresult[x -add];
                    }
                    else {
                        result[x] = copyresult[x - add];
                    }

                }
            }
            if(result[k] == 0){
                break;
            }
        }
        return result;
    }
    public char[] Combine (char[] first, char[] second, boolean saveminus) {
        char[] combinedresult = new char[100];
        int secondindex = 0;
        for(int i = 0; ; ++ i) {
            if(second[secondindex] == 0) {
                return CheckCombine(first);
            }
            if(first[i] == 0) {
                if(second[secondindex] == '-' && saveminus == false) {
                    secondindex++;
                    i--;
                    continue;
                }
                first[i] = second[secondindex];
                secondindex++;
            }
        }
    }
    public char[] log(char[] symb, int i) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0;
        //get log argument;
        char aArgument = symb[i+1];
        for (z = i + 2; ; z++) {
            if (symb[z] != 0 && symb[z] != ')' ) {
                argument[index] = symb[z];
                index++;
            } else {
                break;
            }

        }
        hardDerivative = true;
        charptr = 0;
        derivArgument = derivate(argument);
        charptr = z;
        hardDerivative = false;
        int c;
        for (z = 0; ; ++z) {
            if (result[z] == 0) {
                result[z] = '1';
                result[z + 1] = '/';
                result[z + 2] = '(';
                result[z + 3] = '(';
                break;
            }
        }
        index = 0;
        for (c = z + 4; c < derivArgument.length; ++c, ++index) {
            if (derivArgument[index] == 0) break;
            result[c] = derivArgument[index];
        }
        result[c] = ')';
        result[c+1] = 'l';
        result[c+2] = 'n';
        result[c+3] = '(';
        result[c+4] = aArgument;
        result[c+5] = ')';
        result[c+6] = ')';
        return result;

    }

    public char[] CheckCombine(char[] Combined) {
        for(int m = 0; m < Combined.length; ++m) {
            if(Combined[m] == '+' && Character.isDigit(Combined[m+1]) && Combined[m+2] == '(' && Combined[m+3] == '-'  ){
                Combined[m] = '-';
                for(int n = m+3; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
            if(Combined[m] == '+' && Combined[m+1] == '-' && Combined[m+2] == '1' && Combined[m+3] == '/'&& (Combined[m+4] == '('&& (Combined[m+5] == 's')  )) {
                Combined[m] = '-';
                for(int n = m+1; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
            if(Combined[m] == '-' && Character.isDigit(Combined[m+1]) && Combined[m+2] == '(' && Combined[m+3] == '-'  ){
                Combined[m] = '+';
                for(int n = m+3; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
            if(Combined[m] == '-' && Combined[m+1] == '-' && Combined[m+2] == '1' && Combined[m+3] == '/'&& (Combined[m+4] == '('&& (Combined[m+5] == 's')  )) {
                Combined[m] = '+';
                for(int n = m+1; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
            if(Combined[m] == '-' && Combined[m+1] == '-'  ){
                Combined[m] = '+';
                for(int n = m+1; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
            if((Combined[m] == '+' && Combined[m+1] == '-' )|| (Combined[m] == '-' && Combined[m+1] == '+' ) ){
                Combined[m] = '-';
                for(int n = m+1; n <Combined.length; ++n){
                    if(Combined[n] == 0) break;
                    Combined[n] = Combined[n+1];
                }
            }
        }
        return Combined;
    }
}
