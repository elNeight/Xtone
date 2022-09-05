package interpreter.ast.statement;

import interpreter.ast.expression.Expression;
import interpreter.lib.Variables;
import translator.Translator;

import java.util.Objects;

public class AssignStatement implements Statement {

    private final String type;
    private final String variable;
    private final Expression expression;

    public AssignStatement(String type, String variable, Expression expression) {
        this.type = type;
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {

        Object result = expression.eval();

        if (Objects.isNull(type))
            Variables.setValue(variable, result);
        else
            Variables.addVariable(type, variable, result);


    }

    @Override
    public String represent(Translator translator) {
        return translator.translateAssignStatement(expression, type, variable);
    }
}
