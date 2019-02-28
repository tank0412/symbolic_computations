public class Transform {
    private int charptr = 0;
    public char[] derivate(char[] symb) {
        char[] derivresult = new char[100];
        for(; charptr < symb.length; ++charptr) {
            if(symb[charptr] == 0 ) break;
            if(symb[charptr] == 's' && symb[charptr + 1] == 'i' && symb[charptr + 2] == 'n') {
                derivresult =  sin(symb,charptr+3);
            }
            if(symb[charptr] == 'x' ) {
                for(int j = 0; j < derivresult.length; ++ j) {
                    if(derivresult[j] == 0) {
                        derivresult[j] = 'x';
                        break;
                    }
                }
            }

            if(symb[charptr] == '^' && Character.isDigit(symb[charptr + 1]) ) {
                derivresult =  pow(symb, charptr);
            }
        }
        return derivresult;
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
        charptr = 0;
        derivArgument = derivate(argument);
        charptr = z;
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
                    if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n') {
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
                    if (symb[z] != '(' || symb[z - 1] == 's' || symb[z - 1] == 'n') {
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
            derivArgument = derivate(argument);
            charptr = backupcharptr;
            int c;
            for (z = 0; ; ++z) {
                if (result[z] == 0) {
                    result[z] = '(';
                    break;
                }
            }
            index = 0;
            for (c = z + 4; c < derivArgument.length; ++c, ++index) {
                if (derivArgument[index] == 0) break;
                result[c] = derivArgument[index];
            }
            result[c] = ')';
            result[c+1] = '^';
            int digit = Character.getNumericValue(symb[i + 1]) - 1;
            result[c+2] = (char)( digit + '0');
            return result;
        }

}
