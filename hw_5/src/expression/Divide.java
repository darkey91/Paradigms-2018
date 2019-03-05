package expression;

public class Divide extends BinaryOperation {
    public Divide(MainExpression x, MainExpression y) {
        super(x, y);
    }

    protected int calc(int x, int y) {
        //assert y != 0;
        return x / y;
    }

    protected double calc(double x, double y) {
       // assert y != 0;
        return x / y;
    }
}