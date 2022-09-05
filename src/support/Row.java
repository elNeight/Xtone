package support;

import java.util.Arrays;

public class Row implements Collection {

    private final String[] row;

    public Row(String[] row) {
        this.row = row;
    }

    public String getElement(double position) {
        return row[(int) position];
    }

    public void setElement(double position, String value) {
        row[(int) position] = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(row);
    }
}
