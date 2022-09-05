package interpreter.lexer;

public record Token(TokenType type, String text, Integer line) {


    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Integer getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", line=" + line +
                '}';
    }

}
