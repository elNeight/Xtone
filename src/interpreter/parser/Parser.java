package interpreter.parser;

import interpreter.ast.expression.*;
import interpreter.ast.expression.type.*;
import interpreter.ast.statement.*;
import interpreter.exception.GrammarException;
import interpreter.lexer.Token;
import interpreter.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "", null);

    private final List<Token> tokens;
    private final int size;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public List<Statement> parse() {
        List<Statement> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(function());
        }
        return result;
    }

    private Statement function() {

        Token type = get(0);

        if (!checkTypeMatch())
            throw new GrammarException("function is declared with wrong return type", type.getLine());

        Token name = consume(TokenType.WORD);
        consume(TokenType.L_PAREN);

        List<Map.Entry<String, String>> arguments = new ArrayList<>();

        while (!match(TokenType.R_PAREN)) {
            Token argType = get(0);
            Token argName;
            if (checkTypeMatch()) {
                argName = consume(TokenType.WORD);
                arguments.add(Map.entry(argType.getText(), argName.getText()));
                if (match(TokenType.R_PAREN)) break;
                consume(TokenType.COMMA);
                if (match(TokenType.R_PAREN)) throw new GrammarException("comma before )", argType.getLine());
                continue;
            }
            throw new GrammarException("Wrong type in function", argType.getLine());
        }


        Statement body = block();

        return new FunctionDeclarationStatement(type.getText(), name.getText(), arguments, body);
    }

    private Statement block() {
        BlockStatement block = new BlockStatement();
        consume(TokenType.L_BRACE);
        while (!match(TokenType.R_BRACE)) {
            block.add(statement());
        }


        return block;
    }

    private Statement statement() {
        Statement statement;
        Token current = get(0);

        if (match(TokenType.IF))
            return ifElseStatement();
        else if (match(TokenType.WHILE))
            return whileStatement();
        else if (match(TokenType.FOR))
            return forStatement();


        if (match(TokenType.PRINT))
            statement = printStatement();
        else if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.L_PAREN))
            statement = funStatement();
        else if (match(TokenType.RETURN))
            statement = returnStatement();
        else
            statement = assignStatement();
        if (!match(TokenType.SEMICOLON)) throw new GrammarException("';' is forgotten", get(0).getLine());
        return statement;
    }

    private Statement forStatement() {
        consume(TokenType.L_PAREN);
        Token type = get(0);
        Token name = get(1);
        Statement assign = assignStatement();
        consume(TokenType.SEMICOLON);
        Expression expression = logicalExpression();
        consume(TokenType.SEMICOLON);
        Statement change = assignStatement();
        consume(TokenType.R_PAREN);
        Statement block = block();
        return new ForStatement(type.getText(), name.getText(), assign, expression, change, block, type.getLine());
    }

    private Statement whileStatement() {
        consume(TokenType.L_PAREN);
        Token expToken = get(0);
        Expression whileExpression = logicalExpression();
        consume(TokenType.R_PAREN);
        Statement whileStatement = block();
        return new WhileStatement(whileExpression, whileStatement, expToken.getLine());
    }

    private Statement ifElseStatement() {
        consume(TokenType.L_PAREN);
        Token expToken = get(0);
        Expression ifExpression = logicalExpression();
        consume(TokenType.R_PAREN);
        Statement ifStatement = block();
        consume(TokenType.ELSE);
        Statement elseStatement = block();

        return new IfElseStatement(ifExpression, ifStatement, elseStatement, expToken.getLine());
    }

    private Statement returnStatement() {
        return new ReturnStatement(expression());
    }

    private Statement funStatement() {
        Token name = consume(TokenType.WORD);
        Expression expression = funExpression(name);
        return new FunctionStatement(name.getText(), expression);
    }

    private Statement printStatement() {
        return new PrintStatement(expression());
    }

    private Statement assignStatement() {

        Token current = get(0);
        Token next = get(1);

        if (checkTypeMatch()) {
            if (match(TokenType.WORD) && match(TokenType.ASSIGN)) {
                String type = current.getText();
                String variable = next.getText();
                return new AssignStatement(type, variable, expression());
            }
        } else if (match(TokenType.WORD)) {

            if (match(TokenType.ASSIGN)) {
                String variable = current.getText();
                return new AssignStatement(null, variable, expression());
            } else if (match(TokenType.L_SQUARE)) {
                Expression position = expression();
                consume(TokenType.R_SQUARE);

                if (match(TokenType.L_SQUARE)) {
                    Expression column = expression();
                    consume(TokenType.R_SQUARE);
                    consume(TokenType.ASSIGN);

                    return new TableSetElementStatement(current.getText(), position, column, expression(), current.getLine());
                }
                consume(TokenType.ASSIGN);

                return new LineSetStatement(current.getText(), position, expression(), current.getLine());

            }


        }
        throw new GrammarException("wrong assign statement", get(0).getLine());
    }

    private Expression expression() {
        return addition();
    }

    private Expression logicalExpression() {
        Token expToken = get(0);
        Expression result = multiplication();

        while (true) {
            if (match(TokenType.EQUAL)) {
                result = new ConditionalExpression("==", result, addition(), expToken.getLine());
                continue;
            } else if (match(TokenType.NOT_EQUAL)) {
                result = new ConditionalExpression("!=", result, addition(), expToken.getLine());
                continue;
            } else if (match(TokenType.LT)) {
                result = new ConditionalExpression("<", result, addition(), expToken.getLine());
                continue;
            } else if (match(TokenType.GT)) {
                result = new ConditionalExpression(">", result, addition(), expToken.getLine());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression addition() {

        Token expToken = get(0);
        Expression result = multiplication();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression("+", result, multiplication(), expToken.getLine());
                continue;
            } else if (match(TokenType.MINUS)) {
                result = new BinaryExpression("-", result, multiplication(), expToken.getLine());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplication() {

        Token expToken = get(0);
        Expression result = typeCasting();

        while (true) {
            if (match(TokenType.STAR)) {
                result = new BinaryExpression("*", result, typeCasting(), expToken.getLine());
                continue;
            } else if (match(TokenType.SLASH)) {
                result = new BinaryExpression("/", result, typeCasting(), expToken.getLine());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression typeCasting() {
        Expression expression;
        if (!(lookMatch(0, TokenType.L_PAREN) && lookMatch(1, TokenType.L_PAREN))) {
            if (match(TokenType.L_PAREN)) {
                Token current = get(0);
                if (checkTypeMatch() && match(TokenType.R_PAREN)) {
                    expression = new TypeCastingExpression(current.getText(), primary(), current.getLine());
                } else throw new GrammarException("wrong type casting syntax", current.getLine());
                return expression;
            }
        }
        return primary();
    }

    private Expression primary() {
        Token current = get(0);
        if (match(TokenType.NUMBER))
            return new NumberExpression(Double.parseDouble(current.getText()));
        else if (match(TokenType.CREATE_TABLE))
            return createTable();
        else if (match(TokenType.STRING_LITERAL))
            return new StringExpression(current.getText());
        else if (match(TokenType.L_PAREN)) {
            Expression result = expression();
            match(TokenType.R_PAREN);
            return result;
        } else if (match(TokenType.WORD))
            return wordCollision(current);
        throw new GrammarException("Unknown expression", get(0).getLine());

    }

    private Expression wordCollision(Token current) {

        if (match(TokenType.L_SQUARE)) {

            Expression position = expression();
            consume(TokenType.R_SQUARE);

            if (match(TokenType.L_SQUARE)) {
                Expression column = expression();
                consume(TokenType.R_SQUARE);

                return new TableGetElementExpression(current.getText(), position, column, current.getLine());
            }

            return new LineGetExpression(current.getText(), position, current.getLine());

        } else if (match(TokenType.DOT)) {
            if (match(TokenType.CREATE_COLUMN)) {
                consume(TokenType.L_PAREN);
                Expression column = expression();
                consume(TokenType.R_PAREN);

                return new ColumnExpression(current.getText(), column, current.getLine());

            } else if (match(TokenType.CREATE_ROW)) {
                consume(TokenType.L_PAREN);
                Expression row = expression();
                consume(TokenType.R_PAREN);

                return new RowExpression(current.getText(), row, current.getLine());
            }

            throw new GrammarException("unknown method", current.getLine());

        } else if (lookMatch(0, TokenType.L_PAREN)) {
            return funExpression(current);
        } else
            return new VariableExpression(current.getText());

    }

    private Expression funExpression(Token name) {
        consume(TokenType.L_PAREN);

        List<Expression> arguments = new ArrayList<>();

        while (!match(TokenType.R_PAREN)) {
            arguments.add(expression());
            if (match(TokenType.R_PAREN)) break;
            consume(TokenType.COMMA);
            if (match(TokenType.R_PAREN)) throw new GrammarException("comma before )", get(0).getLine());
        }

        return new FunctionExpression(name.getText(), arguments, get(0).getLine());
    }

    private Expression createTable() {
        Expression rows, columns;
        consume(TokenType.L_PAREN);

        rows = expression();
        consume(TokenType.COMMA);

        columns = expression();
        consume(TokenType.R_PAREN);

        return new TableExpression(rows, columns, get(0).getLine());

    }

    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) throw new RuntimeException("Token " + current + " doesn't match " + type);
        position++;
        return current;
    }

    private boolean match(TokenType type) {
        Token current = get(0);
        if (type != current.getType()) return false;
        position++;
        return true;
    }

    private Token get(int relativePosition) {
        int position = this.position + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private boolean checkTypeMatch() {
        return match(TokenType.NUM_TYPE) || match(TokenType.STRING_TYPE)
                || match(TokenType.TABLE_TYPE) || match(TokenType.COLUMN_TYPE)
                || match(TokenType.ROW_TYPE);
    }

}
