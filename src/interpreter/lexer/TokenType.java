package interpreter.lexer;

public enum TokenType {


    //key-words

    NUM_TYPE("Num\\b"),
    STRING_TYPE("String\\b"),
    TABLE_TYPE("Table\\b"),
    COLUMN_TYPE("Column\\b"),
    ROW_TYPE("Row\\b"),

    CREATE_TABLE("newTable\\b"),
    CREATE_COLUMN("newColumn\\b"),
    CREATE_ROW("newRow\\b"),

    WHILE("while\\b"),
    FOR("for\\b"),

    IF("if\\b"),
    ELSE("else\\b"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    LT("<"),
    GT(">"),


    PRINT("print\\b"),
    RETURN("return\\b"),


    // regular tokens

    NUMBER("[0-9]+"),
    STRING_LITERAL("\"[^\"]*\""),
    WORD("[a-zA-Z]+"),

    PLUS("\\+"),
    MINUS("-"),
    STAR("\\*"),
    SLASH("/"),
    ASSIGN("\\="),

    SPACE(" +"),

    L_PAREN("\\("),
    R_PAREN("\\)"),
    L_BRACE("\\{"),
    R_BRACE("\\}"),
    L_SQUARE("\\["),
    R_SQUARE("\\]"),
    COMMA(","),
    DOT("\\."),

    SEMICOLON(";"),


    EOF(null);


    private final String regexp;

    public String getRegexp() {
        return regexp;
    }

    TokenType(String regexp) {
        this.regexp = regexp;
    }
}
