package support;

import java.util.Arrays;

public class Column implements Collection {

    private final String[] column;

    public Column(String[] column) {
        this.column = column;
    }

    public String getElement(double position) {
        return column[(int) position];
    }

    public void setElement(double position, String value) {
        column[(int) position] = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(column);
    }
}
