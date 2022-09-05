package interpreter.ast.statement;

import translator.Translator;

public interface Statement {

    void execute();

    String represent(Translator translator);

}
