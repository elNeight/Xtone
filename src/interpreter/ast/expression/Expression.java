package interpreter.ast.expression;

import translator.Translator;

public interface Expression {
    Object eval();

    String represent(Translator translator);
}
