package interpreter.ast.statement;

import interpreter.ast.expression.Expression;
import translator.Translator;

public class FunctionStatement implements Statement {

    private final String name;
    private final Expression expression;

    public FunctionStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void execute() {
        expression.eval();
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateFunStatement(expression);
    }
}
