public class Transform {
    private Node previousNode;
    private Node nodeExpr;
    private Node computeNode;
    private boolean checkNodeId = false;
    public Node derivate(Node symb) {
        Node derivatedNode = null;
        if(symb.id != Expressions.Switch) {
            derivatedNode = getByInOrder(symb);
        }
        else{
            Parse parse = new Parse();
            nodeExpr = parse.getContext(); // use default context
            Write write = new Write();
            write.writeNode(nodeExpr);
            derivatedNode = execSymbAlgo(symb);
        }
        if(derivatedNode == null) {
            return computeNode;
        }
        else {
            return derivatedNode;
        }
    }
    public Node getByInOrder(Node node){
        Node resultNode = null;
        switch(node.id) {
            case pow: {
                resultNode = derivPow(node);
                break;
            }
            case sin: {
                resultNode = derivSin(node);
                break;
            }
            case cos: {
                resultNode = derivCos(node);
                break;
            }
            case tg: {
                resultNode = derivTg(node);
                break;
            }
            case ctg: {
                resultNode = derivCtg(node);
                break;
            }
            case arcsin: {
                resultNode = derivArcsin(node);
                break;
            }
            case arccos: {
                resultNode = derivArccos(node);
                break;
            }
            case arctg: {
                resultNode = derivArctg(node);
                break;
            }
            case arcctg: {
                resultNode = derivArcctg(node);
                break;
            }
            case sqrt: {
                resultNode = derivSqrt(node);
                break;
            }
            case ln: {
                resultNode = derivLn(node);
                break;
            }
            case exponent: {
                resultNode = derivExp(node);
                break;
            }
            case log:{
                resultNode = derivLog(node);
                break;
            }
            case minus: {
                resultNode = derivMinus(node);
                break;
            }
            case plus: {
                resultNode = derivPlus(node);
                break;
            }
        }

        return resultNode;
    }
    public Node execSymbAlgo(Node nodeAlgo) {
        Node result = null;
        Node resultNode = null;
        if(nodeAlgo.left.id == Expressions.NodeId) {
            checkNodeId = true;
        }
        if(checkNodeId == true && nodeAlgo.right != null) {
            if (nodeAlgo.right.id == Expressions.Case) {
                execSymbAlgo(nodeAlgo.right);
                return null;
            }
            if (nodeAlgo.id == Expressions.Case) {
                //nodeAlgo.left.id - case condition
                if(nodeAlgo.left.id == nodeExpr.id ) {
                    if(nodeAlgo.right.id == Expressions.body) {
                        Node bodyNode = nodeAlgo.right;
                        if(bodyNode.left.id == Expressions.assign) {
                            Node assignNode = bodyNode.left;
                            if(assignNode.left.id == Expressions.resultNode) {
                                switch (assignNode.right.id ) {
                                    case derivPow: {
                                        result = derivPow(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivCos: {
                                        result = derivCos(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivSin: {
                                        result = derivSin(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivTg: {
                                        result = derivTg(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivCtg: {
                                        result = derivCtg(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivArcsin: {
                                        result = derivArcsin(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivArccos: {
                                        result = derivArccos(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivArctg: {
                                        result = derivArctg(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivArcctg: {
                                        result = derivArcctg(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivSqrt: {
                                        result = derivSqrt(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivLn: {
                                        result = derivLn(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivLog: {
                                        result = derivLog(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivMinus: {
                                        result = derivMinus(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                    case derivPlus: {
                                        result = derivPlus(nodeExpr);
                                        if(result != null) {
                                            computeNode = result;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    execSymbAlgo(nodeAlgo.right);
                    return null;
                }
            }
        }
        return result;
    }
    public Node derivPow(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivSin(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivCos(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivTg(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivCtg(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivArcsin(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivArccos(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivArctg(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivArcctg(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivSqrt(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivLn(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivExp(Node node) {
        Node resultNode = null;
        if (node.right.id == Expressions.x) {
            Node lnNode = new Node(0, Expressions.pow);
            lnNode.left = new Node(0, Expressions.exponent);// e^
            lnNode.right = new Node(0, Expressions.x); //x;
            resultNode = lnNode;
            previousNode = lnNode;
        }
        return resultNode;
    }
    public Node derivLog(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivMinus(Node node) {
        Node resultNode = null;
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
        return resultNode;
    }
    public Node derivPlus(Node node) {
        Node resultNode = null;
        Node plusNode = new Node(0, Expressions.plus);
        plusNode.right = getByInOrder(node.right);
        plusNode.left= getByInOrder(node.left);
        Node plusleft = getByInOrder(node.right);
        if(plusleft.id == Expressions.minus){
            plusNode.id = Expressions.minus;
            plusleft.id = plusleft.left.id;
            plusleft.value = plusleft.left.value;
            try {
                plusleft.right = plusleft.left.right;
            }
            catch(Exception NullPointerException ) {
                ;
            }
            try{
                plusleft.left = plusleft.left.left;
            }
            catch(Exception NullPointerException ) {
                ;
            }
        }
        plusNode.right= plusleft;
        resultNode = plusNode;
        previousNode = plusNode;
        return resultNode;
    }
    public Node hardArgument(Node node) {
        Transform transform2 = new Transform();
        Node argumentNode = transform2.getByInOrder(node);
        return argumentNode;
    }

}
