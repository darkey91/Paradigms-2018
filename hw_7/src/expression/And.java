package expression;

public class And extends BinaryOperation {
    public And(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int calc(int x, int y) {
        return x & y;
    }
}
