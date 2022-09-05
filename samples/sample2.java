import support.*;

public class sample2 {
    public static Table getTable() {
        Table table = new Table(4.0, 4.0);
        table.set(0.0, 0.0, "AAA");
        table.set(0.0, 1.0, "BBB");
        table.set(0.0, 2.0, "AAA");
        table.set(0.0, 3.0, "AAA");
        table.set(1.0, 0.0, "AAA");
        table.set(1.0, 1.0, "AAA");
        table.set(1.0, 2.0, "BBB");
        table.set(1.0, 3.0, "AAA");
        table.set(2.0, 0.0, "BBB");
        table.set(2.0, 1.0, "AAA");
        table.set(2.0, 2.0, "AAA");
        table.set(2.0, 3.0, "BBB");
        table.set(3.0, 0.0, "BBB");
        table.set(3.0, 1.0, "AAA");
        table.set(3.0, 2.0, "AAA");
        table.set(3.0, 3.0, "AAA");
        return table;
    }

    public static void main(String[] args) {
        Double rows = 4.0;
        Double columns = 4.0;
        Table table = getTable();
        System.out.println(table);
        for (Double i = 0.0; (i).compareTo(rows) < 0; i = (i + 1.0)) {
            for (Double j = 0.0; (j).compareTo(columns) < 0; j = (j + 1.0)) {
                if ((table.get(i, j)).equals("BBB")) {
                    table.set(i, j, "AAA");
                } else {
                    table.set(i, j, "BBB");
                }

            }

        }

        System.out.println(table);
    }


}