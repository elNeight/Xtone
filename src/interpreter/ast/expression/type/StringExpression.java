package interpreter.ast.expression.type;

import interpreter.ast.expression.Expression;
import translator.Translator;

public class StringExpression implements Expression {

    private final String value;

    public StringExpression(String value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateStringExpression(value);
    }

}
