grammar xtone;



    TYPE: 'Num'
        | 'String'
        | 'Table'
        | 'Column'
        | 'Row';

    CREATE: 'newTable'
        | 'newColumn'
        | 'newRow'

    WHILE: 'while';
    FOR: 'for';
    IF: 'if';
    ELSE: 'else';
    EQUAL: '==',
    NOT_EQUAL: '!=';
    LT: '<';
    GT: '>';

    PRINT: 'print';
    RETURN: 'return';

    NUMBER: '[0-9]+';
    STRING_LITERAL: '\'[^\']*\''
    WORD: '[a-zA-Z]+';

    PLUS: '+';
    MINUS: '-';
    STAR: '*';
    SLASH: '/';
    ASSIGN: '=';

    L_PAREN: '(';
    R_PAREN: ')';
    L_BRACE: '{';
    R_BRACE: '}';
    L_SQUARE: '[';
    R_SQUARE: ']';

    COMMA: ',';
    DOT: '.';
    SEMICOLON: ';';



    program: (functionDeclaration)* EOF;

    functionDeclaration: TYPE WORD L_PAREN (TYPE WORD COMMA)* R_PAREN block;

    statement: ifElseStatement
        | whileStatement
        | forStatement
        | assignStatement
        | returnStatement
        | functionStatement
        | printStatement;

    ifElseStatement: IF L_PAREN logicalExpression R_PAREN block ELSE block;

    whileStatement: WHILE L_PAREN logicalExpression R_Paren block;

    returnStatement: RETURN expression SEMICOLON;

    functionExpression: WORD L_PAREN (expression COMMA)* R_PAREN SEMICOLON;

    printStatement: PRINT expression SEMICOLON;

    assignStatement: TYPE WORD ASSIGN expression SEMICOLON
        | WORD ASSIGN expression SEMICOLON
        | WORD L_SQUARE expression R_SQUARE L_SQUARE expression R_SQUARE ASSIGN expression SEMICOLON
        | WORD L_SQUARE expression R_SQUARE ASSIGN expression SEMICOLON;

    block: L_BRACE (statement)* R_BRACE;

    logicalExpression: expression EQUAL expression
        | expression NOT_EQUAL expression
        | expression LT expression
        | expression GT expression;

    expression: expression PLUS term
        | expression MINUS term
        | term;

    term: term STAR unary
        | term SLASH unary;

    unary: L_PAREN TYPE R_PAREN factor;

    factor: L_PAREN expression R_PAREN
        | NUMBER
        | STRING_LITERAL
        | CREATE
        | wordCollision;

    wordCollision: WORD L_PAREN (expression COMMA)* R_PAREN
        | WORD L_SQUARE expression R_SQUARE L_SQUARE expression R_SQUARE
        | WORD L_SQUARE expression R_SQUARE
        | WORD DOT L_PAREN expression COMMA expression R_PAREN
        | WORD DOT L_PAREN expression R_PAREN
        | WORD;