package expression.parser;

import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements Parser {
    private enum tokens {
        BEGIN, OPEN_BRACE, CLOSE_BRACE, MISTAKE,
        MULTIPLY, DIVIDE, PLUS, MINUS, AND, OR, XOR, END,
        CONSTANT, VARIABLE, UNARY_MINUS, LOG10, POW10
    }


    private boolean isOperationLast = false;
    private int psp = 0;
    private tokens cur_token;
    private int value;
    private int pointer = 0;
    private String expression;
    private char var_name;

    private void removeSpaces() {
        while (pointer < expression.length() && Character.isWhitespace(expression.charAt(pointer))) {
            pointer++;
        }
    }

    private String getNumber(int p) {
        int start = p;

        while (p < expression.length() && Character.isDigit(expression.charAt(p))) {
            ++p;
        }
        if (p == start)
            p++;

        return expression.substring(start, p);
    }

    private String makeMsg(String msg) {
        StringBuilder exceptionMsg = new StringBuilder("\n");
        exceptionMsg.append(msg);
        exceptionMsg.append(" in ");
        int len = exceptionMsg.length();
        exceptionMsg.append(expression);
        exceptionMsg.append("\n");

        for (int i = 0; i < len + pointer; ++i) {
            exceptionMsg.append(" ");
        }
        exceptionMsg.append("^");
        return exceptionMsg.toString();
    }

    private void noOperation() throws ParsingException {
        if (cur_token == tokens.CLOSE_BRACE || cur_token == tokens.VARIABLE || cur_token == tokens.CONSTANT) {
            throw new ParsingException(makeMsg("Missing operation "));
        }

    }

    private void noOperand() throws ParsingException {
        if (cur_token == tokens.OPEN_BRACE || cur_token == tokens.BEGIN || isOperationLast) {
            throw new ParsingException(makeMsg("Missing operand in "));
        }
    }

    private void get_token() throws ParsingException {
        if (cur_token == tokens.MISTAKE) {
            throw new ParsingException(makeMsg("Unidentified character"));
        }
        removeSpaces();
        if (pointer >= expression.length()) {
            cur_token = tokens.END;
            return;
        }
        char cur_char = expression.charAt(pointer);

        switch (cur_char) {
            case '+': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.PLUS;
                break;
            }
            case '-': {
                if (cur_token == tokens.VARIABLE || cur_token == tokens.CONSTANT || cur_token == tokens.CLOSE_BRACE) {
                    noOperand();
                    isOperationLast = true;
                    cur_token = tokens.MINUS;

                } else {
                    removeSpaces();
                    if (Character.isDigit(expression.charAt(pointer + 1))) {
                        String tmp = getNumber(pointer + 1);

                        try {
                            value = Integer.parseInt("-" + tmp);
                        } catch (NumberFormatException e) {
                            throw new ParsingException(makeMsg("Given overflow value in "));
                        }
                        isOperationLast = false;
                        cur_token = tokens.CONSTANT;
                        pointer += tmp.length();
                    } else {
                        isOperationLast = true;
                        cur_token = tokens.UNARY_MINUS;
                    }
                }

                break;
            }
            case '*': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.MULTIPLY;
                break;
            }
            case '/': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.DIVIDE;
                break;
            }
            case '&': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.AND;
                break;
            }
            case '^': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.XOR;
                break;
            }
            case '|': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.OR;
                break;
            }
            case '(': {
                noOperation();
                isOperationLast = false;
                psp++;
                cur_token = tokens.OPEN_BRACE;
                break;
            }
            case ')': {
                psp--;
                if (psp < 0) {
                    throw new ParsingException(makeMsg("Missing opening brace in "));
                }
                if (isOperationLast || cur_token == tokens.OPEN_BRACE) {
                    throw new ParsingException(makeMsg("Wrong expression in brace in "));
                }
                cur_token = tokens.CLOSE_BRACE;
                isOperationLast = false;
                break;
            }
            default: {
                if (Character.isDigit(cur_char)) {
                    cur_token = tokens.CONSTANT;
                    String tmp = getNumber(pointer);
                    try {
                    value = Integer.parseInt(tmp);
                    } catch (NumberFormatException e) {
                        throw new ParsingException("Overflow");
                    }
                    pointer += tmp.length() - 1;

                } else if (cur_char == 'x' || cur_char == 'y' || cur_char == 'z') {
                    var_name = cur_char;
                    cur_token = tokens.VARIABLE;
                } else if (cur_char == 'l' || cur_char == 'p') {
                    if (expression.substring(pointer, pointer + 5).equals("log10")) {
                        pointer += 4;
                        cur_token = tokens.LOG10;
                    } else if (expression.substring(pointer, pointer + 5).equals("pow10")) {
                        pointer += 4;
                        cur_token = tokens.POW10;
                    }
                } else {
                    cur_token = tokens.MISTAKE;
                    throw new ParsingException(makeMsg("Unidentified character in "));
                }
                isOperationLast = false;

            }
        }
        ++pointer;
    }

    private CommonExpression getVarConst() throws ParsingException {
        get_token();
        CommonExpression left;

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
            case OPEN_BRACE: {
                left = or();
                if (cur_token != tokens.CLOSE_BRACE) {
                    throw new ParsingException(makeMsg("Missed closing brace"));
                }
                get_token();
                break;
            }
            case UNARY_MINUS: {
                left = new CheckedNegate(getVarConst());
                break;
            }
            case LOG10: {
                left = new Log10(getVarConst());
                break;
            }
            case POW10: {
                left = new Pow10(getVarConst());
                break;
            }
            default:
                throw new ParsingException(makeMsg("Missing operand in "));
                //left = new Const(0);
                //return new Variable(Character.toString(var_name));

        }
        return left;

    }


    private CommonExpression multDiv() throws ParsingException {
        CommonExpression left = getVarConst();
        while (true) {
            switch (cur_token) {
                case MULTIPLY: {
                    left = new CheckedMultiply(left, getVarConst());
                    break;
                }
                case DIVIDE: {
                    left = new CheckedDivide(left, getVarConst());
                    break;
                }
                default:
                    return left;
            }
        }
    }

    private CommonExpression addSub() throws ParsingException {
        CommonExpression left = multDiv();

        while (true) {
            switch (cur_token) {
                case PLUS: {
                    left = new CheckedAdd(left, multDiv());
                    break;
                }
                case MINUS: {
                    left = new CheckedSubtract(left, multDiv());
                    break;
                }
                default:
                    return left;
            }
        }
    }


    private CommonExpression and() throws ParsingException {
        CommonExpression left = addSub();
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

    private CommonExpression xor() throws ParsingException {
        CommonExpression left = and();

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

    private CommonExpression or() throws ParsingException {
        CommonExpression left = xor();

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

    public CommonExpression parse(String expression) throws ParsingException {
        psp = 0;
        isOperationLast = false;
        this.expression = expression;
        cur_token = tokens.BEGIN;
        pointer = 0;
        return or();
    }


}

/*package expression.parser;

import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements Parser {
    private enum tokens {
        BEGIN, OPEN_BRACE, CLOSE_BRACE, MISTAKE,
        MULTIPLY, DIVIDE, PLUS, MINUS, AND, OR, XOR, END,
        CONSTANT, VARIABLE, UNARY_MINUS, LOG10, POW10
    }


    private boolean isOperationLast = false;
    private int psp = 0;
    private tokens cur_token;
    private long value;
    private int pointer = 0;
    private String expression;
    private char var_name;

    private void removeSpaces() {
        while (pointer < expression.length() && Character.isWhitespace(expression.charAt(pointer))) {
            pointer++;
        }
    }

    private String getNumber(int p) {
        int start = p;

        while (p < expression.length() && Character.isDigit(expression.charAt(p))) {
            ++p;
        }
        if (p == start)
            p++;

        return expression.substring(start, p);
    }

    private String makeMsg(String msg) {
        StringBuilder exceptionMsg = new StringBuilder("\n");
        exceptionMsg.append(msg);
        exceptionMsg.append(" in ");
        int len = exceptionMsg.length();
        exceptionMsg.append(expression);
        exceptionMsg.append("\n");

        for (int i = 0; i < len + pointer; ++i) {
            exceptionMsg.append(" ");
        }
        exceptionMsg.append("^");
        return exceptionMsg.toString();
    }

    private void noOperation() throws ParsingException {
        if (cur_token == tokens.CLOSE_BRACE || cur_token == tokens.VARIABLE || cur_token == tokens.CONSTANT) {
            throw new ParsingException(makeMsg("Missing operation "));
        }

    }

    private void noOperand() throws ParsingException {
        if (cur_token == tokens.OPEN_BRACE || cur_token == tokens.BEGIN || isOperationLast) {
            throw new ParsingException(makeMsg("Missing operand in "));
        }
    }

    private void get_token() throws ParsingException {
        if (cur_token == tokens.MISTAKE) {
            throw new ParsingException(makeMsg("Unidentified character"));
        }
        removeSpaces();
        if (pointer >= expression.length()) {
            cur_token = tokens.END;
            return;
        }
        char cur_char = expression.charAt(pointer);

        switch (cur_char) {
            case '+': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.PLUS;
                break;
            }
            case '-': {
                if (cur_token == tokens.VARIABLE || cur_token == tokens.CONSTANT || cur_token == tokens.CLOSE_BRACE) {
                    noOperand();
                    isOperationLast = true;
                    cur_token = tokens.MINUS;

                } else {
                    isOperationLast = false;
                    cur_token = tokens.UNARY_MINUS;
                }

                break;
            }
            case '*': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.MULTIPLY;
                break;
            }
            case '/': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.DIVIDE;
                break;
            }
            case '&': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.AND;
                break;
            }
            case '^': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.XOR;
                break;
            }
            case '|': {
                noOperand();
                isOperationLast = true;
                cur_token = tokens.OR;
                break;
            }
            case '(': {
                noOperation();
                isOperationLast = false;
                psp++;
                cur_token = tokens.OPEN_BRACE;
                break;
            }
            case ')': {
                psp--;
                if (psp < 0) {
                    throw new ParsingException(makeMsg("Missing opening brace in "));
                }
                if (isOperationLast || cur_token == tokens.OPEN_BRACE) {
                    throw new ParsingException(makeMsg("Wrong expression in brace in "));
                }
                cur_token = tokens.CLOSE_BRACE;
                isOperationLast = false;
                break;
            }
            default: {
                if (Character.isDigit(cur_char)) {
                    cur_token = tokens.CONSTANT;
                    String tmp = getNumber(pointer);
                    value = Long.parseLong(tmp);
                    pointer += tmp.length() - 1;

                } else if (cur_char == 'x' || cur_char == 'y' || cur_char == 'z') {
                    var_name = cur_char;
                    cur_token = tokens.VARIABLE;
                } else if (cur_char == 'l' || cur_char == 'p') {
                    if (expression.substring(pointer, pointer + 5).equals("log10")) {
                        pointer += 4;
                        cur_token = tokens.LOG10;
                    } else if (expression.substring(pointer, pointer + 5).equals("pow10")) {
                        pointer += 4;
                        cur_token = tokens.POW10;
                    }
                } else {
                    cur_token = tokens.MISTAKE;
                    throw new ParsingException(makeMsg("Unidentified character in "));
                }
                isOperationLast = false;

            }
        }
        ++pointer;
    }

    private CommonExpression getVarConst() throws ParsingException {
        get_token();
        CommonExpression left;

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
            case OPEN_BRACE: {
                left = or();
                if (cur_token != tokens.CLOSE_BRACE) {
                    throw new ParsingException(makeMsg("Missed closing brace"));
                }
                get_token();
                break;
            }
            case UNARY_MINUS: {
                left = new CheckedNegate(getVarConst());
                break;
            }
            case LOG10: {
                left = new Log10(getVarConst());
                break;
            }
            case POW10: {
                left = new Pow10(getVarConst());
                break;
            }
            default:
                throw new ParsingException(makeMsg("Missing operand in "));
                //left = new Const(0);
                //return new Variable(Character.toString(var_name));

        }
        return left;

    }


    private CommonExpression multDiv() throws ParsingException {
        CommonExpression left = getVarConst();
        while (true) {
            switch (cur_token) {
                case MULTIPLY: {
                    left = new CheckedMultiply(left, getVarConst());
                    break;
                }
                case DIVIDE: {
                    left = new CheckedDivide(left, getVarConst());
                    break;
                }
                default:
                    return left;
            }
        }
    }

    private CommonExpression addSub() throws ParsingException {
        CommonExpression left = multDiv();

        while (true) {
            switch (cur_token) {
                case PLUS: {
                    left = new CheckedAdd(left, multDiv());
                    break;
                }
                case MINUS: {
                    left = new CheckedSubtract(left, multDiv());
                    break;
                }
                default:
                    return left;
            }
        }
    }


    private CommonExpression and() throws ParsingException {
        CommonExpression left = addSub();
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

    private CommonExpression xor() throws ParsingException {
        CommonExpression left = and();

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

    private CommonExpression or() throws ParsingException {
        CommonExpression left = xor();

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

    public CommonExpression parse(String expression) throws ParsingException {
        psp = 0;
        isOperationLast = false;
        this.expression = expression;
        cur_token = tokens.BEGIN;
        pointer = 0;
        return or();
    }


}

*/

