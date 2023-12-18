package nl.schoutens.adventofcode23.day18;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Question: The Elves are concerned the lagoon won't be large enough; if they follow their dig plan, how many cubic meters of lava could it hold?
 */
public class AdventOfCode2023Day18Part1 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day18/input.txt";
        long totalCubicMeters = calculateTotalCubicMeters(fileName, StandardCharsets.UTF_8);
        System.out.println("Total cubic meters: " + totalCubicMeters);
    }

    private static long calculateTotalCubicMeters(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        DigPlan digPlan = new DigPlan();

        while((line = br.readLine()) != null) {
            digPlan.add(createDigPlanRow(line));
        }
        br.close();

        return digPlan.dig(true);
    }

    /*
     * Example input:
     * R 6 (#70c710)
     */
    private static DigPlanRow createDigPlanRow(String line) {
        String[] splitted = line.split(" ");
        return new DigPlanRow(splitted[0].charAt(0), Integer.parseInt(splitted[1]), splitted[2]);
    }
}
