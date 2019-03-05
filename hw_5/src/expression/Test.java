package expression;

public class Test {
    public static void main(String[] args) {
        int h = new Subtract(
                new Multiply(
                        new Const(2),
                        new Variable("x")
                ),
                new Const(3)
        ).evaluate(5);

        System.out.println(h);

    }
}
