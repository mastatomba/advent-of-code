package nl.schoutens.adventofcode23.day18;

import nl.schoutens.datatype.StringGrid;
import nl.schoutens.util.StringUtils;

public class DigSite {
    private StringGrid stringGrid;

    public DigSite(StringGrid stringGrid) {
        this.stringGrid = stringGrid;
    }

    public int getCubicMeterDigged(boolean includeInnerArea) {
        int count = 0;
        for (int i=0; i<stringGrid.getRowSize(); i++) {
            count += getCubicMeterDiggedInRow(stringGrid.getRows().get(i), includeInnerArea);
        }
        return count;
    }

    public void dig(int fromRowIndex, int toRowIndex, int fromColumnIndex, int toColumnIndex) {
        // System.out.println("DigSite.dig("+fromRowIndex+","+toRowIndex+","+fromColumnIndex+","+toColumnIndex+")");

        for (int i=fromRowIndex; i<=toRowIndex; i++) {
            StringBuilder diggedRow = new StringBuilder(this.stringGrid.getRows().get(i));
            for (int j=fromColumnIndex; j<=toColumnIndex; j++) {
                diggedRow.setCharAt(j, '#');
            }
            this.stringGrid.getRows().set(i, diggedRow.toString());
        }
    }

    public void printToConsole() {
        System.out.println("Digsite ("+this.stringGrid.getRowSize()+"x"+this.stringGrid.getColumnSize()+")");
        for (String row: this.stringGrid.getRows()) {
            System.out.println("\t"+row);
        }
    }

    private int getCubicMeterDiggedInRow(String row, boolean includeInnerArea) {
        if (!includeInnerArea) {
            return StringUtils.countCharacterInString(row, '#');
        }

        int count = 0;
        boolean countInner = false;
        char previousTile = '.';
        for (char tile: row.toCharArray()) {
            if (tile == '#') {
                count++;
                if (countInner) {
                    if (previousTile == '.') {
                        countInner = false;
                    }
                } else {
                    countInner = true;
                }
            } else {
                if (countInner) {
                    count++;
                }
            }
            previousTile = tile;
        }

        // System.out.println("getCubicMeterDiggedInRow("+row+"): "+count);

        return count;
    }
}
