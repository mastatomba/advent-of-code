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
public class AdventOfCode2023Day11Part1 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day11/input.txt";
        int sumOfLengths = calculateSumOfLengths(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of lengths: " + sumOfLengths);
    }

    private static int calculateSumOfLengths(String fileName, Charset cs) throws IOException {
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

        System.out.println("Universe size before expansion: " + universeRows.get(0).length() + " * " + universeRows.size());

        universeRows = expandUniverse(universeRows);

        System.out.println("Universe size after expansion: " + universeRows.get(0).length() + " * " + universeRows.size());

        List<Point> galaxies = findGalaxies(universeRows);

        System.out.println("Number of galaxies: " + galaxies.size());
        // for (Point point : galaxies) {
        //     // Number of galaxies: 9
        //     // ....1........   java.awt.Point[x=0,y=4]
        //     // .........2...   java.awt.Point[x=1,y=9]
        //     // 3............   java.awt.Point[x=2,y=0]
        //     // .............
        //     // .............
        //     // ........4....   java.awt.Point[x=5,y=8]
        //     // .5...........   java.awt.Point[x=6,y=1]
        //     // ............6   java.awt.Point[x=7,y=12]
        //     // .............
        //     // .............
        //     // .........7...   java.awt.Point[x=10,y=9]
        //     // 8....9.......   java.awt.Point[x=11,y=0]    java.awt.Point[x=11,y=5]
        //     System.out.println("\t"+point.toString());
        // }

        int sumOfLengths = 0;
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

                    int lengthBetweenGalaxies = getLengthBetweenGalaxies(galaxies.get(i), galaxies.get(j));

                    // System.out.println(lengthBetweenGalaxies);

                    sumOfLengths += lengthBetweenGalaxies;
                } else {
                    // System.out.println("Already checked");
                }
            }
        }

        return sumOfLengths;
    }

    private static int getLengthBetweenGalaxies(Point galaxy1, Point galaxy2) {
        int length = 0;
        if (galaxy1.x < galaxy2.x) {
            length = galaxy2.x - galaxy1.x;
        } else {
            length = galaxy1.x - galaxy2.x;
        }
        if (galaxy1.y < galaxy2.y) {
            length += galaxy2.y - galaxy1.y;
        } else {
            length += galaxy1.y - galaxy2.y;
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

    private static List<String> expandUniverse(List<String> universeRows) {
        List<String> expandedUniverseRows = new ArrayList<>();
        List<Integer> emptyColumnIndices = getEmptyUniverseColumnIndices(universeRows);

        for (String universeRow: universeRows) {
            String expandedUniverseRow = "";
            char[] universeRowColumns = universeRow.toCharArray();
            for (int columnIndex=0; columnIndex<universeRowColumns.length; columnIndex++) {
                expandedUniverseRow += universeRowColumns[columnIndex];
                if (emptyColumnIndices.contains(columnIndex)) {
                    expandedUniverseRow += universeRowColumns[columnIndex];
                }
            }

            expandedUniverseRows.add(expandedUniverseRow);
            if (!universeRow.contains("#")) {
                expandedUniverseRows.add(expandedUniverseRow);
            }
        }

        return expandedUniverseRows;
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
