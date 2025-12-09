package nl.schoutens.aoc2025.day9;

import nl.schoutens.datatype.Cell;
import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.ArrayList;
import java.util.List;

public class AOCYear2025Day09 {

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day9/input.txt");

            var points = new ArrayList<Cell>();
            for (var line : lines) {
                var split = line.split(",");
                points.add(new Cell(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }

            System.out.println("Part 1: " + part1(points));
            System.out.println("Part 2: " + part2(points)); // 4629504600, too high
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long part1(List<Cell> points) {
        var max = 0L;

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var area = calculateArea(points.get(i), points.get(j));

                if (area > max) {
                    max = area; 
                }
            }
        }

        return max;
    }

    private static long part2(List<Cell> points) {
        var max = 0L;

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var p1 = points.get(i);
                var p2 = points.get(j);
                var area = calculateArea(p1, p2);

                if (area > max) {
                    var areaEdges = getAreaEdges(p1, p2);
                    var isValid = true;
                    for (var edge : areaEdges) {
                        // System.out.println("  Edge: " + edge);
                        if (!points.contains(edge)) {
                            if (!isPointInArea(edge.columnIndex(), edge.rowIndex(), points)) {
                                isValid = false;
                                // System.out.println("    Edge " + edge + " is NOT in area defined by points");
                                break;
                            }
                        }
                    }
                    if (isValid) {
                        // System.out.println("  Valid area between " + points.get(i) + " and " + points.get(j) + " = " + area);
                        max = area; 
                    }
                }
            }
        }

        return max;
    }

    private static long calculateArea(Cell p1, Cell p2) {
        long length = Math.abs(p1.columnIndex() - p2.columnIndex()) + 1;
        long width = Math.abs(p1.rowIndex() - p2.rowIndex()) + 1;
        return length * width;
    }

    private static List<Cell> getAreaEdges(Cell p1, Cell p2) {
        var edges = new ArrayList<Cell>();

        int minRow = Math.min(p1.rowIndex(), p2.rowIndex());
        int maxRow = Math.max(p1.rowIndex(), p2.rowIndex());
        int minCol = Math.min(p1.columnIndex(), p2.columnIndex());
        int maxCol = Math.max(p1.columnIndex(), p2.columnIndex());

        edges.add(new Cell(minRow, minCol));
        edges.add(new Cell(minRow, maxCol));
        edges.add(new Cell(maxRow, minCol));
        edges.add(new Cell(maxRow, maxCol));

        return edges;
    }

    public static boolean isPointInArea(double x, double y, List<Cell> points) {
        boolean inside = false;
        int n = points.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = points.get(i).columnIndex();
            double yi = points.get(i).rowIndex();
            double xj = points.get(j).columnIndex();
            double yj = points.get(j).rowIndex();

            boolean intersect = ((yi > y) != (yj > y)) &&
                    (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

            if (intersect) {
                inside = !inside;
            }
        }
        return inside;
    }
}
