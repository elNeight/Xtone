package interpreter.ast.statement;

import interpreter.lib.Functions;
import interpreter.lib.Variables;
import translator.Translator;

import java.util.List;
import java.util.Map;

public class FunctionDeclarationStatement implements Statement {

    private final String type;
    private final String name;
    private final Statement body;
    private final List<Map.Entry<String, String>> arguments;

    public FunctionDeclarationStatement(String type, String name, List<Map.Entry<String, String>> arguments, Statement body) {
        this.name = name;
        this.body = body;
        this.arguments = arguments;
        this.type = type;
    }

    @Override
    public void execute() {
        Functions.addFunction(type, name, body, arguments);
        Variables.addFunction(name);
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateFunDecStatement(arguments, type, name, body);
    }
}
