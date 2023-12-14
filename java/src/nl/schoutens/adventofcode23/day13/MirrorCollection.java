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
    private int columnSize = 0;

    public MirrorCollection() {
        this.mirrorRows = new ArrayList<>();
    }

    public void add(MirrorRow mirrorRow) {
        this.mirrorRows.add(mirrorRow);
        this.columnSize = mirrorRow.getColumnSize();
    }

    /**
     * To summarize your pattern notes, add up the number of columns to the left of each vertical line of reflection; 
     * to that, also add 100 multiplied by the number of rows above each horizontal line of reflection. 
     * In the above example, the first pattern's vertical line has `5` columns to its left and the second pattern's horizontal line has `4` rows above it, a total of `405`.
     */
    public List<SummarizeResult> summarize() {
        List<SummarizeResult> summarizeResults = new ArrayList<>();
        if (mirrorRows.size() > 0) {
            List<Integer> lineIndices = this.findHorizontalReflections();
            for (Integer lineIndex : lineIndices) {
                summarizeResults.add(new SummarizeResult(true, lineIndex));
            }

            lineIndices = this.findVerticalReflections();
            for (Integer lineIndex : lineIndices) {
                summarizeResults.add(new SummarizeResult(false, lineIndex));
            }
        }
        return summarizeResults;
    }

    public SummarizeResult fixSmudgeAndSummarize() {
        SummarizeResult summarizeResult1 = this.summarize().get(0);
        System.out.println("\tOriginal result "+summarizeResult1);

        for (int rowIndex=0; rowIndex<this.mirrorRows.size(); rowIndex++) {
            for (int columnIndex=0; columnIndex<this.columnSize; columnIndex++) {
                System.out.println("\t\tChecking position "+rowIndex+","+columnIndex);

                MirrorCollection mirrorCollection = this.createCopyWithFlippedPattern(rowIndex, columnIndex);
                List<SummarizeResult> summarizeResults = mirrorCollection.summarize();
                for (SummarizeResult summarizeResult2 : summarizeResults) {
                    if (!summarizeResult1.equals(summarizeResult2)) {
                        System.out.println("\tFound smudge at "+rowIndex+","+columnIndex);
                        return summarizeResult2;
                    }
                }
            }
        }
        System.err.println("\tAnother reflection was not found..");
        return null;
    }

    private MirrorCollection createCopyWithFlippedPattern(int rowIndex, int columnIndex) {
        MirrorCollection mirrorCollection = new MirrorCollection();
        for (int i=0; i<this.mirrorRows.size(); i++) {
            MirrorRow mirrorRow = this.mirrorRows.get(i).createCopy();
            if (i==rowIndex) {
                mirrorRow.flipPattern(columnIndex);
            }
            mirrorCollection.add(mirrorRow);
        }
        return mirrorCollection;
    }

    private List<Integer> findHorizontalReflections() {
        List<Integer> lineIndices = new ArrayList<>();
        int rowIndex = 0;
        MirrorRow mirrorRow = this.mirrorRows.get(rowIndex);

        for (int i=this.mirrorRows.size()-1; i>0; i--) {
            if (mirrorRow.equals(this.mirrorRows.get(i))) {
                if (this.isCompleteHorizontalReflection(rowIndex, i)) {

                    System.out.println("\tHorizontal reflection found from "+rowIndex+" to "+i);

                    lineIndices.add((int) Math.floor((double)(i-rowIndex) / 2.0) + rowIndex);
                }
            }
        }

        rowIndex = this.mirrorRows.size()-1;
        mirrorRow = this.mirrorRows.get(rowIndex);

        for (int i=0; i<this.mirrorRows.size()-1; i++) {
            if (mirrorRow.equals(this.mirrorRows.get(i))) {
                if (this.isCompleteHorizontalReflection(i, rowIndex)) {

                    System.out.println("\tHorizontal reflection found from "+i+" to "+rowIndex);

                    lineIndices.add((int) Math.floor((double)(rowIndex-i) / 2.0) + i);
                }
            }
        }

        return lineIndices;
    }

    private List<Integer> findVerticalReflections() {
        List<Integer> lineIndices = new ArrayList<>();
        int columnIndex = 0;
        String mirrorColumn = this.getMirrorColumn(columnIndex);

        // System.out.println("\t1. Checking vertical reflection for "+mirrorColumn);

        for (int i=this.columnSize-1; i>0; i--) {
            if (mirrorColumn.equals(this.getMirrorColumn(i))) {
                if (this.isCompleteVerticalReflection(columnIndex, i)) {
                    
                    System.out.println("\tVertical reflection found from "+columnIndex+" to "+i);

                    lineIndices.add((int) Math.floor((double)(i-columnIndex) / 2.0) + columnIndex);
                }
            }
        }

        columnIndex = this.columnSize-1;
        mirrorColumn = this.getMirrorColumn(columnIndex);

        // System.out.println("\t2. Checking vertical reflection for "+mirrorColumn);

        for (int i=0; i<this.columnSize-1; i++) {
            if (mirrorColumn.equals(this.getMirrorColumn(i))) {
                if (this.isCompleteVerticalReflection(i, columnIndex)) {

                    System.out.println("\tVertical reflection found from "+i+" to "+columnIndex);

                    lineIndices.add((int) Math.floor((double)(columnIndex-i) / 2.0) + i);
                }
            }
        }

        return lineIndices;
    }

    private boolean isCompleteHorizontalReflection(int fromRowIndex, int toRowIndex) {
        // System.out.println("isCompleteHorizontalReflection("+fromRowIndex+","+toRowIndex+")");

        while (fromRowIndex < toRowIndex) {
            MirrorRow mirrorRow1 = this.mirrorRows.get(fromRowIndex++);
            MirrorRow mirrorRow2 = this.mirrorRows.get(toRowIndex--);
            if (!mirrorRow1.equals(mirrorRow2)) {
                // System.out.println("\t"+mirrorRow1+" = "+mirrorRow2+": false");

                return false;
            } else {
                // System.out.println("\t"+mirrorRow1+" = "+mirrorRow2+": true");
            }
        }
        return fromRowIndex > toRowIndex;
    }

    private boolean isCompleteVerticalReflection(int fromColumnIndex, int toColumnIndex) {
        // System.out.println("isCompleteVerticalReflection("+fromColumnIndex+","+toColumnIndex+")");

        while (fromColumnIndex < toColumnIndex) {
            String mirrorColumn1 = this.getMirrorColumn(fromColumnIndex++);
            String mirrorColumn2 = this.getMirrorColumn(toColumnIndex--);
            if (!mirrorColumn1.equals(mirrorColumn2)) {
                // System.out.println("\t"+mirrorColumn1+" = "+mirrorColumn2+": false");

                return false;
            } else {
                // System.out.println("\t"+mirrorColumn1+" = "+mirrorColumn2+": true");
            }
        }
        return fromColumnIndex > toColumnIndex;
    }

    private String getMirrorColumn(int columnIndex) {
        String mirrorColumn = "";
        for (MirrorRow mirrorRow : mirrorRows) {
            mirrorColumn += mirrorRow.patternAt(columnIndex);
        }
        return mirrorColumn;
    }
}
