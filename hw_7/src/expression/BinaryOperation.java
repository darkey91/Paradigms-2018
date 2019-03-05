package expression;

import expression.exceptions.*;

public abstract class BinaryOperation implements CommonExpression {
    private CommonExpression firstExpression;
    private CommonExpression secondExpression;

    public BinaryOperation(CommonExpression first, CommonExpression second) {
        firstExpression = first;
        secondExpression = second;
    }

    public int evaluate(int x) throws  CalculationException {
        return calc(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    public int evaluate(int x, int y, int z) throws CalculationException{
        return calc(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
    protected abstract int calc(int x, int y) throws CalculationException;
}
