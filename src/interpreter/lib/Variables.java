package interpreter.lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variables {

    private static final Map<String, Set<Variable>> functions = new HashMap<>();

    private static Set<Variable> current;

    private static String currentName;


    public static boolean isExisted(String name) {
        return current.contains(new Variable("null", name, 0));
    }

    public static Object getValue(String name) {
        if (!isExisted(name)) throw new RuntimeException("var '" + name + "' doesn't exist");
        return current.stream().filter(v -> v.getName().equals(name))
                .findAny()
                .orElseThrow()
                .getValue();
    }

    public static String getType(String name) {
        if (!isExisted(name)) throw new RuntimeException("var '" + name + "' doesn't exist");
        return current.stream().filter(v -> v.getName().equals(name))
                .findAny()
                .orElseThrow()
                .getType();
    }

    public static void addVariable(String type, String name, Object value) {
        if (isExisted(name)) throw new RuntimeException("var '" + name + "' already exists");
        if (!checkTypes(type, value)) throw new RuntimeException("var '" + name + "' has different type");
        current.add(new Variable(type, name, value));
    }

    public static void setValue(String name, Object value) {
        if (!isExisted(name)) throw new RuntimeException("var '" + name + "' doesn't exist");
        if (!checkTypes(getType(name), value)) throw new RuntimeException("var '" + name + "' has different type");

        Variable var = current.stream().filter(v -> v.getName().equals(name))
                .findAny()
                .orElseThrow();
        var.setValue(value);
    }

    public static boolean checkTypes(String type, Object value) {

        String valueType = value.getClass().getSimpleName();
        if (valueType.equals("Double")) valueType = "Num";

        return type.equals(valueType);
    }

    public static void deleteVariable(String name) {
        current.remove(new Variable("null", name, 0));
    }

    public static void addFunction(String name) {
        functions.put(name, new HashSet<>());
        current = functions.get(name);
        currentName = name;
    }

    //think about it
    public static void changeFunction(String name) {
        current = functions.get(name);
        currentName = name;
    }

    public static void cleanFunction(String name) {
        current.clear();
    }

    public static String getCurrentFunName() {
        return currentName;
    }

    public static void clear() {
        functions.clear();
        current = null;
        currentName = null;
    }


}
