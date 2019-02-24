import java.util.Scanner;
class Symbolic {
    private static boolean ispow = false;
    private static boolean hasSymbol = false;
    private static boolean islog = false;
    private static boolean isln = false;
    private static boolean issin = false;
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
             Result = epsilon(Symb,Result,i);
             continue;
         }
         if (Symb[i] == 'l' && Symb[i + 1] == 'o' && Symb[i + 2] == 'g') {
             //for log
             Result = log(Symb,Result,i);
             continue;
         }
         if (Symb[i] == 'l' && Symb[i + 1] == 'n' && Symb[i + 2] == 'x') {
             //for ln
             Result = ln(Symb,Result,i);
             continue;
         }
         if (Symb[i] == 's' && Symb[i + 1] == 'i' && Symb[i + 2] == 'n') {
             //for sin
             Result = sin(Symb,Result,i);
             continue;
         }
         // is -
         if (Symb[i] == '-') {
             if (ispow == true || isln == true || issin == true) {
                 Result[i + 2] = '-';
                 hasSymbol = true;
             } else {
                 if(islog == true) {
                     Result[i + 5] = '-';
                 }
                 else {
                     Result[i + 1] = '-';
                     hasSymbol = true;
                 }
             }
         }
         //is +
         if (Symb[i] == '+') {
             if (ispow == true|| isln == true || issin == true) {
                 Result[i + 2] = '+';
                 hasSymbol = true;
             } else {
                 if(islog == true) {
                     Result[i + 5] = '+';
                 }
                 else {
                     Result[i + 1] = '+';
                     hasSymbol = true;
                 }
             }
         }
     }
        ResultS = String.valueOf(Result);
     return ResultS;
    }

    public static char[] pow(char[] Symb, char[] Result , int i){
        ispow = true;
        if(islog == true) {
            Result[i +5] = Symb[i + 2];
            Result[i + 6] = Symb[i];
            Result[i + 7] = Symb[i + 1];
            int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
            Result[i + 8] = (char) (digit + '0');
            return Result;
        }
        if(hasSymbol == false) {
            Result[i] = Symb[i + 2];
            Result[i + 1] = Symb[i];
            Result[i + 2] = Symb[i + 1];
            int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
            Result[i + 3] = (char) (digit + '0');
            return Result;
        }
        else{
            Result[i+2] = Symb[i + 2];
            Result[i + 3] = Symb[i];
            Result[i + 4] = Symb[i + 1];
            int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
            Result[i + 5] = (char) (digit + '0');
            return Result;
        }
    }
    public static char[] epsilon(char[] Symb, char[] Result , int i) {
        if (ispow == true || isln == true || issin == true) {
            Result[i+2] = 'e';
            Result[i + 3] = '^';
            Result[i + 4] = 'x';
            return Result;
        }
        else {
            if(islog == true) {
                Result[i + 5] = Symb[i];
                Result[i + 6] = Symb[i + 1];
                Result[i +7] = Symb[i + 2];
                return Result;
            }
            else {
                Result[i + 1] = 'e';
                Result[i + 2] = '^';
                Result[i + 3] = 'x';
                return Result;
            }
        }
    }
    private static char[] log(char[] Symb, char[] Result , int i) {
        islog = true;
        Result[i+2] = '1';
        Result[i + 3] = '/';
        Result[i + 4] = '(';
        Result[i + 5] = Symb[i+4];
        Result[i + 6] = 'l';
        Result[i + 7] = 'n';
        Result[i + 8] = Symb[i+3];
        Result[i + 9] = ')';
        return Result;
    }
    private static char[] ln(char[] Symb, char[] Result , int i)  {
        isln = true;
        if(hasSymbol == true) {
            Result[i+2] = '1';
            Result[i + 3] = '/';
            Result[i + 4] = 'x';
        }
        else
        {
            if(islog == true) {
                Result[i+5] = '1';
                Result[i + 6] = '/';
                Result[i + 7] = 'x';
            }
            else {
                Result[i] = '1';
                Result[i + 1] = '/';
                Result[i + 2] = 'x';
            }
        }

        return Result;
    }
    public static char[] sin(char[] Symb, char[] Result , int i) {
            issin = true;
        if(islog == true) {
            Result[i + 5] = 'c';
            Result[i + 6] = 'o';
            Result[i + 7] = 's';
            Result[i + 8] = Symb[i + 3];
            return Result;
        }
        else {
            Result[i + 2] = 'c';
            Result[i + 3] = 'o';
            Result[i + 4] = 's';
            Result[i + 5] = Symb[i + 3];
            return Result;
        }

    }
}