package expression;

public class Variable implements MainExpression{
    protected String name;

    public Variable(String x) {
        name = x;
    }

    public int evaluate(int x) {
        return x;
    }

    public double evaluate(double x) {
        return x;
    }

}
