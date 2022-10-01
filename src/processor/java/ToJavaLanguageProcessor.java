package processor.java;

import factory.TranslatorFactory;
import interpreter.Interpreter;
import interpreter.ast.statement.Statement;
import interpreter.lib.Functions;
import interpreter.lib.Variables;
import processor.TranslatableLanguageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ToJavaLanguageProcessor extends TranslatableLanguageProcessor {

    public ToJavaLanguageProcessor(TranslatorFactory factory, String[] args) {
        super(factory, args);
    }

    @Override
    public void process() throws IOException {

        Interpreter interpreter = new Interpreter();
        List<Statement> statements = interpreter.interpret(fileToProcess);

        StringBuilder builder = new StringBuilder();
        statements.forEach(s -> builder.append(s.represent(factory.getTranslator())).append("\n"));
        factory.getWriter().write(fileToProcess, builder.toString());

        Functions.clear();
        Variables.clear();
    }

    @Override
    protected File processArgs(String[] args) {
        return new File(args[args.length - 1]);
    }
}
