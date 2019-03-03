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
                derivresult =  sin(symb,charptr+3);
            }
            if(symb[charptr] == 'x' ) { //x only
                for(int j = 0; j < derivresult.length; ++ j) {
                    if(derivresult[j] == 0) {
                        derivresult[j] = 'x';
                        break;
                    }
                }
            }

            if(symb[charptr] == '^' && Character.isDigit(symb[charptr + 1]) ) {//pow
                derivresult =  pow(symb, charptr);
            }
            if(symb[charptr] == 'c' && symb[charptr + 1] == 'o' && symb[charptr + 2] == 's') { //cos
                derivresult =  cos(symb,charptr+3);
            }
            if(symb[charptr] == 't' && symb[charptr + 1] == 'g') { //tg
                derivresult =  tg(symb,charptr+2);
            }
            if(symb[charptr] == 'c' && symb[charptr + 1] == 't'&& symb[charptr + 2] == 'g') { //ctg
                derivresult =  ctg(symb,charptr+3);
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
    public char[] sin(char[] symb, int i) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0;
        //get sin argument;
        for (z = i + 1; ; z++) {
            if (symb[z] != ')') {
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
                result[z] = 'c';
                result[z + 1] = 'o';
                result[z + 2] = 's';
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
        return result;

    }
        public char[] pow(char[] symb, int i){
            char[] result = new char[100];
            char[] argument = new char[100];
            char[] derivArgument = new char[100];
            int index = 0, z = 0, backupcharptr=0;
            for (z = i - 2; ; z--) {
                if(z-1 >= 0) {
                    if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n'|| symb[z - 1] == 'g') {
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
                    if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n'|| symb[z - 1] == 'g') {
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
            for (c = z + 2; c < derivArgument.length; ++c, ++index) {
                if (derivArgument[index] == 0) break;
                result[c] = derivArgument[index];
            }
            result[c] = ')';
            result[c+1] = '^';
            digit -=1;
            result[c+2] = (char)( digit + '0');
            return result;
        }
    public char[] cos(char[] symb, int i) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0;
        //get sin argument;
        for (z = i + 1; ; z++) {
            if (symb[z] != ')') {
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
                result[z] = '-';
                result[z+1] = 's';
                result[z + 2] = 'i';
                result[z + 3] = 'n';
                result[z + 4] = '(';
                break;
            }
        }
        index = 0;
        for (c = z + 5; c < derivArgument.length; ++c, ++index) {
            if (derivArgument[index] == 0) break;
            result[c] = derivArgument[index];
        }
        result[c] = ')';
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
    public char[] tg(char[] symb, int i) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0;
        //get tg argument;
        for (z = i + 1; ; z++) {
            if (symb[z] != ')') {
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
                result[z+1] = '/';
                result[z + 2] = '(';
                result[z + 3] = 'c';
                result[z + 4] = 'o';
                result[z + 5] = 's';
                result[z + 6] = '^';
                result[z + 7] = '2';
                result[z + 8] = '(';
                break;
            }
        }
        index = 0;
        for (c = z + 9; c < derivArgument.length; ++c, ++index) {
            if (derivArgument[index] == 0) break;
            result[c] = derivArgument[index];
        }
        result[c] = ')';
        result[c+1] = ')';
        return result;

    }
    public char[] ctg(char[] symb, int i) {
        char[] result = new char[100];
        char[] argument = new char[100];
        char[] derivArgument = new char[100];
        int index = 0, z = 0;
        //get tg argument;
        for (z = i + 1; ; z++) {
            if (symb[z] != ')') {
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
                result[z] = '-';
                result[z+1] = '1';
                result[z+2] = '/';
                result[z + 3] = '(';
                result[z + 4] = 's';
                result[z + 5] = 'i';
                result[z + 6] = 'n';
                result[z + 7] = '^';
                result[z + 8] = '2';
                result[z + 9] = '(';
                break;
            }
        }
        index = 0;
        for (c = z + 10; c < derivArgument.length; ++c, ++index) {
            if (derivArgument[index] == 0) break;
            result[c] = derivArgument[index];
        }
        result[c] = ')';
        result[c+1] = ')';
        return result;

    }

public char[] CheckCombine(char[] Combined) {
    for(int m = 0; m < Combined.length; ++m) {
        if(Combined[m] == '+' && Combined[m+1] == '(' && Combined[m+2] == '-'  ){
            Combined[m] = '-';
            for(int n = m+2; n <Combined.length; ++n){
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
        if(Combined[m] == '-' && Combined[m+1] == '(' && Combined[m+2] == '-'  ){
            Combined[m] = '+';
            for(int n = m+2; n <Combined.length; ++n){
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
    }
    return Combined;
}
}
