package interpreter.ast.expression.type;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Table;
import translator.Translator;

public class RowExpression extends Metainfo implements Expression {

    private final String name;
    private final Expression row;


    public RowExpression(String name, Expression row, Integer line) {
        super(line);
        this.name = name;
        this.row = row;
    }

    @Override
    public Object eval() {
        Object value = Variables.getValue(name);

        Double r = (Double) row.eval();

        if ((double) r.intValue() != r)
            throw new GrammarException("wrong table access", getLine());

        if (Variables.checkTypes("Table", value)) {
            return ((Table) value).getRow(r.intValue());
        }

        throw new GrammarException("wrong usage of var and [] operators", getLine());
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateRowExpression(name, row);
    }

}
