package nl.schoutens.aoc2025.day7;

import nl.schoutens.datatype.StringGrid;
import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.List;

public class AOCYear2025Day07 {

    private static final char START = 'S';
    private static final char SPLITTER = '^';
    private static final char BEAM = '|';

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day7/input.txt");

            System.out.println("Part 1: " + part1(lines, lines.get(0).indexOf(START)));
            System.out.println("Part 2: " + part2(lines, lines.get(0).indexOf(START)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<String> lines, int start) {
        var grid = createStringGrid(lines);
        var count = 0;

        grid.setCellValue(BEAM, 1, start);

        for (int row = 2; row < grid.getRowSize(); row+=2) {
            for (int col = 0; col < grid.getColumnSize(); col++) {
                if (grid.charAt(row-1, col) == BEAM) {
                    if (grid.charAt(row, col) == SPLITTER) {
                        grid.setCellValue(BEAM, row, col-1);
                        grid.setCellValue(BEAM, row, col+1);
                        grid.setCellValue(BEAM, row+1, col-1);
                        grid.setCellValue(BEAM, row+1, col+1);
                        count++;
                    }
                    else {
                        grid.setCellValue(BEAM, row, col);
                        grid.setCellValue(BEAM, row+1, col);
                    }
                }
            }
        }

        return count;
    }

    private static long part2(List<String> lines, int start) {
        var grid = createStringGrid(lines);
        var beamValues = create2DLongGrid(grid.getRowSize(), grid.getColumnSize());

        grid.setCellValue(BEAM, 1, start);
        beamValues[1][start] = 1;

        for (int row = 2; row < grid.getRowSize(); row+=2) {
            for (int col = 0; col < grid.getColumnSize(); col++) {
                if (grid.charAt(row-1, col) == BEAM) {
                    long beamValue = beamValues[row-1][col];
                    if (grid.charAt(row, col) == SPLITTER) {
                        grid.setCellValue(BEAM, row, col-1);
                        grid.setCellValue(BEAM, row, col+1);
                        grid.setCellValue(BEAM, row+1, col-1);
                        grid.setCellValue(BEAM, row+1, col+1);

                        beamValues[row][col-1] += beamValue;
                        beamValues[row][col+1] += beamValue;
                        beamValues[row+1][col-1] += beamValue;
                        beamValues[row+1][col+1] += beamValue;
                    }
                    else {
                        grid.setCellValue(BEAM, row, col);
                        grid.setCellValue(BEAM, row+1, col);

                        beamValues[row][col] += beamValue;
                        beamValues[row+1][col] += beamValue;
                    }
                }
            }
        }

        long count = 0;
        for (long beamValue: beamValues[grid.getRowSize()-1]) {
            count += beamValue;
        }

        return count;
    }

    private static StringGrid createStringGrid(List<String> lines) {
        var grid = new StringGrid();
        for (String line: lines) {
            grid.addRow(line);
        }
        return grid;
    }

    private static long[][] create2DLongGrid(int rowCount, int columnCount) {
        var grid = new long[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                grid[row][col] = 0L;
            }
        }
        return grid;
    }
}
