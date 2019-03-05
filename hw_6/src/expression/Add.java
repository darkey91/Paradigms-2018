package expression;

public class Add extends BinaryOperation {
    public Add(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public int calc(int x, int y) {
        return x + y;
    }
    public double calc(double x, double y) {
        return x + y;
    }
}
