package expression;

import expression.parser.ExpressionParser;

public class Test {
    public static void main(String[] args) {
        ExpressionParser j = new ExpressionParser();
        TripleExpression k = j.parse("(- - - x^1883669513)|(- x^1681810605)");
       int b =  k.evaluate(0,0,0);
       System.out.print(b);
    }

}
