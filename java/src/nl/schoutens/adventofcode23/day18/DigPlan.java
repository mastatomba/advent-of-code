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

        int rowIndex = digSite.getStartRowIndex();
        int columnIndex = digSite.getStartColumnIndex();
        for (DigPlanRow digPlanRow : digPlanRows) {
            int newRowIndex = rowIndex + digPlanRow.getRowOffset();
            int newColumnIndex = columnIndex + digPlanRow.getColumnOffset();

            digSite.dig(
                rowIndex,
                columnIndex,
                newRowIndex,
                newColumnIndex
            );

            rowIndex = newRowIndex;
            columnIndex = newColumnIndex;
        }

        // digSite.printToConsole();

        return digSite.getCubicMeterDigged(includeInnerArea);
    }

    private DigSite createDigSite() throws Exception {
        int y = 0;
        int x = 0;
        int positiveRowIndex = 0;
        int negativeRowIndex = 0;
        int positiveColumnIndex = 0;
        int negativeColumnIndex = 0;

        for (DigPlanRow digPlanRow : this.digPlanRows) {
            if (digPlanRow.getDirection() == 'D') {
                y += digPlanRow.getNumberOfMeters();
                if (y > positiveRowIndex) {
                    positiveRowIndex = y;
                }
            } else if (digPlanRow.getDirection() == 'U') {
                y -= digPlanRow.getNumberOfMeters();
                if (y < negativeRowIndex) {
                    negativeRowIndex = y;
                }
            }
        }

        for (DigPlanRow digPlanRow : this.digPlanRows) {
            if (digPlanRow.getDirection() == 'R') {
                x += digPlanRow.getNumberOfMeters();
                if (x > positiveColumnIndex) {
                    positiveColumnIndex = x;
                }
            } else if (digPlanRow.getDirection() == 'L') {
                x -= digPlanRow.getNumberOfMeters();
                if (x < negativeColumnIndex) {
                    negativeColumnIndex = x;
                }
            }
        }

        int rowSize = positiveRowIndex - negativeRowIndex + 1;
        int columnSize = positiveColumnIndex - negativeColumnIndex + 1;
        StringGrid stringGrid = new StringGrid();
        String groundRow = ".".repeat(columnSize);

        for (int i=0; i<rowSize; i++) {
            stringGrid.addRow(groundRow);
        }

        return new DigSite(stringGrid, (0-negativeRowIndex), (0-negativeColumnIndex));
    }
}
