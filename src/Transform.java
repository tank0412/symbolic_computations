public class Transform {
    private Node previousNode;
    public Node derivate(Node symb) {
        Node derivatedNode = getByInOrder(symb);
        return derivatedNode;
    }
    public Node getByInOrder(Node node){
        Node resultNode = null;
        switch(node.id) {
            case pow: {
                Node powNode = new Node(Expressions.mul);
                double digit = 0;
                if(node.left instanceof Digit) {
                    digit = ((Digit) node.left).value;
                    powNode.left = new Digit(digit);
                }
                Node rightNode = new Node(Expressions.pow);
                if(node.right.id == Expressions.x) {
                    rightNode.right=new Node(Expressions.x);
                    rightNode.left =  new Digit((digit-1));
                    powNode.right = rightNode;
                    resultNode = powNode;
                    previousNode = powNode;
                }
                else {
                    rightNode.right=node.right;
                    //rightNode.right=hardArgument(node.right); // x
                    rightNode.left = new Digit((digit-1));
                    powNode.right = rightNode;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left=powNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case sin: {
                Node sinNode = new Node(Expressions.cos);
                if(node.right.id == Expressions.x) {
                  sinNode.right = new Node(Expressions.x);
                  resultNode = sinNode;
                  previousNode = sinNode;
                }
                else{
                    sinNode.right = node.right;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = sinNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case cos: {
                Node cosNode = new Node(Expressions.minus);
                cosNode.left=new Node(Expressions.sin);
                if(node.right.id == Expressions.x) {
                    ((Node) cosNode.left).right= new Node(Expressions.x);
                    resultNode = cosNode;
                    previousNode = cosNode;
                }
                else {
                    ((Node) cosNode.left).right= node.right;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = cosNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case tg: {
                Node tgNode = new Node(Expressions.div);
                tgNode.left = new Digit(1);
                Node denominator = new Node(Expressions.pow);
                denominator.right =new Digit(2);
                denominator.left= new Node(Expressions.cos);
                if(node.right.id == Expressions.x) {
                    ((Node) denominator.left).right= new Node(Expressions.x);
                    tgNode.right = denominator;
                    resultNode = tgNode;
                    previousNode = tgNode;
                }
                else  {
                    ((Node) denominator.left).right= new Node(Expressions.x);
                    tgNode.right = denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = tgNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case ctg: {
                //Node tgNode = new Node(0,Expressions.div);
                Node ctgNode = new Node(Expressions.minus);
                ctgNode.left = new Node(Expressions.div);
                ((Node) ctgNode.left).left= new Digit(1);
                Node denominator = new Node(Expressions.pow);
                denominator.right = new Digit(2);
                denominator.left= new Node(Expressions.sin);
                if(node.right.id == Expressions.x) {
                    ((Node) denominator.left).right= new Node(Expressions.x);
                    ((Node) ctgNode.left).right= denominator;
                    resultNode = ctgNode;
                    previousNode = ctgNode;
                }
                else{
                    ((Node) denominator.left).right= node.right;
                    ((Node) ctgNode.left).right= denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = ctgNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arcsin: {
                Node arcsinNode = new Node(Expressions.div);
                arcsinNode.left = new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(Expressions.minus);
                ((Node) denominator.left).left= new Digit(1);
                Node powArgument = new Node(Expressions.pow); //()^2
                powArgument.right = new Digit(2);;
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(Expressions.x);
                    ((Node) denominator.left).right= powArgument;
                    arcsinNode.right = denominator;
                    resultNode = arcsinNode;
                    previousNode = arcsinNode;
                }
                else {
                    powArgument.left = node.right;
                    ((Node) denominator.left).right= powArgument;
                    arcsinNode.right = denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = arcsinNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arccos: {
                //Node arccosNode = new Node(0,Expressions.div);
                Node arccosNode = new Node(Expressions.minus);
                arccosNode.left = new Node(Expressions.div);
                ((Digit) arccosNode.left).left= new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(Expressions.minus);
                ((Digit) denominator.left).left = new Digit(1);;
                Node powArgument = new Node(Expressions.pow); //x^2
                powArgument.right = new Digit(2);;
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(Expressions.x);
                    ((Node) denominator.left).right= powArgument;
                    ((Node) arccosNode.left).right= denominator;
                    resultNode = arccosNode;
                    previousNode = arccosNode;
                }
                else {
                    powArgument.left = node.right;
                    ((Node) denominator.left).right= powArgument;
                    ((Node) arccosNode.left).right= denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = arccosNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arctg: {
                Node arctgNode = new Node(Expressions.div);
                arctgNode.left = new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.plus); //+
                denominator.left=new Digit(1);; //1
                Node powArgument = new Node(Expressions.pow); //()^2
                powArgument.right = new Digit(2);;

                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(Expressions.x);
                    denominator.right = powArgument;
                    arctgNode.right = denominator;
                    resultNode = arctgNode;
                    previousNode = arctgNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.right = powArgument;
                    arctgNode.right = denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = arctgNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arcctg: {
                Node arcctgNode = new Node(Expressions.minus);
                arcctgNode.left = new Node(Expressions.div);
                ((Digit) arcctgNode.left).left=  new Digit(1);// 1/()
                Node denominator = new Node(Expressions.plus); //sqrt(1-
                denominator.left= new Digit(1);;
                Node powArgument = new Node(Expressions.pow); //x^2
                powArgument.right = new Digit(2);;
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(Expressions.x);
                    denominator.right = powArgument;
                    ((Node) arcctgNode.left).right=  denominator;
                    resultNode = arcctgNode;
                    previousNode = arcctgNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.right = powArgument;
                    ((Node) arcctgNode.left).right=  denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = arcctgNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case sqrt: {
                if (node.right.id == Expressions.x) {
                    Node sqrtNode = new Node(Expressions.div);
                    sqrtNode.left = new Digit(1);;// 1/()
                    Node denominator = new Node(Expressions.mul); //*
                    denominator.left = new Digit(2);; //2
                    Node sqrtArgument = new Node(Expressions.sqrt); //sqrt
                    sqrtArgument.left = new Node(Expressions.x);// x
                    denominator.right = sqrtArgument;
                    sqrtNode.right = denominator;
                    resultNode = sqrtNode;
                    previousNode = sqrtNode;
                }
                break;
            }
            case ln: {
                Node lnNode = new Node(Expressions.div);
                lnNode.left = new Digit(1);;// 1/()
                if (node.right.id == Expressions.x) {
                    Node denominator = new Node(Expressions.x); //x
                    lnNode.right = denominator;
                    resultNode = lnNode;
                    previousNode = lnNode;
                }
                else{
                    Node denominator =node.right; //x
                    lnNode.right = denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = lnNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case exponent: {
                if (node.right.id == Expressions.x) {
                    Node lnNode = new Node(Expressions.pow);
                    lnNode.left = new Node(Expressions.exponent);// e^
                    lnNode.right = new Node(Expressions.x); //x;
                    resultNode = lnNode;
                    previousNode = lnNode;
                }
                break;
            }
            case log:{
                Node logNode = new Node(Expressions.div);
                logNode.left = new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.mul); //*
                if (node.right.id == Expressions.x) {
                    denominator.left = new Node(Expressions.x); //x
                    Node lnArgument = new Node(Expressions.ln); //ln
                    lnArgument.left = new Node(((Node) node.left).id);// a
                    denominator.right = lnArgument;
                    logNode.right = denominator;
                    resultNode = logNode;
                    previousNode = logNode;
                }
                else{
                    denominator.left = node.right; //x
                    Node lnArgument = new Node(Expressions.ln); //ln
                    lnArgument.left = new Node(((Node) node.left).id);// a
                    denominator.right = lnArgument;
                    logNode.right = denominator;
                    Node mulNode = new Node(Expressions.mul);
                    mulNode.left = logNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case minus: {
                Node minusNode = new Node(Expressions.minus);
                if(previousNode != null) {
                    minusNode.right = previousNode;
                }
                else{
                    minusNode.left = getByInOrder(((Node) node.left));
                }
                Node minusleft = getByInOrder(node.right);
                if(minusleft.id == Expressions.minus){
                    minusNode.id = Expressions.plus;
                    minusleft.id = (((Node) minusleft.left).id);
                    //((Node) minusleft).value = minusleft.left.value;
                    try {
                        ((Node) minusleft).right = ((Node) minusleft.left).right;
                    }
                    catch(Exception NullPointerException ) {
                        ;
                    }
                    try{
                        ((Node) minusleft).left = ((Node) minusleft.left).left;
                    }
                    catch(Exception NullPointerException ) {
                        ;
                    }
                }
                else {
                    if((minusleft.id == Expressions.mul && ((Node) minusleft.left).id == Expressions.minus )) {
                        minusNode.id = Expressions.plus;

                        ((Node) minusleft.left).id = ((Node) ((Node) minusleft.left).left).id;
                        //minusleft.left.value = minusleft.left.left.value;
                        try {
                            ((Node) minusleft.left).right = ((Node) ((Node) minusleft.left).left).right;
                        }
                        catch(Exception NullPointerException ) {
                            ;
                        }
                        try{
                            ((Node) minusleft.left).left = ((Node) ((Node) minusleft.left).left).left;
                        }
                        catch(Exception NullPointerException ) {
                            ;
                        }
                    }
                }
                minusNode.right= minusleft;
                resultNode = minusNode;
                previousNode = minusNode;
                break;
            }
            case plus: {
                Node plusNode = new Node(Expressions.plus);
                plusNode.right = getByInOrder(node.right);
                plusNode.left= getByInOrder(((Node) node.left));
                resultNode = plusNode;
                previousNode = plusNode;
                break;
            }
        }

        return resultNode;
    }
    public Node hardArgument(Node node) {
        Transform transform2 = new Transform();
        Node argumentNode = transform2.getByInOrder(node);
        return argumentNode;
    }

}
