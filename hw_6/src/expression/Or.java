package expression;

public class    Or extends BinaryOperation{
    public Or(TripleExpression first, TripleExpression second){
        super(first, second);
    }

    protected int calc(int x, int y) {
        return x | y;
    }
}
