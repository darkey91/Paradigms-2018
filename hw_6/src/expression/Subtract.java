package expression;

public class Subtract extends BinaryOperation {
    public Subtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public int calc(int x, int y) {
        return x - y;
    }
    public double calc(double x, double y) {
        return x - y;
    }

}

