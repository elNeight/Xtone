package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Table;
import translator.Translator;

public class TableGetElementExpression extends Metainfo implements Expression {

    private final String name;
    private final Expression row;
    private final Expression column;

    public TableGetElementExpression(String name, Expression row, Expression column, Integer line) {
        super(line);
        this.name = name;
        this.row = row;
        this.column = column;
    }

    @Override
    public Object eval() {

        Object value = Variables.getValue(name);

        Double r = (Double) row.eval();
        Double c = (Double) column.eval();

        if (((double) r.intValue() != r) || ((double) c.intValue() != c))
            throw new GrammarException("wrong table access", getLine());

        if (Variables.checkTypes("Table", value))
            return ((Table) value).get(r.intValue(), c.intValue());

        throw new GrammarException("wrong usage of var and [] operators", getLine());
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateTableGetElExpression(name, row, column);
    }
}
