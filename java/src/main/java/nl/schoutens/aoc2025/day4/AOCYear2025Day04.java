package nl.schoutens.aoc2025.day4;

import nl.schoutens.datatype.StringGrid;
import nl.schoutens.datatype.Cell;
import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.List;
import java.util.ArrayList;

public class AOCYear2025Day04 {

    private static final char PAPER = '@';
    private static final char REMOVED = 'X';

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day4/input.txt");
            
            System.out.println("Part 1: " + part1(createGrid(lines)));
            System.out.println("Part 2: " + part2(createGrid(lines)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(StringGrid grid) {
        return getRemovedPaperCount(grid);
    }

    private static int part2(StringGrid grid) {
        var count = 0;
        var removedThisRound = getRemovedPaperCount(grid);
        while (removedThisRound > 0) {
            count += removedThisRound;
            removedThisRound = getRemovedPaperCount(grid);
        }
        return count;
    }

    private static StringGrid createGrid(List<String> lines) throws Exception {
        var grid = new StringGrid();
        for (String line: lines) {
            grid.addRow(line);
        }
        return grid;
    }

    private static int getRemovedPaperCount(StringGrid grid) {
        var count = 0;
        List<Cell> toRemove = new ArrayList<>();
        for (int row=0; row<grid.getRowSize(); row++) {
            for (int col=0; col<grid.getColumnSize(); col++) {
                if (grid.charAt(row, col) == PAPER) {
                    if (getAdjacentPaperCount(grid, row,  col) < 4) {
                        count++;
                        toRemove.add(new Cell(row, col));
                    }
                }
            }
        }
        for (var cell: toRemove) {
            grid.setCellValue(REMOVED, cell.rowIndex(), cell.columnIndex());
        }
        return count;
    }

    private static int getAdjacentPaperCount(StringGrid grid, int rowIndex, int columnIndex) {
        var count = 0;
        for (int row=rowIndex-1; row<=rowIndex+1; row++) {
            for (int col=columnIndex-1; col<=columnIndex+1; col++) {
                if (row == rowIndex && col == columnIndex) {
                    continue;
                }
                if (row >= 0 && row < grid.getRowSize() && col >= 0 && col < grid.getColumnSize()) {
                    if (grid.charAt(row, col) == PAPER) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
