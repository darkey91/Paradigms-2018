package expression;

import expression.exceptions.CalculationException;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;

import javax.swing.*;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public int calc(int x, int y) throws CalculationException {
        if (y < 0 && x >= 0 && x - Integer.MAX_VALUE > y) {
            throw new CalculationException("Overflow in addition");
        }

        if (y > 0 && x <= 0 && Integer.MIN_VALUE - x > -y /* x - Integer.MIN_VALUE < y */) {
            throw new CalculationException("Underflow in addition");
        }
        return x - y;
    }

    public double calc(double x, double y) {
        return x - y;
    }

}
