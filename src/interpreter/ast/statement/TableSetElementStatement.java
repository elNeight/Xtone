package interpreter.ast.statement;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Table;
import translator.Translator;

public class TableSetElementStatement extends Metainfo implements Statement {

    private final String name;
    private final Expression row;
    private final Expression column;
    private final Expression expression;

    public TableSetElementStatement(String name, Expression row, Expression column,
                                    Expression expression, Integer line) {
        super(line);
        this.name = name;
        this.row = row;
        this.column = column;
        this.expression = expression;
    }


    @Override
    public void execute() {

        Object value = Variables.getValue(name);
        Object exp = expression.eval();

        Double r = (Double) row.eval();
        Double c = (Double) column.eval();

        if (((double) r.intValue() != r) || ((double) c.intValue() != c))
            throw new GrammarException("wrong table access", getLine());


        if (Variables.checkTypes("Table", value)
                && Variables.checkTypes("String", exp)) {

            Table result = (Table) Variables.getValue(name);
            result.set(r.intValue(), c.intValue(), (String) exp);

        } else throw new GrammarException("incompatible types for var and [] ops", getLine());

    }

    @Override
    public String represent(Translator translator) {
        return translator.translateTableSetElExpression(name, row, column, expression);
    }
}
