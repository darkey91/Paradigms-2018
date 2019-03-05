package expression;

public class Xor extends BinaryOperation{
    public Xor(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    protected int calc(int x, int y) {
        int h = x ^ y;
        return x ^ y;
    }
}
