package nl.schoutens.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a grid of characters
 */
public class StringGrid {
    private List<String> rows;
    private int columnSize = 0;

    public StringGrid() {
        this.rows = new ArrayList<>();
    }

    public void addRow(String row) throws Exception {
        if (this.rows.isEmpty()) {
            this.columnSize = row.length();
        } else {
            if (row.length() != this.columnSize) {
                throw new Exception("The row has incorrect column size, it should be " + this.columnSize);
            }
        }
        this.rows.add(row);
    }

    public int getRowSize() {
        return this.rows.size();
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public List<String> getRows() {
        return this.rows;
    }

    public List<String> getColumns() {
        List<String> columns = new ArrayList<>();
        if (!this.rows.isEmpty()) {       
            for (int i=0; i<this.columnSize; i++) {
                columns.add(this.getColumn(i));
            }
        }
        return columns;
    }

    public char charAt(int rowIndex, int columnIndex) throws Exception {
        if (rowIndex >= this.rows.size() || columnIndex >= this.columnSize) {
            throw new Exception("The indexes are out of bound, grid size is " + this.rows.size() + "x" + this.columnSize);
        }
        return this.rows.get(rowIndex).charAt(columnIndex);
    }

    private String getColumn(int columnIndex) {
        String column = "";
        for (String row: this.rows) {
            column += row.charAt(columnIndex);
        }
        return column;
    }
}
