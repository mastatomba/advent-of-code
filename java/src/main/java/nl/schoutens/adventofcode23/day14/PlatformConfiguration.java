package nl.schoutens.adventofcode23.day14;

import nl.schoutens.datatype.StringGrid;

public class PlatformConfiguration {
    private StringGrid stringGrid;

    public PlatformConfiguration(StringGrid stringGrid) {
        this.stringGrid = stringGrid;
    }

    public void slideRocksNorth() throws Exception {
        StringGrid newStringGrid = new StringGrid();
        for (String column : this.stringGrid.getColumns()) {

            // System.out.print(column + " > ");

            while (column.indexOf(".O", 0) != -1) {
                column = column.replace(".O", "O.");
            }

            // System.out.println(column);

            newStringGrid.addRow(column);
        }

        this.stringGrid = new StringGrid();
        for (String column: newStringGrid.getColumns()) {
            this.stringGrid.addRow(column);
        }
    }

    public void slideRocksEast() throws Exception {
        StringGrid newStringGrid = new StringGrid();
        for (String row : this.stringGrid.getRows()) {

            // System.out.print(row + " > ");

            while (row.indexOf("O.", 0) != -1) {
                row = row.replace("O.", ".O");
            }

            // System.out.println(row);

            newStringGrid.addRow(row);
        }
        this.stringGrid = newStringGrid;
    }

    public void slideRocksSouth() throws Exception {
        StringGrid newStringGrid = new StringGrid();
        for (String column : this.stringGrid.getColumns()) {

            // System.out.print(column + " > ");

            while (column.indexOf("O.", 0) != -1) {
                column = column.replace("O.", ".O");
            }

            // System.out.println(column);

            newStringGrid.addRow(column);
        }

        this.stringGrid = new StringGrid();
        for (String column: newStringGrid.getColumns()) {
            this.stringGrid.addRow(column);
        }
    }

    public void slideRocksWest() throws Exception {
        StringGrid newStringGrid = new StringGrid();
        for (String row : this.stringGrid.getRows()) {

            // System.out.print(row + " > ");

            while (row.indexOf(".O", 0) != -1) {
                row = row.replace(".O", "O.");
            }

            // System.out.println(row);

            newStringGrid.addRow(row);
        }
        this.stringGrid = newStringGrid;
    }

    /*
     * Each cycle tilts the platform four times so that the rounded rocks roll north, then west, then south, then east.
     */
    public void runCycle() throws Exception {
        this.slideRocksNorth();
        this.slideRocksWest();
        this.slideRocksSouth();
        this.slideRocksEast();

        // System.out.println("After cycle:");
        // for (String row: this.stringGrid.getRows()) {
        //     System.out.println("\t" + row);
        // }
    }

    public long calculateLoadOfNorthBeams() {
        long load = 0;
        int rowSize = this.stringGrid.getRowSize();
        for (String row: this.stringGrid.getRows()) {
            long count = row.chars().filter(ch -> ch == 'O').count();
            load += count * rowSize--;
        }
        return load;
    }
}
