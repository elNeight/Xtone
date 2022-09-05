package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.exception.GrammarException;
import interpreter.exception.IncompatibleType;
import support.Column;
import support.Row;
import support.Table;
import translator.Translator;

public class ConditionalExpression extends Metainfo implements Expression {
    private final Expression firstExpression;
    private final Expression secondExpression;
    private final String operation;


    public ConditionalExpression(String operation, Expression firstExpression, Expression secondExpression, Integer line) {
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

        if (first instanceof Double || first instanceof String) {
            switch (operation) {
                case "==" -> result = (firstExpression.eval()).equals(secondExpression.eval());
                case "!=" -> result = !(firstExpression.eval()).equals(secondExpression.eval());
                case ">" ->
                        result = ((Comparable) firstExpression.eval()).compareTo((Comparable) secondExpression.eval()) > 0;
                case "<" ->
                        result = ((Comparable) firstExpression.eval()).compareTo((Comparable) secondExpression.eval()) < 0;
                default -> throw new GrammarException("unexpected operator", getLine());
            }
        } else if (first instanceof Table || first instanceof Column || first instanceof Row) {
            throw new GrammarException("Table doesn't override" + operation + "operator", getLine());
        }

        return result;

    }

    @Override
    public String represent(Translator translator) {
        return translator.translateConditionalExpression(firstExpression, operation, secondExpression);
    }
}
