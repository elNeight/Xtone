package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.exception.IncompatibleType;
import translator.Translator;

public class TypeCastingExpression extends Metainfo implements Expression {

    private final String type;
    private final Expression expression;

    public TypeCastingExpression(String type, Expression expression, Integer line) {
        super(line);
        this.type = type;
        this.expression = expression;
    }

    @Override
    public Object eval() {


        Object result = expression.eval();

        if (type.equals("String")) {
            result = result.toString();
            return result;
        } else if (type.equals("Num") && result instanceof String) {
            double d = 0;
            try {
                String str = ((String) result).substring(1, ((String) result).length() - 1);
                d = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                throw new IncompatibleType("wrong type casting", getLine());
            }
            result = d;
            return result;
        }


        throw new IncompatibleType("wrong type during casting", getLine());


    }

    @Override
    public String represent(Translator translator) {
        return translator.translateTypeCastingExpression(type, expression);
    }
}
