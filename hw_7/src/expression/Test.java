package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;

public class Test {
    public static void main(String[] args) throws CalculationException, ParsingException {
        String expression = "( 8 * 7";

        ExpressionParser parser = new ExpressionParser();

        CommonExpression yolo = parser.parse(expression);

        for (int i = 0; i < 3; i++) {
            System.out.println(yolo.evaluate(i, i, i));
        }

    }
};

