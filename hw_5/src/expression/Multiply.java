package expression;

public class Multiply extends BinaryOperation {
    public Multiply(MainExpression x, MainExpression y) {
        super(x, y);
    }

    public int calc(int x, int y) {
        return x * y;
    }

    public double calc(double x, double y) {
        return x * y;
    }
}
