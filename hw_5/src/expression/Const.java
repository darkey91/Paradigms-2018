package expression;

public class Const implements MainExpression {
    private Number value;

    public Const(Number x) {
        value = x;
    }

    public double evaluate(double x) {
        return value.doubleValue();
    }

    public int evaluate(int x) {
        return value.intValue();
    }
}
