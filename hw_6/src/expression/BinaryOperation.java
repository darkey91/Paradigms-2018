package expression;

public abstract class BinaryOperation implements TripleExpression {
    private TripleExpression firstExpression;
    private TripleExpression secondExpression;

    public BinaryOperation(TripleExpression first, TripleExpression second) {
        firstExpression = first;
        secondExpression = second;
    }

    public int evaluate(int x, int y, int z) {
        return calc(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
    protected abstract int calc(int x, int y);

}
