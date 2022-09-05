package interpreter.ast.statement;

import interpreter.ast.expression.Expression;
import translator.Translator;

public class PrintStatement implements Statement {

    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        Object result = expression.eval();
        result = result.toString().replaceAll("\"", "");
        System.out.println(result);
    }

    @Override
    public String represent(Translator translator) {
        return translator.translatePrintStatement(expression);
    }
}
