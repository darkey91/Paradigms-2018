package expression;

public class And extends BinaryOperation {
    public And(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calc(int x, int y) {
        return x & y;
    }
}
