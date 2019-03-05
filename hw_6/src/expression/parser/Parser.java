package expression.parser;

import expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
   public  TripleExpression parse(String expression);
}
