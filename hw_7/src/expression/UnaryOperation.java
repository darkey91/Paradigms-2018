package expression;
import expression.exceptions.*;

public abstract class UnaryOperation implements CommonExpression {
    private CommonExpression first;

    public UnaryOperation(CommonExpression first) {
        this.first = first;
    }

    public int evaluate(int x) throws CalculationException {
        return calc(first.evaluate(x));
    }
    public int evaluate(int x, int y, int z) throws CalculationException {
        return calc(first.evaluate(x, y, z));
    }

    protected abstract int calc(int x) throws CalculationException;
}
