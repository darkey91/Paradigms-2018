package expression;


import expression.exceptions.CalculationException;

public class Log10 extends UnaryOperation {
    public Log10(CommonExpression a) {
        super(a);
    }

    public int calc(int x) throws CalculationException{
        int res = 0;

        if(x <= 0) {
            throw new CalculationException("Osnovanie logarifma <= 0");
        }

        while (x > 0) {
            x/= 10;
            res++;
        }
        return --res;
    }
}
