package interpreter.ast.statement;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import translator.Translator;

public class WhileStatement extends Metainfo implements Statement {

    private final Expression whileExpression;
    private final Statement whileStatement;


    public WhileStatement(Expression whileExpression, Statement whileStatement, Integer line) {
        super(line);
        this.whileExpression = whileExpression;
        this.whileStatement = whileStatement;
    }

    @Override
    public void execute() {
        Object result = whileExpression.eval();

        if (!result.getClass().getSimpleName().equals("Boolean"))
            throw new GrammarException("Logical expression in if statement is wrong", getLine());

        while ((Boolean) whileExpression.eval()) {
            whileStatement.execute();
        }


    }

    @Override
    public String represent(Translator translator) {
        return translator.translateWhileStatement(whileExpression, whileStatement);
    }
}
