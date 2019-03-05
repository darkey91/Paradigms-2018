package expression;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(TripleExpression first) {
        super(first);
    }

    protected int calc (int x) {
        return -x;
    }
}
