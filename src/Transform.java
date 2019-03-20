public class Transform {
    private Node previousNode;
    private Node hardDerivative = null;
    public Node derivate(Node symb) {
        Node context;
        Node derivatedNode = null;
        if(Import.rules.size() == 0) {
            derivatedNode = getByInOrder(symb);
            return derivatedNode;
        }
        else {
            Parse parse = new Parse();
            context = parse.getContext();
            context = symbAlgo(context);
            return context;
        }
    }

    private Node symbAlgo(Node expr) {
        if(hardDerivative !=null) {
            expr = traverseExpr(expr);
            Node hardDer = hardDerivative;
            hardDerivative = null;
            Node mul = new Node(Expressions.mul);
            expr.parent=mul;
            mul.arguments.add(expr);
            Transform mytransofrm = new Transform();
            Node hardDerivate = mytransofrm.symbAlgo(hardDer);
            mul.arguments.add(hardDerivate);
            return mul;
        }
            for (Node rule : Import.rules) {
                 if(expr.id == rule.arguments.get(0).id) {
                     if(expr.arguments.get(0).id != Expressions.x) {
                         hardDerivative=expr.arguments.get(0);
                     }
                     expr.id = rule.arguments.get(1).id;
                     int index = 0;
                     for(Node node : rule.arguments  ) {
                         if(index <expr.arguments.size() ) {
                             expr.arguments.set(index, symbAlgo(rule.arguments.get(1).arguments.get(index)));
                         }
                         else {
                             if(index <rule.arguments.get(1).arguments.size() ) {
                                 expr.arguments.add(index, symbAlgo(rule.arguments.get(1).arguments.get(index)));
                             }
                         }
                         index++;
                     }
                     break;
                 }
            }
            return expr;
    }

    public Node traverseExpr(Node expr) {
        int index = 0;
        if(expr.id == Expressions.x) {
            Node getHardDeriv = hardDerivative.traverseInOrderCopy(hardDerivative);
            expr = getHardDeriv;
            return expr;
        }
        for(Node node : expr.arguments  ) {
            expr.arguments.set(index, traverseExpr(node));
            index++;
        }
        return expr;
    }

    private Node getByInOrder(Node node){
        Node resultNode = null;
        switch(node.id) {
            case pow: {
                Node powNode = new Node(Expressions.mul);
                double digit = 0;
                for(Node node1: node.arguments) {
                    if(node1 instanceof Digit) {
                        digit = ((Digit) node1).value;
                        powNode.arguments.add( new Digit(digit,powNode));
                        break;
                    }
                }
                Node rightNode = new Node(Expressions.pow);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        rightNode.arguments.add(new Node(Expressions.x,rightNode));
                        rightNode.arguments.add(new Digit((digit - 1), rightNode));
                        rightNode.parent = powNode;
                        powNode.arguments.add(rightNode);
                        resultNode = powNode;
                        previousNode = powNode;
                        break;
                    } else {
                        rightNode.arguments.add(node2);
                        //rightNode.right=hardArgument(node.right); // x
                        rightNode.arguments.add(new Digit((digit - 1), rightNode));
                        rightNode.parent=powNode;
                        powNode.arguments.add(rightNode);
                        resultNode = hardArgument(node2, powNode);
                        break;
                    }
                }
                break;
            }
            case sin: {
                Node sinNode = new Node(Expressions.cos);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        sinNode.arguments.add(new Node(Expressions.x,sinNode));
                        resultNode = sinNode;
                        previousNode = sinNode;
                        break;
                    } else {
                        node2.parent=sinNode;
                        sinNode.arguments.add(node2);
                        resultNode = hardArgument(node2, sinNode);
                        break;
                    }
                }
                break;
            }
            case cos: {
                Node cosNode = new Node(Expressions.minus);
                cosNode.arguments.add(new Node(Expressions.sin,cosNode));
                int index = 0;
                Node argument = cosNode.arguments.get(index);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        argument.arguments.add(new Node(Expressions.x, argument));
                        cosNode.arguments.set(index,argument);
                        resultNode = cosNode;
                        previousNode = cosNode;
                        break;
                    } else {
                        node2.parent = argument;
                        argument.arguments.add(node2);
                        cosNode.arguments.set(index,argument);
                        resultNode = hardArgument(node2, cosNode);
                        break;
                    }
                }
                break;
            }
            case tg: {
                Node tgNode = new Node(Expressions.div);
                tgNode.arguments.add(new Digit(1, tgNode));
                Node denominator = preparePow();
                denominator.arguments.add(new Node(Expressions.cos, denominator));
                int index = 0;
                Node argument = denominator.arguments.get(index);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        argument.arguments.add( new Node(Expressions.x, argument));
                        denominator.arguments.set(index,argument);
                        denominator.parent=tgNode;
                        tgNode.arguments.add(denominator);
                        resultNode = tgNode;
                        previousNode = tgNode;
                        break;
                    } else {
                        argument.arguments.add( node2);
                        denominator.arguments.set(index,argument);
                        denominator.parent=tgNode;
                        tgNode.arguments.add(denominator);
                        resultNode = hardArgument(node2, tgNode);
                        break;
                    }
                }
                break;
            }
            case ctg: {
                //Node tgNode = new Node(0,Expressions.div);
                int index = 0;
                Node ctgNode = prepareMinusDiv();
                Node denominator = preparePow();
                denominator.arguments.add(new Node(Expressions.sin, denominator));
                Node argument = denominator.arguments.get(index+1);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        argument.arguments.add( new Node(Expressions.x, argument));
                        denominator.arguments.set(index+1,argument);
                        argument = ctgNode.arguments.get(index);
                        argument.arguments.add(denominator);
                        ctgNode.arguments.set(index,argument);
                        resultNode = ctgNode;
                        previousNode = ctgNode;
                        break;
                    } else {
                        node2.parent = argument;
                        argument.arguments.add( node2);
                        denominator.arguments.set(index+1,argument);
                        argument = ctgNode.arguments.get(index);
                        argument.arguments.add( denominator);
                        resultNode = hardArgument(node2, ctgNode);
                        break;
                    }
                }
                break;
            }
            case arcsin: {
                Node arcsinNode = new Node(Expressions.div);
                arcsinNode.arguments.add( new Digit(1));;// 1/()
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                denominator.arguments.add( new Node(Expressions.minus, denominator));
                int index = 0;
                Node argument = denominator.arguments.get(index);
                argument.arguments.add(new Digit(1, argument));
                Node powArgument = preparePow();
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        powArgument.arguments.add(new Node(Expressions.x,powArgument));
                        powArgument.parent = argument;
                        argument.arguments.add(powArgument);
                        denominator.parent=arcsinNode;
                        denominator.arguments.set(index,argument);
                        arcsinNode.arguments.add(denominator);
                        resultNode = arcsinNode;
                        previousNode = arcsinNode;
                        break;
                    } else {
                        node2.parent=powArgument;
                        powArgument.arguments.add(node2);
                        argument.arguments.add(node2);
                        argument.arguments.add(powArgument);
                        denominator.parent=arcsinNode;
                        denominator.arguments.set(index,argument);
                        arcsinNode.arguments.add(denominator);
                        resultNode = hardArgument(node2, arcsinNode);
                        break;
                    }
                }
                break;
            }
            case arccos: {
                //Node arccosNode = new Node(0,Expressions.div);
                int index = 0;
                Node arccosNode = prepareMinusDiv();
                Node denominator = new Node(Expressions.sqrt); //sqrt(1-
                Node minus = new Node(Expressions.minus, denominator);
                denominator.arguments.add(minus);
                Node argument = minus;
                argument.arguments.add(new Digit(1, argument));
                Node powArgument = new Node(Expressions.pow); //x^2
                powArgument.arguments.add(new Digit(2, powArgument));
                denominator.arguments.set(index,argument);
                argument=arccosNode.arguments.get(index);
                argument.arguments.add(denominator);
                arccosNode.arguments.set(index,argument);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        powArgument.arguments.add(new Node(Expressions.x, powArgument));
                        powArgument.parent=denominator;
                        denominator.parent=argument;
                        denominator.arguments.add(powArgument);
                        resultNode = arccosNode;
                        previousNode = arccosNode;
                        break;
                    } else {
                        node2.parent=powArgument;
                        powArgument.parent=argument;
                        powArgument.arguments.add(node2);
                        argument = denominator.arguments.get(index);
                        argument.arguments.add(powArgument);
                        denominator.parent=arccosNode;
                        resultNode = hardArgument(node2, arccosNode);
                        break;
                    }
                }
                break;
            }
            case arctg: {
                Node arctgNode = new Node(Expressions.div);
                arctgNode.arguments.add(new Digit(1, arctgNode));;// 1/()
                Node denominator = new Node(Expressions.plus); //+
                denominator.arguments.add(new Digit(1, denominator));; //1
                Node powArgument = preparePow(); //1 / 1 + ()^2
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        powArgument.arguments.add(new Node(Expressions.x, powArgument));
                        resultNode = finishDenominator(denominator, powArgument, arctgNode);
                        break;
                    } else {
                        node2.parent = powArgument;
                        powArgument.arguments.add(node2);
                        powArgument.parent=denominator;
                        denominator.arguments.add(powArgument);
                        denominator.parent = arctgNode;
                        arctgNode.arguments.add(denominator);
                        resultNode = hardArgument(node2, arctgNode);
                        break;
                    }
                }
                break;
            }
            case arcctg: {
                Node arcctgNode = new Node(Expressions.minus);
                arcctgNode.arguments.add(new Node(Expressions.div, arcctgNode));
                int index = 0;
                Node argument = arcctgNode.arguments.get(index);
                argument.arguments.add(new Digit(1, argument));// 1/()
                Node denominator = new Node(Expressions.plus); //sqrt(1-
                denominator.arguments.add(new Digit(1, denominator));;
                Node powArgument = new Node(Expressions.pow); //x^2
                powArgument.arguments.add(new Digit(2, powArgument));
                arcctgNode.arguments.set(index,argument);
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        powArgument.arguments.add(new Node(Expressions.x, powArgument));
                        powArgument.parent=denominator;
                        denominator.arguments.add(powArgument);
                        argument = arcctgNode.arguments.get(index);
                        denominator.parent=argument;
                        argument.arguments.add(denominator);
                        resultNode = arcctgNode;
                        previousNode = arcctgNode;
                        break;
                    } else {
                        node2.parent = powArgument;
                        powArgument.arguments.add(node2);
                        powArgument.parent=denominator;
                        denominator.arguments.add(powArgument);
                        arcctgNode = doAssignAndRewrite(denominator,arcctgNode,index);
                        resultNode = hardArgument(node2, arcctgNode);
                        break;
                    }
                }
                break;
            }
            case sqrt: {
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        Node sqrtNode = new Node(Expressions.div);
                        sqrtNode.arguments.add(new Digit(1, sqrtNode));// 1/()
                        Node denominator = new Node(Expressions.mul); //*
                        denominator.arguments.add(new Digit(2, denominator));; //2
                        Node sqrtArgument = new Node(Expressions.sqrt); //sqrt
                        sqrtArgument.arguments.add(new Node(Expressions.x, sqrtNode));// x
                        resultNode =doMutipleAssign(denominator,sqrtArgument,sqrtNode);
                        break;
                    }
                }
                break;
            }
            case ln: {
                Node lnNode = new Node(Expressions.div);
                lnNode.arguments.add(new Digit(1, lnNode));;// 1/()
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        Node denominator = new Node(Expressions.x, lnNode); //x
                        lnNode.arguments.add(denominator);
                        previousNode = lnNode;
                        resultNode = lnNode;
                        break;
                    } else {
                        Node denominator = node2; //x
                        lnNode.arguments.add(denominator);
                        resultNode = hardArgument(node2, lnNode);
                        break;
                    }
                }
                break;
            }
            case exponent: {
                for(Node node2: node.arguments) {
                    if (node2.id == Expressions.x) {
                        Node lnNode = new Node(Expressions.pow);
                        lnNode.arguments.add(new Node(Expressions.exponent, lnNode));// e^
                        lnNode.arguments.add(new Node(Expressions.x, lnNode)); //x;
                        previousNode = lnNode;
                        resultNode = lnNode;
                        break;
                    }
                }
                break;
            }
            case log:{
                Node logNode = new Node(Expressions.div);
                logNode.arguments.add(new Digit(1, logNode));;// 1/()
                Node denominator = new Node(Expressions.mul); //*
                for(Node node2: node.arguments) {
                    if(node2.id == Expressions.a) {
                        continue;
                    }
                    if (node2.id == Expressions.x) {
                        denominator.arguments.add( new Node(Expressions.x, denominator)); //x
                        Node lnArgument = new Node(Expressions.ln); //ln
                        for(Node node3: node.arguments) {
                            if(node3.id == Expressions.a) {
                                lnArgument.arguments.add(node3);// a
                                break;
                            }
                        }
                        resultNode = finishDenominator(denominator, lnArgument, logNode);
                    } else {
                        node2.parent =denominator;
                        denominator.arguments.add(node2); //x
                        Node lnArgument = new Node(Expressions.ln); //ln
                        for(Node node3: node.arguments) {
                            if (node3.id == Expressions.a) {
                                node3.parent=denominator;
                                lnArgument.arguments.add(new Node((node3).id));// a
                                break;
                            }
                        }
                        lnArgument.parent=denominator;
                        denominator.arguments.add( lnArgument);
                        logNode.arguments.add(denominator);
                        resultNode = hardArgument(node2, logNode);
                    }
                }
                break;
            }
            case minus: {
                Node minusNode = new Node(Expressions.minus);
                if(previousNode != null) {
                    minusNode.arguments.add(previousNode);
                }
                else{
                    minusNode.arguments.add(getByInOrder(((Node) node.arguments.get(0))));
                }
                Node minusleft = getByInOrder(node.arguments.get(1));
                if(minusleft.id == Expressions.minus) {
                    minusNode.id = Expressions.plus;
                        if (minusleft.arguments.get(0) != null) {
                            Node nodeMinus = minusleft.arguments.get(0);
                            minusleft = nodeMinus;
                        }
                }
                else {
                    if ((minusleft.id == Expressions.mul && ((Node) minusleft.arguments.get(0)).id == Expressions.minus)) {
                        minusNode.id = Expressions.plus;
                        Node arg = minusleft.arguments.get(0);
                        arg.id = ((Node) ((Node) minusleft.arguments.get(0)).arguments.get(0)).id;
                        arg.arguments.set(1,((Node) ((Node) minusleft.arguments.get(0)).arguments.get(0)).arguments.get(1));
                        arg.arguments.set(0, ((Node) ((Node) minusleft.arguments.get(0)).arguments.get(0)).arguments.get(0));
                        break;
                    }
                }
                minusNode.arguments.add(minusleft);
                resultNode = minusNode;
                previousNode = minusNode;
                break;
            }
            case plus: {
                Node plusNode = new Node(Expressions.plus);
                plusNode.arguments.add(getByInOrder(node.arguments.get(0)));
                plusNode.arguments.add(getByInOrder(node.arguments.get(1)));
                resultNode = plusNode;
                previousNode = plusNode;
                break;
            }
        }

        return resultNode;
    }
    private Node hardArgument(Node node, Node leftNode) {
        Transform transform2 = new Transform();
        Node argumentNode = transform2.getByInOrder(node);
        Node mulNode = new Node(Expressions.mul);
        leftNode.parent=mulNode;
        mulNode.arguments.add(leftNode);
        argumentNode.parent=mulNode;
        mulNode.arguments.add(argumentNode);
        previousNode = mulNode;
        return mulNode;
    }
    private Node preparePow() {
        Node denominator = new Node(Expressions.pow);
        denominator.arguments.add(new Digit(2, denominator)); //  / ()^2
        return denominator;
    }
    private Node finishDenominator(Node denominator, Node argument, Node node) {
      return doMutipleAssign(denominator,argument,node);
    }
    private Node doMutipleAssign(Node nodeOne, Node nodeTwo, Node nodeThree) {
        nodeTwo.parent=nodeOne;
        nodeOne.parent=nodeThree;
        nodeOne.arguments.add(nodeTwo);
        nodeThree.arguments.add( nodeOne);
        previousNode = nodeThree;
        return nodeThree;
    }
    private Node doAssignAndRewrite( Node denominator, Node arcctgNode, int index) {
        Node argument;
        argument=arcctgNode.arguments.get(index);
        denominator.parent=argument;
        argument.arguments.add(denominator);
        arcctgNode.arguments.set(index, argument);
        return arcctgNode;
    }
    private Node prepareMinusDiv() {
        int index = 0;
        Node mathNode = new Node(Expressions.minus);
        Node div = new Node(Expressions.div, mathNode);
        mathNode.arguments.add(div);
        Node argument = div;
        argument.arguments.add(new Digit(1, argument));// 1/()
        mathNode.arguments.set(index,argument);
        return mathNode;
    }

}
