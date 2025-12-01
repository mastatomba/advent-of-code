package nl.schoutens.adventofcode23.day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.schoutens.util.NumberUtils;

/*
 * Question: Starting at `AAA`, follow the left/right instructions. How many steps are required to reach `ZZZ`?
 */
public class AdventOfCode2023Day8Part2 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day8/input.txt";
        long numberOfSteps = calculateNumberOfSteps(fileName, StandardCharsets.UTF_8);
        System.out.println("Number of steps: " + numberOfSteps);
    }

    private static long calculateNumberOfSteps(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        Map<String,MapDirection> mapDirections = new HashMap<>();
        char[] directions = null;

        while((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                if (directions == null) {
                    directions = line.toCharArray();
                } else {
                    appendMapDirection(mapDirections, line);
                }
            }
        }
        br.close();

        // System.out.println(Arrays.toString(directions));
        // System.out.println("Directions:");
        // mapDirections.forEach((key, value) -> System.out.println("\t" + key + ": " + value));

        List<Integer> numberOfStepsList = new ArrayList<>();
        for (String location : mapDirections.keySet()) {
            if (location.endsWith("A")) {
                int numberOfSteps = AdventOfCode2023Day8Part1.calculateNumberOfStepsForStartLocation(directions, mapDirections, location, "Z");
                numberOfStepsList.add(numberOfSteps);

                System.out.println("numberOfSteps for start location "+location+": "+numberOfSteps);
            }
        }

        long lcm = 0;
        long x = numberOfStepsList.get(0);
        for (int i=1; i<numberOfStepsList.size(); i++) {
            long y = numberOfStepsList.get(i);
            lcm = NumberUtils.calculateLcm(x, y);

            System.out.println("LCM for "+x+" and "+y+": "+lcm);

            x = lcm;
        }

        return lcm;
    }

    /*
     * Input example: 'FLR = (SXT, CRV)'
     */
    private static void appendMapDirection(Map<String,MapDirection> mapDirections, String line) {
        String[] splitted = line.split("=");
        mapDirections.put(splitted[0].trim(), createMapDirection(splitted[1].trim()));
    }

    /*
     * Input example: '(SXT, CRV)'
     */
    private static MapDirection createMapDirection(String line) {
        line = line.substring(1, line.length()-1); // should get 'SXT, CRV'
        String[] splitted = line.split(","); // should get 'SXT' and ' CRV'
        return new MapDirection(splitted[0].trim(), splitted[1].trim());
    }
}
