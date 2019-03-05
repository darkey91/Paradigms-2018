package expression;

public class Add extends BinaryOperation {
    public Add(MainExpression a, MainExpression b) {
        super(a, b);
    }

    public int calc(int x, int y) {
        return x + y;
    }

    public double calc(double x, double y) {
        return x + y;
    }

}
