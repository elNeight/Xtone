package interpreter.ast.expression.type;

import interpreter.ast.expression.Expression;
import translator.Translator;

public class NumberExpression implements Expression {

    private final double value;


    public NumberExpression(double value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateNumberExpression(value);
    }
}
