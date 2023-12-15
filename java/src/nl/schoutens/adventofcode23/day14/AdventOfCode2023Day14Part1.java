package nl.schoutens.adventofcode23.day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import nl.schoutens.datatype.StringGrid;

/**
 * Question: Tilt the platform so that the rounded rocks all roll north. Afterward, what is the total load on the north support beams?
 */
public class AdventOfCode2023Day14Part1 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day14/input.txt";
        long loadOfNorthBeams = calculateLoadOfNorthBeams(fileName, StandardCharsets.UTF_8);
        System.out.println("Total load of north beams: " + loadOfNorthBeams);
    }

    private static long calculateLoadOfNorthBeams(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringGrid stringGrid = new StringGrid();

        while((line = br.readLine()) != null) {
            stringGrid.addRow(line);
        }
        br.close();

        PlatformConfiguration platformConfiguration = new PlatformConfiguration(stringGrid);
        platformConfiguration.slideRocksNorth();

        return platformConfiguration.calculateLoadOfNorthBeams();
    }
}
