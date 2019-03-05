package expression.parser;

import expression.exceptions.CalculationException;
import expression.exceptions.ParsingException;

public class Test {
    public static void main(String[] args) throws ParsingException, CalculationException {
        String exp = "-2147483648";

        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse(exp).evaluate(0,0,0));


    }
}
