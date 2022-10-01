package processor;

import factory.TranslatorFactory;
import interpreter.Interpreter;
import interpreter.ast.statement.Statement;
import interpreter.lib.Functions;
import interpreter.lib.Variables;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class TranslatableLanguageProcessor implements LanguageProcessor{

    protected final File fileToProcess;
    protected final TranslatorFactory factory;

    public TranslatableLanguageProcessor(TranslatorFactory factory, String[] args) {
        this.factory = factory;
        fileToProcess = processArgs(args);
    }

    protected abstract File processArgs(String[] args);

}
