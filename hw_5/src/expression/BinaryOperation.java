package expression;

public abstract class BinaryOperation implements MainExpression {
    private MainExpression first;
    private MainExpression second;

    public BinaryOperation(MainExpression a, MainExpression b) {
        first = a;
        second = b;
    }

    public int evaluate(int x) {
        return calc(first.evaluate(x), second.evaluate(x));
    }

    protected abstract int calc(int x, int y);

    public double evaluate(double x) {
        return calc(first.evaluate(x), second.evaluate(x));
    }
    protected abstract double calc(double x, double y);
}

