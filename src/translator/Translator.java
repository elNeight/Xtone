package translator;

import interpreter.ast.expression.Expression;
import interpreter.ast.statement.Statement;
import support.Table;

import java.util.List;
import java.util.Map;

public interface Translator {

    String translateBlock(List<Statement> statements);

    String translateAssignStatement(Expression expression, String type, String variable);

    String translateForStatement(Statement statement, Statement assign, Expression expression, Statement block);

    String translateFunDecStatement(List<Map.Entry<String, String>> arguments, String type,
                                    String name, Statement body);

    String translateFunStatement(Expression expression);

    String translateIfElseStatement(Expression ifExpression, Statement ifStatement, Statement elseStatement);

    String translateLineSetStatement(String name, Expression position, Expression expression);

    String translatePrintStatement(Expression expression);

    String translateReturnStatement(Expression expression);

    String translateTableSetElExpression(String name, Expression row, Expression column, Expression expression);

    String translateWhileStatement(Expression expression, Statement statement);

    ///////expressions

    String translateColumnExpression(String name, Expression column);

    String translateNumberExpression(double value);

    String translateRowExpression(String name, Expression row);

    String translateStringExpression(String value);

    String translateTableExpression(Table table, Expression rows, Expression columns);

    String translateBinaryExpression(Expression first, String operator, Expression second);

    String translateConditionalExpression(Expression first, String operator, Expression second);

    String translateFunExpression(List<Expression> arguments, String name);

    String translateLineGetExpression(String name, Expression position);

    String translateTableGetElExpression(String name, Expression row, Expression column);

    String translateTypeCastingExpression(String type, Expression expression);

    String translateVariableExpression(String name);


}
