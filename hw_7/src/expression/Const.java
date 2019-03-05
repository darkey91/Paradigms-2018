package expression;

public class Const implements CommonExpression {
    public Number constant;

    public Const(Number x) {
        constant = x;
    }

    public int evaluate(int x) {
        return constant.intValue();
    }

    public double evaluate(double x) {
        return constant.doubleValue();
    }

    public int evaluate(int x, int y, int z) {
        return constant.intValue();
    }

}


/*
 private String getNumber(int p) {
        int start = p;

        while (p < expression.length() && Character.isDigit(expression.charAt(p))) {
            ++p;
        }
        if (p == start)
            p++;

        return expression.substring(start, p);
    }
 */