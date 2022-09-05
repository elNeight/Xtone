package interpreter.ast.statement;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import interpreter.lib.Variables;
import translator.Translator;

public class ForStatement extends Metainfo implements Statement {


    private final String type;
    private final String name;
    private final Statement assign;
    private final Expression expression;
    private final Statement change;
    private final Statement block;

    public ForStatement(String type, String name, Statement assign,
                        Expression expression, Statement change, Statement block, Integer line) {
        super(line);
        this.type = type;
        this.name = name;
        this.assign = assign;
        this.expression = expression;
        this.change = change;
        this.block = block;
    }

    @Override
    public void execute() {
        if (!type.equals("Num")) throw new GrammarException("for should have Num iterator type", getLine());

        assign.execute();

        for (Double i = (Double) Variables.getValue(name); (Boolean) expression.eval(); change.execute()) {
            block.execute();
        }
        Variables.deleteVariable(name);
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateForStatement(change, assign, expression, block);
    }
}
