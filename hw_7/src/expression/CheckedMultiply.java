package expression;
import java.lang.Math;

import expression.exceptions.CalculationException;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int calc(int x, int y) throws CalculationException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new CalculationException("Overflow in multiplication");
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new CalculationException("Overflow in multiplication");
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new CalculationException("Underflow in multiplication");
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new CalculationException("Underflow in multiplication");

        }
        return x * y;
    }

    public double calc(double x, double y) {
        return x * y;
    }
}





