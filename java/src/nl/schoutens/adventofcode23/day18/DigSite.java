package nl.schoutens.adventofcode23.day18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.schoutens.datatype.StringGrid;
import nl.schoutens.util.StringUtils;

public class DigSite {
    private StringGrid stringGrid;
    private int startRowIndex;
    private int startColumnIndex;

    public DigSite(
        StringGrid stringGrid,
        int startRowIndex,
        int startColumnIndex
    ) {
        this.stringGrid = stringGrid;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public int getStartColumnIndex() {
        return startColumnIndex;
    }

    public void dig(int oldRowIndex, int oldColumnIndex, int newRowIndex, int newColumnIndex) {
        // System.out.println("DigSite.dig("+oldRowIndex+","+oldColumnIndex+","+newRowIndex+","+newColumnIndex+")");

        int fromRowIndex = oldRowIndex > newRowIndex ? newRowIndex : oldRowIndex;
        int fromColumnIndex = oldColumnIndex > newColumnIndex ? newColumnIndex : oldColumnIndex;
        int toRowIndex = oldRowIndex > newRowIndex ? oldRowIndex : newRowIndex;
        int toColumnIndex = oldColumnIndex > newColumnIndex ? oldColumnIndex : newColumnIndex;

        for (int i=fromRowIndex; i<=toRowIndex; i++) {
            StringBuilder diggedRow = new StringBuilder(this.stringGrid.getRows().get(i));
            for (int j=fromColumnIndex; j<=toColumnIndex; j++) {
                diggedRow.setCharAt(j, '#');
            }
            this.stringGrid.getRows().set(i, diggedRow.toString());
        }
    }

    public int getCubicMeterDigged(boolean includeInnerArea) throws Exception {
        if (includeInnerArea) {
            this.fillInnerArea();
        }

        int count = 0;
        for (int i=0; i<stringGrid.getRowSize(); i++) {
            count += getCubicMeterDiggedInRow(stringGrid.getRows().get(i), includeInnerArea);
        }
        return count;
    }

    public void printToConsole() {
        System.out.println("Digsite (size:"+this.stringGrid.getRowSize()+"x"+this.stringGrid.getColumnSize()+") (start:"+startRowIndex+","+startColumnIndex+")");
        for (String row: this.stringGrid.getRows()) {
            System.out.println("\t"+row);
        }
    }

    private void fillInnerArea() throws Exception {
        for (int i=0; i<this.stringGrid.getRowSize(); i++) {
            this.stringGrid.getRows().set(i, removeUndiggedTerrainFromOuterbounds(this.stringGrid.getRows().get(i)));
        }
        StringGrid newStringGrid = new StringGrid();
        for (String column: this.stringGrid.getColumns()) {
            newStringGrid.addRow(removeUndiggedTerrainFromOuterbounds(column));
        }

        this.stringGrid = newStringGrid;

        // Pattern pattern = Pattern.compile("\\#[. ]*\\#");
        Pattern pattern = Pattern.compile("\\#[ .]* [ .]*\\#");
        int iteration = 0;

        while (iteration++<20 && doesGridContainPattern(pattern)) {
            System.out.println("\tIteration: " + iteration);

            for (int i=0; i<this.stringGrid.getRowSize(); i++) {
                this.stringGrid.getRows().set(i, removeUndiggedTerrainFromInnerbounds(this.stringGrid.getRows().get(i), pattern));
            }
            newStringGrid = new StringGrid();
            for (String column: this.stringGrid.getColumns()) {
                newStringGrid.addRow(removeUndiggedTerrainFromInnerbounds(column, pattern));
            }
            this.stringGrid = newStringGrid;
        }
    }

    private boolean doesGridContainPattern(Pattern pattern) {
        for (String row: stringGrid.getRows()) {
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                if (matcher.group().contains(" ") && matcher.group().contains(".")) {
                    System.out.println("\tRow matches: " + row + " ("+matcher.group()+")");

                    return true;
                }
            }
        }
        for (String column: stringGrid.getColumns()) {
            Matcher matcher = pattern.matcher(column);
            while (matcher.find()) {
                if (matcher.group().contains(" ") && matcher.group().contains(".")) {
                    System.out.println("\tColumn matches: " + column + " ("+matcher.group()+")");

                    return true;
                }
            }
        }

        System.out.println("\tNo match found in grid.");

        return false;
    }

    private String removeUndiggedTerrainFromOuterbounds(String terrain) {
        StringBuilder sbTerrain = new StringBuilder(terrain);
        for (int j=0; j<sbTerrain.indexOf("#"); j++) {
            sbTerrain.setCharAt(j, ' ');
        }
        for (int j=sbTerrain.lastIndexOf("#") + 1; j<sbTerrain.length(); j++) {
            sbTerrain.setCharAt(j, ' ');
        }
        return sbTerrain.toString();
    }

    /*
     * example input:   "  #..#.. ..##...#  "
     * expected result: "  #..#     ##...#  "
     */
    private String removeUndiggedTerrainFromInnerbounds(String terrain, Pattern pattern) {
        Matcher matcher = pattern.matcher(terrain);
        StringBuilder sbTerrain = new StringBuilder(terrain);

        while (matcher.find()) {
            if (matcher.group().contains(" ") && matcher.group().contains(".")) {
                System.out.println("\tFound: " + matcher.group());

                for (int j=matcher.start()+1; j<matcher.end()-1; j++) {
                    sbTerrain.setCharAt(j, ' ');
                }
            }
        }
        return sbTerrain.toString();
    }

    private int getCubicMeterDiggedInRow(String row, boolean includeInnerArea) {
        int count = StringUtils.countCharacterInString(row, '#');
        if (includeInnerArea) {
            count += StringUtils.countCharacterInString(row, '.');
        }

        System.out.println("getCubicMeterDiggedInRow("+row+"): "+count);

        return count;
    }
}
