package nl.schoutens.adventofcode23.day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import nl.schoutens.datatype.StringGrid;

/**
 * Question: Run the spin cycle for 1000000000 cycles. Afterward, what is the total load on the north support beams?
 * Correct answer: 118747
 */
public class AdventOfCode2023Day14Part2 {
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
        long numberOfCycles = 1000;
        for (long i=0; i< numberOfCycles; i++) {
            platformConfiguration.runCycle();

            // if found out that there is a repeating result after 90 cycles. each 63 cycles it repeats itself.
            // if (i % 250 == 0) {
                System.out.println(i+": "+platformConfiguration.calculateLoadOfNorthBeams());
            // }
        }

        return platformConfiguration.calculateLoadOfNorthBeams();
    }
}
