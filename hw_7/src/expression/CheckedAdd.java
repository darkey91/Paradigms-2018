package expression;

import expression.exceptions.CalculationException;
import expression.exceptions.*;

import java.lang.Integer;

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second);
    }


    public int calc(int x, int y) throws CalculationException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new CalculationException("Overflow in addition");
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new CalculationException("Underflow in addition");
        }
        return x + y;
    }

    public double calc(double x, double y) {
        return x + y;
    }
}
