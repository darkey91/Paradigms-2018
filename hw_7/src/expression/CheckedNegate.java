package expression;

import expression.exceptions.*;

public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(CommonExpression first) {
        super(first);
    }

    protected int calc(int x) throws CalculationException {
        if (x == Integer.MIN_VALUE) {
            throw new CalculationException("Overflow in unary minus");
        }
        return -x;
    }
}


/*
package expression;

import expression.exceptions.*;

public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(CommonExpression first) {
        super(first);
    }

    protected int calc(int x) throws CalculationException {

        try {
            x = -x;
        } catch (NumberFormatException e) {
            throw new CalculationException("Given overflow value in ");
        }
        if (x == Integer.MIN_VALUE) {
            throw new CalculationException("Overflow in unary minus");
        }
        return x;
    }
}

 */