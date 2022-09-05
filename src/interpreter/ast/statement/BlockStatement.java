package interpreter.ast.statement;

import translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {

    private final List<Statement> statements;

    public BlockStatement() {
        this.statements = new ArrayList<>();
    }


    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void execute() {
        for (Statement s : statements)
            s.execute();
    }

    @Override
    public String represent(Translator translator) {
        return translator.translateBlock(statements);
    }
}
