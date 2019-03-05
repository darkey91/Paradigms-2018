package expression;
import expression.exceptions.*;


public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int calc(int x, int y) throws CalculationException {
        if (y == 0) {
            throw new CalculationException("Division by zero\n");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new CalculationException("Overflow in division");
        }

        return x / y;
    }

    public double calc(double x, double y) {
        return x / y;
    }
}
