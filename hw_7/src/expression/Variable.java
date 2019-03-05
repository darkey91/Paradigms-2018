package expression;

import expression.parser.*;

public class Variable implements CommonExpression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
        }

    }
}
