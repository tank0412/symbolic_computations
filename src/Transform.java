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
                Node powNode = new Node(0,Expressions.mul);
                int digit = 0;
                if(node.left.id == Expressions.digit) {
                    digit = (int)node.left.value;
                    powNode.left = new Node(digit, Expressions.digit);
                }
                Node rightNode = new Node(0,Expressions.pow);
                if(node.right.id == Expressions.x) {
                    rightNode.right=new Node(0,Expressions.x);
                    rightNode.left = new Node((digit-1), Expressions.digit);
                    powNode.right = rightNode;
                    resultNode = powNode;
                    previousNode = powNode;
                }
                else {
                    rightNode.right=node.right;
                    //rightNode.right=hardArgument(node.right); // x
                    rightNode.left = new Node((digit-1), Expressions.digit);
                    powNode.right = rightNode;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left=powNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case sin: {
                Node sinNode = new Node(0,Expressions.cos);
                if(node.right.id == Expressions.x) {
                  sinNode.right = new Node(0,Expressions.x);
                  resultNode = sinNode;
                  previousNode = sinNode;
                }
                else{
                    sinNode.right = node.right;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = sinNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case cos: {
                Node cosNode = new Node(0,Expressions.minus);
                cosNode.left=new Node(0,Expressions.sin);
                if(node.right.id == Expressions.x) {
                    cosNode.left.right = new Node(0,Expressions.x);
                    resultNode = cosNode;
                    previousNode = cosNode;
                }
                else {
                    cosNode.left.right = node.right;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = cosNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case tg: {
                Node tgNode = new Node(0,Expressions.div);
                tgNode.left = new Node(1,Expressions.digit);
                Node denominator = new Node(0,Expressions.pow);
                denominator.right = new Node(2,Expressions.digit);
                denominator.left= new Node(0,Expressions.cos);
                if(node.right.id == Expressions.x) {
                    denominator.left.right = new Node(0,Expressions.x);
                    tgNode.right = denominator;
                    resultNode = tgNode;
                    previousNode = tgNode;
                }
                else  {
                    denominator.left.right = node.right;
                    tgNode.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = tgNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case ctg: {
                //Node tgNode = new Node(0,Expressions.div);
                Node ctgNode = new Node(0,Expressions.minus);
                ctgNode.left = new Node(0,Expressions.div);
                ctgNode.left.left = new Node(1,Expressions.digit);
                Node denominator = new Node(0,Expressions.pow);
                denominator.right = new Node(2,Expressions.digit);
                denominator.left= new Node(0,Expressions.sin);
                if(node.right.id == Expressions.x) {
                    denominator.left.right = new Node(0,Expressions.x);
                    ctgNode.left.right = denominator;
                    resultNode = ctgNode;
                    previousNode = ctgNode;
                }
                else{
                    denominator.left.right = node.right;
                    ctgNode.left.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = ctgNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arcsin: {
                Node arcsinNode = new Node(0,Expressions.div);
                arcsinNode.left = new Node(1,Expressions.digit);// 1/()
                Node denominator = new Node(0,Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(0,Expressions.minus);
                denominator.left.left = new Node(1,Expressions.digit);
                Node powArgument = new Node(0,Expressions.pow); //()^2
                powArgument.right = new Node(2, Expressions.digit);
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(0, Expressions.x);
                    denominator.left.right = powArgument;
                    arcsinNode.right = denominator;
                    resultNode = arcsinNode;
                    previousNode = arcsinNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.left.right = powArgument;
                    arcsinNode.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = arcsinNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arccos: {
                //Node arccosNode = new Node(0,Expressions.div);
                Node arccosNode = new Node(0,Expressions.minus);
                arccosNode.left = new Node(0,Expressions.div);
                arccosNode.left.left = new Node(1,Expressions.digit);// 1/()
                Node denominator = new Node(0,Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(0,Expressions.minus);
                denominator.left.left = new Node(1,Expressions.digit);
                Node powArgument = new Node(0,Expressions.pow); //x^2
                powArgument.right = new Node(2, Expressions.digit);
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(0, Expressions.x);
                    denominator.left.right = powArgument;
                    arccosNode.left.right = denominator;
                    resultNode = arccosNode;
                    previousNode = arccosNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.left.right = powArgument;
                    arccosNode.left.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = arccosNode;
                    mulNode.right=hardArgument(node.right); //
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arctg: {
                Node arctgNode = new Node(0,Expressions.div);
                arctgNode.left = new Node(1,Expressions.digit);// 1/()
                Node denominator = new Node(0,Expressions.plus); //+
                denominator.left= new Node(1,Expressions.digit); //1
                Node powArgument = new Node(0,Expressions.pow); //()^2
                powArgument.right = new Node(2, Expressions.digit);

                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(0, Expressions.x);
                    denominator.right = powArgument;
                    arctgNode.right = denominator;
                    resultNode = arctgNode;
                    previousNode = arctgNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.right = powArgument;
                    arctgNode.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = arctgNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case arcctg: {
                Node arcctgNode = new Node(0,Expressions.minus);
                arcctgNode.left = new Node(0,Expressions.div);
                arcctgNode.left.left = new Node(1,Expressions.digit);// 1/()
                Node denominator = new Node(0,Expressions.plus); //sqrt(1-
                denominator.left= new Node(1,Expressions.digit);
                Node powArgument = new Node(0,Expressions.pow); //x^2
                powArgument.right = new Node(2, Expressions.digit);
                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(0, Expressions.x);
                    denominator.right = powArgument;
                    arcctgNode.left.right = denominator;
                    resultNode = arcctgNode;
                    previousNode = arcctgNode;
                }
                else {
                    powArgument.left = node.right;
                    denominator.right = powArgument;
                    arcctgNode.left.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = arcctgNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case sqrt: {
                if (node.right.id == Expressions.x) {
                    Node sqrtNode = new Node(0, Expressions.div);
                    sqrtNode.left = new Node(1, Expressions.digit);// 1/()
                    Node denominator = new Node(0, Expressions.mul); //*
                    denominator.left = new Node(2, Expressions.digit); //2
                    Node sqrtArgument = new Node(0, Expressions.sqrt); //sqrt
                    sqrtArgument.left = new Node(0, Expressions.x);// x
                    denominator.right = sqrtArgument;
                    sqrtNode.right = denominator;
                    resultNode = sqrtNode;
                    previousNode = sqrtNode;
                }
                break;
            }
            case ln: {
                Node lnNode = new Node(0, Expressions.div);
                lnNode.left = new Node(1, Expressions.digit);// 1/()
                if (node.right.id == Expressions.x) {
                    Node denominator = new Node(0, Expressions.x); //x
                    lnNode.right = denominator;
                    resultNode = lnNode;
                    previousNode = lnNode;
                }
                else{
                    Node denominator =node.right; //x
                    lnNode.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = lnNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case exponent: {
                if (node.right.id == Expressions.x) {
                    Node lnNode = new Node(0, Expressions.pow);
                    lnNode.left = new Node(0, Expressions.exponent);// e^
                    lnNode.right = new Node(0, Expressions.x); //x;
                    resultNode = lnNode;
                    previousNode = lnNode;
                }
                break;
            }
            case log:{
                Node logNode = new Node(0, Expressions.div);
                logNode.left = new Node(1, Expressions.digit);// 1/()
                Node denominator = new Node(0, Expressions.mul); //*
                if (node.right.id == Expressions.x) {
                    denominator.left = new Node(0, Expressions.x); //x
                    Node lnArgument = new Node(0, Expressions.ln); //ln
                    lnArgument.left = new Node(0, node.left.id);// a
                    denominator.right = lnArgument;
                    logNode.right = denominator;
                    resultNode = logNode;
                    previousNode = logNode;
                }
                else{
                    denominator.left = node.right; //x
                    Node lnArgument = new Node(0, Expressions.ln); //ln
                    lnArgument.left = new Node(0, node.left.id);// a
                    denominator.right = lnArgument;
                    logNode.right = denominator;
                    Node mulNode = new Node(0,Expressions.mul);
                    mulNode.left = logNode;
                    mulNode.right=hardArgument(node.right);
                    resultNode = mulNode;
                    previousNode = mulNode;
                }
                break;
            }
            case minus: {
                Node minusNode = new Node(0, Expressions.minus);
                if(previousNode != null) {
                    minusNode.right = previousNode;
                }
                else{
                    minusNode.left = getByInOrder(node.left);
                }
                Node minusleft = getByInOrder(node.right);
                if(minusleft.id == Expressions.minus){
                    minusNode.id = Expressions.plus;
                    minusleft.id = minusleft.left.id;
                    minusleft.value = minusleft.left.value;
                    try {
                        minusleft.right = minusleft.left.right;
                    }
                    catch(Exception NullPointerException ) {
                        ;
                    }
                    try{
                    minusleft.left = minusleft.left.left;
                    }
                    catch(Exception NullPointerException ) {
                        ;
                    }
                }
                else {
                    if((minusleft.id == Expressions.mul && minusleft.left.id == Expressions.minus )) {
                        minusNode.id = Expressions.plus;

                        minusleft.left.id = minusleft.left.left.id;
                        minusleft.left.value = minusleft.left.left.value;
                        try {
                            minusleft.left.right = minusleft.left.left.right;
                        }
                        catch(Exception NullPointerException ) {
                            ;
                        }
                        try{
                            minusleft.left.left = minusleft.left.left.left;
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
                Node plusNode = new Node(0, Expressions.plus);
                plusNode.right = getByInOrder(node.right);
                plusNode.left= getByInOrder(node.left);
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
