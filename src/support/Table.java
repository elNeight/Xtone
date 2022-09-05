package support;

import java.util.Arrays;

public class Table {

    private final String[][] table;
    private final double rows;

    public Table(double rows, double columns) {
        this.rows = rows;
        table = new String[(int) rows][];
        for (int i = 0; i < rows; i++)
            table[i] = new String[(int) columns];
    }


    public String get(double row, double column) {
        return table[(int) row][(int) column];
    }

    public void set(double row, double column, String value) {
        table[(int) row][(int) column] = value;
    }

    public Column getColumn(double column) {
        String[] result = new String[(int) rows];

        for (int i = 0; i < rows; i++) {
            result[i] = table[i][(int) column];
        }

        return new Column(result.clone());
    }

    public Row getRow(double row) {
        return new Row(table[(int) row].clone());
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (String[] string : table)
            s.append(Arrays.toString(string)).append('\n');

        return s + "";

    }
}
