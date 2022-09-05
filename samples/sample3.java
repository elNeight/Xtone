import support.*;

public class sample3 {
    public static Table fillTable(String s, Double n) {
        Table table = new Table(n, n);
        for (Double i = 0.0; (i).compareTo(n) < 0; i = (i + 1.0)) {
            for (Double j = 0.0; (j).compareTo(n) < 0; j = (j + 1.0)) {
                table.set(i, j, s);
            }

        }

        return table;
    }

    public static void main(String[] args) {
        Double sizeOfTable = 3.0;
        String s = (("Hello " + "World") + "!!!");
        System.out.println(fillTable(s, sizeOfTable));
    }


}