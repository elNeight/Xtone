package interpreter.ast.statement;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import support.Collection;
import translator.Translator;

public class LineSetStatement extends Metainfo implements Statement {

    private final String name;
    private final Expression position;
    private final Expression expression;

    public LineSetStatement(String name, Expression position, Expression expression, Integer line) {
        super(line);
        this.name = name;
        this.position = position;
        this.expression = expression;
    }

    @Override
    public void execute() {
        Object value = Variables.getValue(name);
        Object exp = expression.eval();

        Double p = (Double) position.eval();

        if ((double) p.intValue() != p)
            throw new GrammarException("wrong table access", getLine());

        if ((Variables.checkTypes("Row", value) || Variables.checkTypes("Column", value))
                && Variables.checkTypes("String", exp)) {
            Collection result = (Collection) Variables.getValue(name);
            result.setElement(p.intValue(), (String) exp);
        } else throw new GrammarException("incompatible types for var and [] ops", getLine());
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateLineSetStatement(name, position, expression);
    }
}
