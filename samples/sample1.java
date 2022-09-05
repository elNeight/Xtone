import support.*;

public class sample1 {
    public static Row fillRow(String firstName, String lastName, String age, Table table, Double row) {
        table.set(row, 0.0, firstName);
        table.set(row, 1.0, lastName);
        table.set(row, 2.0, age);
        return table.getRow(row);
    }

    public static void main(String[] args) {
        Double rows = 3.0;
        Double columns = 3.0;
        Table table = new Table(rows, columns);
        Row firstRow = fillRow("Petr", "Petrov", "25", table, (rows - 3.0));
        Row secondRow = fillRow("Ivan", "Ivanov", "23", table, (rows - 2.0));
        Row thirdRow = fillRow("Sergey", "Sergeev", "21", table, (rows - 1.0));
        Double iterator = columns;
        while (!(iterator).equals(0.0)) {
            System.out.println(((((firstRow.getElement((iterator - 1.0)) + "   ") + secondRow.getElement((iterator - 1.0))) + "   ") + thirdRow.getElement((iterator - 1.0))));
            iterator = (iterator - 1.0);
        }

        System.out.println("");
        System.out.println("|||------------------------------|||");
        System.out.println("");
        while (!(iterator).equals(3.0)) {
            System.out.println(((("" + (iterator + 1.0)) + " : ") + ("" + table.getColumn(iterator))));
            iterator = (iterator + 1.0);
        }

    }


}