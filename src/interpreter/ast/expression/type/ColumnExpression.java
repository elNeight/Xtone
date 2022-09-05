package interpreter.ast.expression.type;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Table;
import translator.Translator;

public class ColumnExpression extends Metainfo implements Expression {

    private final String name;
    private final Expression column;

    public ColumnExpression(String name, Expression column, Integer line) {
        super(line);
        this.name = name;
        this.column = column;
    }

    @Override
    public Object eval() {
        Object value = Variables.getValue(name);

        Double c = (Double) column.eval();

        if ((double) c.intValue() != c)
            throw new GrammarException("wrong table access", getLine());

        if (Variables.checkTypes("Table", value)) {
            return ((Table) value).getColumn(c.intValue());
        }

        throw new GrammarException("wrong usage of var and [] operators", getLine());
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateColumnExpression(name, column);
    }
}
