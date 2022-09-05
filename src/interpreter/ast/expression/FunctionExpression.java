package interpreter.ast.expression;

import interpreter.ast.Metainfo;
import interpreter.ast.statement.ReturnStatement;
import interpreter.ast.statement.Statement;
import interpreter.exception.GrammarException;
import interpreter.lib.Functions;
import interpreter.lib.Variables;
import translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression extends Metainfo implements Expression {

    private final String name;
    private final List<Expression> arguments;

    public FunctionExpression(String name, List<Expression> arguments, Integer line) {
        super(line);
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Object eval() {

        var parArgs = Functions.getArguments(name);
        List<Object> values = new ArrayList<>();
        if (parArgs.size() == arguments.size()) {
            for (Expression e : arguments)
                values.add(e.eval());
        } else {
            throw new GrammarException("Not equal count of arguments", getLine());
        }

        Statement body = Functions.getBody(name);
        String previous = Variables.getCurrentFunName();
        Variables.changeFunction(name);

        for (int i = 0; i < values.size(); i++) {
            var entry = parArgs.get(i);
            String type = entry.getKey();
            String name = entry.getValue();
            Variables.addVariable(type, name, values.get(i));
        }
        //execution
        Object result = null;

        try {
            body.execute();
        } catch (ReturnStatement returnStatement) {
            result = returnStatement.getResult();
        }

        String type = Functions.getType(name);
        checkTypeConditionals(type, result);

        Variables.cleanFunction(name);
        Variables.changeFunction(previous);

        return result;
    }

    private void checkTypeConditionals(String type, Object result) {
        if (!Variables.checkTypes(type, result))
            throw new GrammarException("return doesn't match fun type", getLine());
    }


    @Override
    public String represent(Translator translator) {
        return translator.translateFunExpression(arguments, name);
    }
}
