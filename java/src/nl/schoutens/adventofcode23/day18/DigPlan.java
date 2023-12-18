package nl.schoutens.adventofcode23.day18;

import java.util.ArrayList;
import java.util.List;
import nl.schoutens.datatype.StringGrid;

public class DigPlan {
    private List<DigPlanRow> digPlanRows;

    public DigPlan() {
        this.digPlanRows = new ArrayList<>();
    }

    public void add(DigPlanRow row) {
        this.digPlanRows.add(row);
    }

    public int dig(boolean includeInnerArea) throws Exception {
        DigSite digSite = this.createDigSite();

        // digSite.printToConsole();

        int rowIndex = 0;
        int columnIndex = 0;
        for (DigPlanRow digPlanRow : digPlanRows) {
            int newRowIndex = rowIndex + digPlanRow.getRowOffset();
            int newColumnIndex = columnIndex + digPlanRow.getColumnOffset();

            digSite.dig(
                rowIndex < newRowIndex ? rowIndex : newRowIndex,
                rowIndex < newRowIndex ? newRowIndex : rowIndex,
                columnIndex < newColumnIndex ? columnIndex : newColumnIndex,
                columnIndex < newColumnIndex ? newColumnIndex : columnIndex
            );

            rowIndex = newRowIndex;
            columnIndex = newColumnIndex;
        }

        digSite.printToConsole();

        return digSite.getCubicMeterDigged(includeInnerArea);
    }

    private DigSite createDigSite() throws Exception {
        StringGrid stringGrid = new StringGrid();
        int rowSize = this.getTrenchRowSize();
        int columnSize = this.getTrenchColumnSize();
        String groundRow = ".".repeat(columnSize);
        for (int i=0; i<rowSize; i++) {
            stringGrid.addRow(groundRow);
        }
        return new DigSite(stringGrid);
    }

    private int getTrenchRowSize() {
        int y = 0;
        int rowSize = 0;
        for (DigPlanRow digPlanRow : this.digPlanRows) {
            if (digPlanRow.getDirection() == 'D') {
                y += digPlanRow.getNumberOfMeters();
                if (y > rowSize) {
                    rowSize = y;
                }
            } else if (digPlanRow.getDirection() == 'U') {
                y -= digPlanRow.getNumberOfMeters();
                if (y < 0) {
                    System.err.println("Y pos less than 0??: " + y);
                }
            }
        }
        return rowSize + 1;
    }

    private int getTrenchColumnSize() {
        int x = 0;
        int columnSize = 0;
        for (DigPlanRow digPlanRow : this.digPlanRows) {
            if (digPlanRow.getDirection() == 'R') {
                x += digPlanRow.getNumberOfMeters();
                if (x > columnSize) {
                    columnSize = x;
                }
            } else if (digPlanRow.getDirection() == 'L') {
                x -= digPlanRow.getNumberOfMeters();
                if (x < 0) {
                    System.err.println("X pos less than 0??: " + x);
                }
            }
        }
        return columnSize + 1;
    }
}
