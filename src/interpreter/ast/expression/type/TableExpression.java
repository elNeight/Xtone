package interpreter.ast.expression.type;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import support.Table;
import translator.Translator;

public class TableExpression extends Metainfo implements Expression {

    private Table table;
    private Expression rows;
    private Expression columns;

    public TableExpression(Expression rows, Expression columns, Integer line) {
        super(line);
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Object eval() {

        Double r = (Double) rows.eval();
        Double c = (Double) columns.eval();

        if (((double) r.intValue() != r) || ((double) c.intValue() != c))
            throw new GrammarException("wrong table access", getLine());

        table = new Table(r.intValue(), c.intValue());

        return table;
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateTableExpression(table, rows, columns);
    }

    @Override
    public String toString() {
        return table + "";
    }
}
