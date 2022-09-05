package interpreter.lexer;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {

    private final List<String> codeLines;
    private List<Token> tokenList = new ArrayList<>();

    public Lexer(List<String> code) {
        this.codeLines = code;
    }

    public List<Token> lexAnalyse() {

        EnumSet<TokenType> acceptableTokens =
                EnumSet.allOf(TokenType.class);

        int i;

        for (i = 0; i < codeLines.size(); i++) {

            while (codeLines.get(i).length() != 0) {
                if (!searchToken(acceptableTokens, i))
                    throw new RuntimeException("Unexpected token at line : " + (i + 1));
            }
        }

        getRidOfSpaces();

        return tokenList;
    }

    private void getRidOfSpaces() {
        tokenList = tokenList.stream().filter(t -> t.getType() != TokenType.SPACE)
                .collect(Collectors.toList());
    }

    private boolean searchToken(Iterable<TokenType> iterable, int line) {
        for (TokenType type : iterable) {
            String currentTokenRegex = "^" + type.getRegexp();
            Pattern pattern = Pattern.compile(currentTokenRegex);
            Matcher matcher = pattern.matcher(codeLines.get(line));

            if (matcher.find()) {
                String tokenValue = matcher.group();
                Token token = new Token(type, tokenValue, line + 1);
                codeLines.set(line, codeLines.get(line).substring(tokenValue.length()));
                tokenList.add(token);
                return true;
            }
        }
        return false;
    }


}
