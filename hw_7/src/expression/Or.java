package expression;

public class Or extends BinaryOperation{
    public Or(CommonExpression first, CommonExpression second){
        super(first, second);
    }

    protected int calc(int x, int y) {
        return x | y;
    }
}
