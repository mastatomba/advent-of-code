package nl.schoutens.adventofcode23.day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/**
 * Question: Expand the universe, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?
 */
public class AdventOfCode2023Day11Part2 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day11/input.txt";
        long sumOfLengths = calculateSumOfLengths(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of lengths: " + sumOfLengths);
    }

    private static long calculateSumOfLengths(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        List<String> universeRows = new ArrayList<>();
        while((line = br.readLine()) != null){
            universeRows.add(line);
        }
        br.close();

        List<Point> galaxies = findGalaxies(universeRows);
        List<Integer> emptyRowIndices = getEmptyUniverseRowIndices(universeRows);
        List<Integer> emptyColumnIndices = getEmptyUniverseColumnIndices(universeRows);

        System.out.println("Number of galaxies: " + galaxies.size());
        // for (Point point : galaxies) {
        //     // Number of galaxies: 9
        //     // java.awt.Point[x=0,y=3]
        //     // java.awt.Point[x=1,y=7]
        //     // java.awt.Point[x=2,y=0]
        //     // java.awt.Point[x=4,y=6]
        //     // java.awt.Point[x=5,y=1]
        //     // java.awt.Point[x=6,y=9]
        //     // java.awt.Point[x=8,y=7]
        //     // java.awt.Point[x=9,y=0]
        //     // java.awt.Point[x=9,y=4]
        //     System.out.println("\t"+point.toString());
        // }

        long sumOfLengths = 0;
        int expansionSize = 1000000;
        List<String> checkedPairs = new ArrayList<>();
        for (int i=0; i<galaxies.size(); i++) {
            for (int j=0; j<galaxies.size(); j++) {
                if (i==j) {
                    continue;
                }

                // System.out.print("Checking point " + i + " and point " + j + ": ");

                String key = i+"_"+j;
                if (!checkedPairs.contains(key)) {
                    checkedPairs.add(key);
                    checkedPairs.add(j+"_"+i);

                    int lengthBetweenGalaxies = getLengthBetweenGalaxies(
                        galaxies.get(i),
                        galaxies.get(j),
                        expansionSize,
                        emptyRowIndices,
                        emptyColumnIndices
                    );

                    // System.out.println(lengthBetweenGalaxies);

                    sumOfLengths += lengthBetweenGalaxies;
                } else {
                    // System.out.println("Already checked");
                }
            }
        }

        return sumOfLengths;
    }

    private static int getLengthBetweenGalaxies(
        Point galaxy1,
        Point galaxy2,
        int expansionSize,
        List<Integer> emptyRowIndices,
        List<Integer> emptyColumnIndices
    ) {
        int length = 0;
        int startx = 0;
        int endx = 0;
        if (galaxy1.x < galaxy2.x) {
            startx = galaxy1.x;
            endx = galaxy2.x;
        } else {
            startx = galaxy2.x;
            endx = galaxy1.x;
        }
        length = endx - startx;
        for (Integer rowIndex : emptyRowIndices) {
            if (rowIndex>=startx && rowIndex<=endx) {
                length += expansionSize-1;
            }
        }

        int starty = 0;
        int endy = 0;
        if (galaxy1.y < galaxy2.y) {
            starty = galaxy1.y;
            endy = galaxy2.y;
        } else {
            starty = galaxy2.y;
            endy = galaxy1.y;
        }
        length += endy - starty;
        for (Integer columnIndex : emptyColumnIndices) {
            if (columnIndex>=starty && columnIndex<=endy) {
                length += expansionSize-1;
            }
        }

        return length;
    }

    private static List<Point> findGalaxies(List<String> universeRows) {
        List<Point> galaxies = new ArrayList<>();

        for (int x=0; x<universeRows.size(); x++) {
            String universeRow = universeRows.get(x);
            if (universeRow.contains("#")) {
                int y = universeRow.indexOf("#");
                while (y != -1) {
                    galaxies.add(new Point(x, y));
                    y = universeRow.indexOf("#", y+1);
                }
            }
        }

        return galaxies;
    }

    private static List<Integer> getEmptyUniverseRowIndices(List<String> universeRows) {
        List<Integer> rowIndices = new ArrayList<>();

        for (int rowIndex=0; rowIndex<universeRows.size(); rowIndex++) {
            if (!universeRows.get(rowIndex).contains("#")) {
                rowIndices.add(rowIndex);
            }
        }

        return rowIndices;
    }

    private static List<Integer> getEmptyUniverseColumnIndices(List<String> universeRows) {
        List<Integer> columnIndices = new ArrayList<>();
        int numberOfColumns = universeRows.get(0).length();

        for (int columnIndex=0; columnIndex<numberOfColumns; columnIndex++) {
            boolean isEmpty = true;
            for (String universeRow: universeRows) {
                if (universeRow.charAt(columnIndex) == '#') {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                columnIndices.add(columnIndex);
            }
        }

        return columnIndices;
    }
}
