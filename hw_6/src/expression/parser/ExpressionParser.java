package expression.parser;
import java.util.ArrayList;
import expression.*;
import expression.parser.Parser;

public class ExpressionParser implements Parser {
    private enum tokens {
        BEGIN, MULTIPLY, DIVIDE, PLUS, MINUS, LBRACE, RBRACE, AND, OR,
        XOR, CONSTANT, VARIABLE, UNARY_MINUS, END
    }
    ;
    private tokens cur_token;
    private int
            value;
    private int pointer = 0;
    private String expression;


    private char var_name;

    private void removeSpaces() {
        while (pointer < expression.length() && Character.isWhitespace(expression.charAt(pointer))) {
            pointer++;
        }
    }

    private void get_token() {
        removeSpaces();
        if (pointer >= expression.length()) {
            cur_token = tokens.END;
            return;
        }
        char cur_char = expression.charAt(pointer);

        switch (cur_char) {
            case '+': {
                cur_token = tokens.PLUS;
                break;
            }
            case '-': {
                if (cur_token == tokens.VARIABLE || cur_token == tokens.CONSTANT || cur_token == tokens.RBRACE) {
                    cur_token = tokens.MINUS;
                    break;
                }
                cur_token = tokens.UNARY_MINUS;
                break;
            }
            case '*': {
                cur_token = tokens.MULTIPLY;
                break;
            }
            case '/': {
                cur_token = tokens.DIVIDE;
                break;
            }
            case '&': {
                cur_token = tokens.AND;
                break;
            }
            case '^': {
                cur_token = tokens.XOR;
                break;
            }
            case '|': {
                cur_token = tokens.OR;
                break;
            }
            case '(': {
                cur_token = tokens.LBRACE;
                break;
            }
            case ')': {
                cur_token = tokens.RBRACE;
                break;
            }
            default: {
                if (Character.isDigit(cur_char)) {
                    cur_token = tokens.CONSTANT;
                    int start = pointer;

                    while (pointer < expression.length() && Character.isDigit(expression.charAt(pointer))) {
                        ++pointer;
                    }
                    if (pointer == start)
                        pointer++;

                    value = Integer.parseUnsignedInt(expression.substring(start, pointer));
                    pointer--;

                } else {
                    var_name = cur_char;
                    cur_token = tokens.VARIABLE;
                }
            }
        }


        ++pointer;
    }

    private TripleExpression getVarConst() {
        get_token();
        TripleExpression left;

        switch (cur_token) {
            case CONSTANT: {
                left = new Const(value);
                get_token();
                break;
            }
            case VARIABLE: {
                left = new Variable(Character.toString(var_name));
                get_token();
                break;
            }
            case LBRACE: {
                left = or();
                get_token();
                break;
            }
            case UNARY_MINUS: {
                left = new UnaryMinus(getVarConst());
                break;
            }
            default:
                left = new Const(0);
                //return new Variable(Character.toString(var_name));

        }
        return left;

    }


    private TripleExpression multDiv() {
        TripleExpression left = getVarConst();
        while (true) {
            switch (cur_token) {
                case MULTIPLY: {
                    left = new Multiply(left, getVarConst());
                    break;
                }
                case DIVIDE: {
                    left = new Divide(left, getVarConst());
                    break;
                }
                default:
                    return left;
            }
        }
    }

    private TripleExpression addSub() {
        TripleExpression left = multDiv();

        while (true) {
            switch (cur_token) {
                case PLUS: {
                    left = new Add(left, multDiv());
                    break;
                }
                case MINUS: {
                    left = new Subtract(left, multDiv());
                    break;
                }
                default:
                    return left;
            }
        }
    }


    private TripleExpression and() {
        TripleExpression left = addSub();
        while (true) {
            switch (cur_token) {
                case AND: {
                    left = new And(left, addSub());
                    break;
                }
                default:
                    return left;
            }
        }

    }

    private TripleExpression xor() {
        TripleExpression left = and();

        while (true) {
            switch (cur_token) {
                case XOR: {
                    left = new Xor(left, and());
                    break;
                }
                default:
                    return left;
            }
        }
    }

    private TripleExpression or() {
        TripleExpression left = xor();

        while (true) {
            switch (cur_token) {
                case OR: {
                    left = new Or(left, xor());
                    break;
                }
                default:
                    return left;
            }
        }

    }

    public ExpressionParser() {

    }

    public TripleExpression parse(String expression) {
        this.expression = expression;
        cur_token = tokens.BEGIN;
        pointer = 0;
        return or();
    }


}

