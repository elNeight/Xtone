package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.exception.GrammarException;
import interpreter.exception.IncompatibleType;
import support.Column;
import support.Row;
import support.Table;
import translator.Translator;

public class BinaryExpression extends Metainfo implements Expression {

    private final Expression firstExpression;
    private final Expression secondExpression;
    private final String operation;

    public BinaryExpression(String operation, Expression firstExpression, Expression secondExpression, Integer line) {
        super(line);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public Object eval() {

        Object first = firstExpression.eval();
        Object second = secondExpression.eval();
        if (first.getClass() != second.getClass()) throw new IncompatibleType("Incompatible types", getLine());

        Object result = null;

        if (first instanceof Double) {
            switch (operation) {
                case "+" -> result = (Double) firstExpression.eval() + (Double) secondExpression.eval();
                case "-" -> result = (Double) firstExpression.eval() - (Double) secondExpression.eval();
                case "*" -> result = (Double) firstExpression.eval() * (Double) secondExpression.eval();
                case "/" -> result = (Double) firstExpression.eval() / (Double) secondExpression.eval();
                default -> throw new GrammarException("unexpected operator", getLine());
            }
        } else if (first instanceof String) {
            if ("+".equals(operation)) {
                result = firstExpression.eval().toString() + secondExpression.eval().toString();
            } else {
                throw new GrammarException("String doesn't override" + operation + "operator", getLine());
            }
        } else if (first instanceof Table || first instanceof Column || first instanceof Row) {
            throw new GrammarException("Table doesn't override" + operation + "operator", getLine());
        }

        return result;

    }

    @Override
    public String represent(Translator translator) {
        return translator.translateBinaryExpression(firstExpression, operation, secondExpression);
    }
}
