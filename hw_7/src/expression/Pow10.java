package expression;

import expression.exceptions.CalculationException;

public class Pow10 extends UnaryOperation {
    int p = 10;
    public Pow10(CommonExpression a) {
        super(a);
    }

    public int calc(int x) throws CalculationException {
        if (x < 0) {
            throw new CalculationException("Power must be positive value");
        }
        if (x >= 10) {
            throw new CalculationException("Power is too big");

        }

       int res = 1;
        for (int tmp = p; x > 0; x >>= 1, tmp = (tmp * tmp)) {
            if ((x & 1) != 0) {
                if (Integer.MAX_VALUE / res < tmp) {
                    throw new CalculationException("Overflow in exponentiation");
                }
                res = (res * tmp);
            }
            /*
            if (Integer.MAX_VALUE / tmp < tmp) {
                throw new CalculationException("Overflow in exponentiation");
            }
        */
        }

       return res;
    }


}
