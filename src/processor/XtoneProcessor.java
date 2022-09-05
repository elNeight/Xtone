package processor;

import interpreter.Interpreter;
import interpreter.ast.statement.Statement;
import interpreter.lib.Functions;
import interpreter.lib.Variables;
import output.Writer;
import output.java.JavaWriter;
import translator.Translator;
import translator.java.JavaTranslator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XtoneProcessor implements LanguageProcessor {

    private final Interpreter interpreter;
    private Translator translator;
    private boolean translateToFile;
    private final File fileToProcess;

    public XtoneProcessor(String[] args) {
        fileToProcess = processArgs(args);
        interpreter = new Interpreter();
    }

    public void process() throws IOException {

        List<Statement> statements = interpreter.interpret(fileToProcess);

        if (translateToFile) {
            translator = new JavaTranslator();

            StringBuilder builder = new StringBuilder();
            statements.forEach(s -> builder.append(s.represent(translator)).append("\n"));

            Writer writer = new JavaWriter();
            writer.write(fileToProcess, builder.toString());
        }

        Functions.clear();
        Variables.clear();
    }

    private File processArgs(String[] args) {
        translateToFile = args[0].equals("-t");
        return new File(args[args.length - 1]);
    }

}
