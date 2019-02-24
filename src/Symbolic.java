import java.util.Scanner;
class Symbolic {
    private static boolean ispow = false;
    private static boolean hasSymbol = false;
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
             if (ispow == true) {
                 Result[i+2] = 'e';
                 Result[i + 3] = '^';
                 Result[i + 4] = 'x';
                 continue;
             }
             else {
                 Result[i +1] = 'e';
                 Result[i + 2] = '^';
                 Result[i + 3] = 'x';
                 continue;
             }
             //i+=2;
         }
         // is -
         if (Symb[i] == '-') {
             if (ispow == true) {
                 Result[i + 2] = '-';
                 hasSymbol = true;
             } else {
                 Result[i+1] = '-';
                 hasSymbol = true;
             }
         }
         //is +
         if (Symb[i] == '+') {
             if (ispow == true) {
                 Result[i + 2] = '+';
                 hasSymbol = true;
             } else {
                 Result[i+1] = '+';
                 hasSymbol = true;
             }
         }
     }
        ResultS = String.valueOf(Result);
     return ResultS;
    }

    public static char[] pow(char[] Symb, char[] Result , int i){
        ispow = true;
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
}