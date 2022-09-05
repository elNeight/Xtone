package interpreter.lib;

import interpreter.ast.statement.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Functions {

    private static final Map<String, Statement> bodies = new HashMap<>();
    private static final Map<String, List<Map.Entry<String, String>>> arguments = new HashMap<>();
    private static final Map<String, String> returnTypes = new HashMap<>();


    public static boolean isExisted(String name) {
        return bodies.containsKey(name);
    }

    public static void addFunction(String type, String name, Statement body, List<Map.Entry<String, String>> parArguments) {
        if (isExisted(name)) throw new RuntimeException("function '" + name + "' already exists");
        arguments.put(name, parArguments);
        bodies.put(name, body);
        returnTypes.put(name, type);
    }

    public static Statement getBody(String name) {
        if (!isExisted(name)) throw new RuntimeException("fun '" + name + "' doesn't exist");
        return bodies.get(name);
    }

    public static List<Map.Entry<String, String>> getArguments(String name) {
        if (!isExisted(name)) throw new RuntimeException("fun '" + name + "' doesn't exist");
        return arguments.get(name);
    }

    public static String getType(String name) {
        if (!isExisted(name)) throw new RuntimeException("fun '" + name + "' doesn't exist");
        return returnTypes.get(name);
    }

    public static void clear() {
        bodies.clear();
        arguments.clear();
        returnTypes.clear();
    }
}
