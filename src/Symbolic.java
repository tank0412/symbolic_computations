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
     boolean hasSymbol = false;
     for(int i = 0; i < Symb.length; ++ i){
         if(Symb[i] == 'x' && Symb[i+1] == '^' && Character.isDigit(Symb[i+2])) { // for pow
             if(hasSymbol == false) {
                 Result[i] = Symb[i + 2];
                 Result[i + 1] = Symb[i];
                 Result[i + 2] = Symb[i + 1];
                 int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
                 Result[i + 3] = (char) (digit + '0');
             }
             else{
                 Result[i+1] = Symb[i + 2];
                 Result[i + 2] = Symb[i];
                 Result[i + 3] = Symb[i + 1];
                 int digit = (Character.getNumericValue(Symb[i + 2])) - 1;
                 Result[i + 4] = (char) (digit + '0');
             }
         }
         if (Symb[i] == '-' ) {
             Result[i + 1] = '-';
             hasSymbol = true;
         }
         if (Symb[i] == '+' ) {
             Result[i + 1] = '+';
             hasSymbol= true;
         }
     }
        ResultS = String.valueOf(Result);
     return ResultS;
    }
}