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
                    resultNode = hardArgument(node.right,powNode); ;
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
                    resultNode = hardArgument(node.right,sinNode);
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
                    resultNode = hardArgument(node.right,cosNode);
                }
                break;
            }
            case tg: {
                Node tgNode = new Node(Expressions.div);
                tgNode.left = new Digit(1);
                Node denominator = preparePow();
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
                    resultNode = hardArgument(node.right,tgNode);
                }
                break;
            }
            case ctg: {
                //Node tgNode = new Node(0,Expressions.div);
                Node ctgNode = new Node(Expressions.minus);
                ctgNode.left = new Node(Expressions.div);
                ((Node) ctgNode.left).left= new Digit(1);
                Node denominator = preparePow();
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
                    resultNode = hardArgument(node.right,ctgNode);
                }
                break;
            }
            case arcsin: {
                Node arcsinNode = new Node(Expressions.div);
                arcsinNode.left = new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(Expressions.minus);
                ((Node) denominator.left).left= new Digit(1);
                Node powArgument = preparePow();
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
                    resultNode = hardArgument(node.right,arcsinNode);
                }
                break;
            }
            case arccos: {
                //Node arccosNode = new Node(0,Expressions.div);
                Node arccosNode = new Node(Expressions.minus);
                arccosNode.left = new Node(Expressions.div);
                ((Node) arccosNode.left).left= new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                denominator.left= new Node(Expressions.minus);
                ((Node) denominator.left).left = new Digit(1);;
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
                    resultNode = hardArgument(node.right,arccosNode);
                }
                break;
            }
            case arctg: {
                Node arctgNode = new Node(Expressions.div);
                arctgNode.left = new Digit(1);;// 1/()
                Node denominator = new Node(Expressions.plus); //+
                denominator.left=new Digit(1);; //1
                Node powArgument = preparePow(); //1 / 1 + ()^2

                if(node.right.id == Expressions.x) {
                    powArgument.left = new Node(Expressions.x);
                    resultNode=finishDenominator(denominator,powArgument,arctgNode);
                }
                else {
                    powArgument.left = node.right;
                    denominator.right = powArgument;
                    arctgNode.right = denominator;
                    resultNode = hardArgument(node.right,arctgNode);
                }
                break;
            }
            case arcctg: {
                Node arcctgNode = new Node(Expressions.minus);
                arcctgNode.left = new Node(Expressions.div);
                ((Node) arcctgNode.left).left=  new Digit(1);// 1/()
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
                    resultNode = hardArgument(node.right,arcctgNode);
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
                    previousNode = sqrtNode;
                    resultNode = sqrtNode;
                }
                break;
            }
            case ln: {
                Node lnNode = new Node(Expressions.div);
                lnNode.left = new Digit(1);;// 1/()
                if (node.right.id == Expressions.x) {
                    Node denominator = new Node(Expressions.x); //x
                    lnNode.right = denominator;
                    previousNode = lnNode;
                    resultNode = lnNode;
                }
                else{
                    Node denominator =node.right; //x
                    lnNode.right = denominator;
                    resultNode = hardArgument(node.right,lnNode);
                }
                break;
            }
            case exponent: {
                if (node.right.id == Expressions.x) {
                    Node lnNode = new Node(Expressions.pow);
                    lnNode.left = new Node(Expressions.exponent);// e^
                    lnNode.right = new Node(Expressions.x); //x;
                    previousNode = lnNode;
                    resultNode = lnNode;
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
                    resultNode=finishDenominator(denominator,lnArgument,logNode);
                }
                else{
                    denominator.left = node.right; //x
                    Node lnArgument = new Node(Expressions.ln); //ln
                    lnArgument.left = new Node(((Node) node.left).id);// a
                    denominator.right = lnArgument;
                    logNode.right = denominator;
                    resultNode = hardArgument(node.right,logNode);
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
                    if(minusleft.left != null) {
                        minusleft.id = (((Node) minusleft.left).id);

                        ((Node) minusleft).right = ((Node) minusleft.left).right;
                        ((Node) minusleft).left = ((Node) minusleft.left).left;
                    }
                }
                else {
                    if((minusleft.id == Expressions.mul && ((Node) minusleft.left).id == Expressions.minus )) {
                        minusNode.id = Expressions.plus;

                        ((Node) minusleft.left).id = ((Node) ((Node) minusleft.left).left).id;
                        ((Node) minusleft.left).right = ((Node) ((Node) minusleft.left).left).right;
                        ((Node) minusleft.left).left = ((Node) ((Node) minusleft.left).left).left;
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
    public Node hardArgument(Node node, Node leftNode) {
        Transform transform2 = new Transform();
        Node argumentNode = transform2.getByInOrder(node);
        Node mulNode = new Node(Expressions.mul);
        mulNode.left=leftNode;
        mulNode.right=argumentNode;
        previousNode = mulNode;
        return mulNode;
    }
    public Node preparePow() {
        Node denominator = new Node(Expressions.pow);
        denominator.right =new Digit(2); //  / ()^2
        return denominator;
    }
    public Node finishDenominator(Node denominator, Node argument, Node node) {
        denominator.right = argument;
        node.right = denominator;
        previousNode = node;
        return node;
    }

}
