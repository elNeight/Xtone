package interpreter.ast.statement;

import interpreter.ast.Metainfo;
import interpreter.ast.expression.Expression;
import interpreter.exception.GrammarException;
import translator.Translator;

public class IfElseStatement extends Metainfo implements Statement {

    private final Expression ifExpression;
    private final Statement ifStatement;
    private final Statement elseStatement;

    public IfElseStatement(Expression ifExpression, Statement ifStatement, Statement elseStatement, Integer line) {
        super(line);
        this.ifExpression = ifExpression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {

        Object result = ifExpression.eval();

        if (!result.getClass().getSimpleName().equals("Boolean"))
            throw new GrammarException("Logical expression in if statement is wrong", getLine());


        if ((Boolean) result) {
            ifStatement.execute();
        } else elseStatement.execute();

    }

    @Override
    public String represent(Translator translator) {
        return translator.translateIfElseStatement(ifExpression, ifStatement, elseStatement);
    }
}
