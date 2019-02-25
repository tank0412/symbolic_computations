import java.util.Scanner;
class Symbolic {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter  an expression");
        char[] Symb = new char[100];
        String expr = reader.nextLine();
        //System.out.println(expr);
        Symb = expr.toCharArray();
        String Result = "";
        Result = derivate(Symb);
        System.out.println(Result);
        return;

    }
    public static String derivate(char[] Symb) {
     char[] Result = new char[100] ;
     String ResultS = "";
     for(int i = 0; i < Symb.length - 1; ++ i) {
         if (Symb[i] == 'x' && Symb[i + 1] == '^' && Character.isDigit(Symb[i + 2])) {
             // for pow
             Result = pow(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 'e' && Symb[i + 1] == '^' && Symb[i + 2] == 'x') {
             //for e^ x
             Result = epsilon(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 'l' && Symb[i + 1] == 'o' && Symb[i + 2] == 'g') {
             //for log
             Result = log(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 'l' && Symb[i + 1] == 'n' && Symb[i + 2] == 'x') {
             //for ln
             Result = ln(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 's' && Symb[i + 1] == 'i' && Symb[i + 2] == 'n') {
             //for sin
             Result = sin(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 'c' && Symb[i + 1] == 'o' && Symb[i + 2] == 's') {
             //for cos
             for(int j=0; j < Result.length; ++j) {
                 if(Result[j] == 0) {
                     if(j-1 >=0) {
                         if (Result[j - 1] == '+') {
                             Result[j - 1] = '-';
                             break;
                         } else {
                             Result[j - 1] = '+';
                             break;
                         }
                     }
                     else {
                         Result[j] = '-';
                         break;
                     }

                 }
                 }
             Result = cos(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 's' && Symb[i + 1] == 'q' && Symb[i + 2] == 'r'&& Symb[i + 3] == 't') {
             //for sqrt
             Result = sqrt(Symb, Result, i);
             continue;
         }
         if (Symb[i] == 't' && Symb[i + 1] == 'g') {
             //for tg
             Result = tg(Symb, Result, i);
             continue;
         }
         // is -
         if (Symb[i] == '-') {
             for(int j=0; j < Result.length; ++j) {
                 if(Result[j] == 0) {
                     Result[j] = '-';
                     break;
                 }
             }
         }
         //is +
         if (Symb[i] == '+') {
             for(int j=0; j < Result.length; ++j) {
                 if(Result[j] == 0) {
                         Result[j] = '+';
                     break;
                 }
             }
         }
     }
        ResultS = String.valueOf(Result);
     return ResultS;
    }

    public static char[] pow(char[] Symb, char[] Result , int i){
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = Symb[i + 2];
                Result[j + 1] = Symb[i];
                Result[j + 2] = Symb[i + 1];
                int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
                Result[j + 3] = (char) (digit + '0');
                break;
            }
        }
                return Result;

        }
    public static char[] epsilon(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = 'e';
                Result[j + 1] = '^';
                Result[j + 2] = 'x';
                break;
            }
        }
            return Result;
    }
    private static char[] log(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = '1';
                Result[j+1] = '/';
                Result[j+2] = '(';
                Result[j+3] = Symb[i+4];
                Result[j+4] = 'l';
                Result[j+5] = 'n';
                Result[j+6] = Symb[i+3];
                Result[j+7] = ')';
                break;
            }
        }
        return Result;
    }
    private static char[] ln(char[] Symb, char[] Result , int i)  {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = '1';
                Result[j+1] = '/';
                Result[j+2] = 'x';
                break;
            }
        }

        return Result;
    }
    public static char[] sin(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = 'c';
                Result[j+1] = 'o';
                Result[j+2] = 's';
                Result[j+3] = Symb[i + 3];
                break;
            }
        }
        return Result;
    }
    public static char[] cos(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = 's';
                Result[j + 1] = 'i';
                Result[j + 2] = 'n';
                Result[j + 3] = Symb[i + 3];
                break;
            }
        }
            return Result;
        }

    public static char[] sqrt(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = '1';
                Result[j+1] = '/';
                Result[j+2] = '(';
                Result[j+3] = '2';
                Result[j+4] = 's';
                Result[j+5] = 'q';
                Result[j+6] = 'r';
                Result[j+7] = 't';
                Result[j+8] = '(';
                Result[j+9] = Symb[i + 5];
                Result[j+10] = ')';
                Result[j+11] = ')';
                break;
            }
        }
            return Result;
        }
    private static char[] tg(char[] Symb, char[] Result , int i) {
        for(int j=0; j < Result.length; ++j) {
            if(Result[j] == 0) {
                Result[j] = '1';
                Result[j+1] = '/';
                Result[j+2] = '(';
                Result[j+3] = 'c';
                Result[j+4] = 'o';
                Result[j+5] = 's';
                Result[j+6] = '^';
                Result[j+7] = '2';
                Result[j+8] = '(';
                Result[j+9] = Symb[i+3];
                Result[j+10] = ')';
                Result[j+11] = ')';
                break;
            }
        }
        return Result;
    }
}
