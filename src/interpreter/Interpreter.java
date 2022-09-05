package interpreter;

import interpreter.ast.statement.Statement;
import interpreter.lexer.Lexer;
import interpreter.lexer.Token;
import interpreter.lib.Functions;
import interpreter.lib.Variables;
import interpreter.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Interpreter {

    public List<Statement> interpret(File file) throws IOException {

        if (!file.getName().endsWith(".ns"))
            throw new IOException("Wrong file extension");

        List<String> lines = readFile(file);
        List<Token> tokens = lexAnalyse(lines);
        List<Statement> statements = parse(tokens);

        execute();

        return statements;
    }

    private List<String> readFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNext())
            lines.add(scanner.nextLine());

        return lines;
    }

    private List<Token> lexAnalyse(List<String> lines) {
        Lexer lexer = new Lexer(lines);
        return lexer.lexAnalyse();
    }

    private List<Statement> parse(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();
        statements.forEach(Statement::execute);

        return statements;
    }

    private void execute() {

        Statement main = Functions.getBody("main");

        if (!Objects.isNull(main))
            Variables.changeFunction("main");
        else {
            System.out.println("main doesn't exist");
            return;
        }

        System.out.println("\n\n\n /-------/ \n\n\n");
        main.execute();
        System.out.println("\n\n\n /-------/ \n\n\n");

    }

}