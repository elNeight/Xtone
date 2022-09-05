package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Column;
import support.Row;
import translator.Translator;

public class LineGetExpression extends Metainfo implements Expression {

    private final String name;
    private final Expression position;

    public LineGetExpression(String name, Expression position, Integer line) {
        super(line);
        this.name = name;
        this.position = position;
    }

    @Override
    public Object eval() {

        Object value = Variables.getValue(name);


        Double p = (Double) position.eval();

        if ((double) p.intValue() != p)
            throw new GrammarException("wrong table access", getLine());


        if (Variables.checkTypes("Row", value) || Variables.checkTypes("Column", value)) {

            String result = "";

            try {
                result = ((Row) value).getElement(p.intValue());
            } catch (ClassCastException e) {
                result = ((Column) value).getElement(p.intValue());
            }

            return result;

        }
        throw new GrammarException("wrong usage of var and [] operators", getLine());
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateLineGetExpression(name, position);
    }
}
