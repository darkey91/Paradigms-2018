package expression;

public class Const implements TripleExpression {
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
