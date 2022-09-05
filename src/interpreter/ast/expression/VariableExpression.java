package interpreter.ast.expression;

import interpreter.lib.Variables;
import translator.Translator;

public class VariableExpression implements Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Object eval() {
        return Variables.getValue(name);
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateVariableExpression(name);
    }

}
