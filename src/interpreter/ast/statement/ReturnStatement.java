package interpreter.ast.statement;

import interpreter.ast.expression.Expression;
import translator.Translator;

public class ReturnStatement extends RuntimeException implements Statement {

    private final Expression expression;
    private Object result;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }


    public Object getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = expression.eval();
        throw this;
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateReturnStatement(expression);
    }
}
