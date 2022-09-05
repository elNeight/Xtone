package translator.java;

import interpreter.ast.expression.Expression;
import interpreter.ast.statement.Statement;
import support.Table;
import translator.Translator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JavaTranslator implements Translator {

    @Override
    public String translateBlock(List<Statement> statements) {

        StringBuilder builder = new StringBuilder();

        builder.append("{").append("\n");

        for (Statement s : statements)
            builder.append(s.represent(this)).append("\n");

        builder.append("}").append("\n");

        return builder.toString();
    }

    @Override
    public String translateAssignStatement(Expression expression, String type, String variable) {
        String targetType;

        if (Objects.isNull(type))
            targetType = "";
        else {

            if (type.equals("Num"))
                targetType = "Double ";
            else
                targetType = type + " ";
        }


        return targetType + variable + " = " + expression.represent(this) + ";";
    }

    @Override
    public String translateForStatement(Statement change, Statement assign, Expression expression, Statement block) {
        String chg = change.represent(this);

        return "for(" + assign.represent(this) + " " + expression.represent(this) + "; "
                + chg.substring(0, chg.length() - 1) + ")\n"
                + block.represent(this);
    }

    @Override
    public String translateFunDecStatement(List<Map.Entry<String, String>> arguments, String type,
                                           String name, Statement body) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arguments.size(); i++) {

            String targetType = null;
            if (arguments.get(i).getKey().equals("Num"))
                targetType = "Double";
            else targetType = arguments.get(i).getKey();

            builder.append(targetType)
                    .append(" ")
                    .append(arguments.get(i).getValue());

            if (i != arguments.size() - 1)
                builder.append(", ");

        }

        String t = null;
        if (type.equals("Num"))
            t = "Double";
        else t = type;

        String n = null;

        if (name.equals("main")) {
            t = "void";
            builder = new StringBuilder();
            builder.append("String [] args");
        }


        return "public static " + t + " " + name + "(" + builder + ") " + body.represent(this);
    }

    @Override
    public String translateFunStatement(Expression expression) {
        return expression.represent(this) + ";";
    }

    @Override
    public String translateIfElseStatement(Expression ifExpression, Statement ifStatement, Statement elseStatement) {
        return "if (" + ifExpression.represent(this) + ")\n"
                + ifStatement.represent(this) + "else\n" + elseStatement.represent(this);
    }

    @Override
    public String translateLineSetStatement(String name, Expression position, Expression expression) {
        return name + ".setElement(" + position.represent(this)
                + ", " + expression.represent(this) + ");";
    }

    @Override
    public String translatePrintStatement(Expression expression) {
        return "System.out.println(" + expression.represent(this) + ");";
    }

    @Override
    public String translateReturnStatement(Expression expression) {
        return "return " + expression.represent(this) + ";";
    }

    @Override
    public String translateTableSetElExpression(String name, Expression row, Expression column, Expression expression) {
        return name + ".set(" + row.represent(this) + ", "
                + column.represent(this) + ", " + expression.represent(this) + ");";
    }

    @Override
    public String translateWhileStatement(Expression expression, Statement statement) {
        return "while (" + expression.represent(this) + ")\n" + statement.represent(this);
    }

    @Override
    public String translateColumnExpression(String name, Expression column) {
        return name + ".getColumn(" + column.represent(this) + ")";
    }

    @Override
    public String translateNumberExpression(double value) {
        return value + "";
    }

    @Override
    public String translateRowExpression(String name, Expression row) {
        return name + ".getRow(" + row.represent(this) + ")";
    }

    @Override
    public String translateStringExpression(String value) {
        return value + "";
    }

    @Override
    public String translateTableExpression(Table table, Expression rows, Expression columns) {
        return "new Table(" + rows.represent(this) + ", " + columns.represent(this) + ")";
    }

    @Override
    public String translateBinaryExpression(Expression first, String operator, Expression second) {
        return "(" + first.represent(this) + " " + operator + " " + second.represent(this) + ")";
    }

    @Override
    public String translateConditionalExpression(Expression first, String operator, Expression second) {

        String result;

        switch (operator) {
            case "==" -> result = "(" + first.represent(this)
                    + ").equals(" + second.represent(this) + ")";
            case "!=" -> result = "!(" + first.represent(this)
                    + ").equals(" + second.represent(this) + ")";
            case ">" -> result = "(" + first.represent(this)
                    + ").compareTo(" + second.represent(this) + ") > 0";
            case "<" -> result = "(" + first.represent(this)
                    + ").compareTo(" + second.represent(this) + ") < 0";
            default -> result = "(" + first.represent(this)
                    + ").equals(" + second.represent(this) + ")";
        }

        return result;
    }

    @Override
    public String translateFunExpression(List<Expression> arguments, String name) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < arguments.size(); i++) {
            builder.append(arguments.get(i).represent(this));

            if (i != arguments.size() - 1)
                builder.append(", ");

        }

        return name + "(" + builder + ")";
    }

    @Override
    public String translateLineGetExpression(String name, Expression position) {
        return name + ".getElement(" + position.represent(this) + ")";
    }

    @Override
    public String translateTableGetElExpression(String name, Expression row, Expression column) {
        return name + ".get(" + row.represent(this) + ", " + column.represent(this) + ")";
    }

    @Override
    public String translateTypeCastingExpression(String type, Expression expression) {

        if (type.equals("String"))
            return "(\"\" + " + expression.represent(this) + ")";
        else
            return "(Double.parseDouble(" + expression.represent(this) + "))";

    }

    @Override
    public String translateVariableExpression(String name) {
        return name;
    }


}
