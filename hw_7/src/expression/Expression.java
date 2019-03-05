package expression;

import expression.exceptions.*;

public interface Expression {
    public int evaluate(int x) throws CalculationException;
}
