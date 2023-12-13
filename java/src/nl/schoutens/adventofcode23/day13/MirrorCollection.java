package nl.schoutens.adventofcode23.day13;

import java.util.ArrayList;
import java.util.List;

/**
 * Input example 1:
 *   #.##..##.
 *   ..#.##.#.
 *   ##......#
 *   ##......#
 *   ..#.##.#.
 *   ..##..##.
 *   #.#.##.#.
 * 
 * Input example 2:
 *   #...##..#
 *   #....#..#
 *   ..##..###
 *   #####.##.
 *   #####.##.
 *   ..##..###
 *   #....#..#
 */
public class MirrorCollection {
    private List<MirrorRow> mirrorRows;

    public MirrorCollection() {
        this.mirrorRows = new ArrayList<>();
    }

    public void add(MirrorRow mirrorRow) {
        this.mirrorRows.add(mirrorRow);
    }

    /**
     * To summarize your pattern notes, add up the number of columns to the left of each vertical line of reflection; 
     * to that, also add 100 multiplied by the number of rows above each horizontal line of reflection. 
     * In the above example, the first pattern's vertical line has `5` columns to its left and the second pattern's horizontal line has `4` rows above it, a total of `405`.
     */
    public int summarize() {
        return this.getNumberOfColumnsLeftToVerticalLineOfReflection() + (100 * this.getNumberOfRowsAboveHorizontalLineOfReflection());
    }

    private int getNumberOfColumnsLeftToVerticalLineOfReflection() {
        System.out.println("\tgetNumberOfColumnsLeftToVerticalLineOfReflection");

        String mirrorColumn1 = this.getMirrorColumn(0);
        for (int i=1; i<mirrorColumn1.length(); i++) {
            String mirrorColumn2 = this.getMirrorColumn(i);

            System.out.println("\t\t"+mirrorColumn1 + " = " + mirrorColumn2);

            if (mirrorColumn1.equals(mirrorColumn2)) {
                System.out.println("Found mirror column '" + mirrorColumn1 + "' at index " + i);

                return i+1;
            }
            mirrorColumn1 = mirrorColumn2;
        }
        return 0;
    }

    private int getNumberOfRowsAboveHorizontalLineOfReflection() {
        System.out.println("\tgetNumberOfRowsAboveHorizontalLineOfReflection");

        MirrorRow mirrorRow1 = this.mirrorRows.get(0);
        for (int i=1; i<this.mirrorRows.size(); i++) {
            MirrorRow mirrorRow2 = this.mirrorRows.get(i);

            System.out.println("\t\t"+mirrorRow1 + " = " + mirrorRow2);

            if (mirrorRow1.equals(mirrorRow2)) {
                System.out.println("Found mirror row '" + mirrorRow1 + "' at index " + i);

                return i+1;
            }
            mirrorRow1 = mirrorRow2;
        }
        return 0;
    }

    private String getMirrorColumn(int columnIndex) {
        String mirrorColumn = "";
        for (MirrorRow mirrorRow : mirrorRows) {
            mirrorColumn += mirrorRow.patternAt(columnIndex);
        }
        return mirrorColumn;
    }
}
