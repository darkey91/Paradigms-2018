package expression;

public abstract class UnaryOperation implements TripleExpression {
    private TripleExpression first;

    public UnaryOperation (TripleExpression first) {
        this.first = first;
    }

    public int evaluate(int x, int y, int z) {
        return calc(first.evaluate(x, y, z));
    }
    protected abstract int calc(int x);
}
