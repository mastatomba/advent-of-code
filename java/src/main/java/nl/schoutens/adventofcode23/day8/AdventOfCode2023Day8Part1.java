package nl.schoutens.adventofcode23.day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
 * Question: Starting at `AAA`, follow the left/right instructions. How many steps are required to reach `ZZZ`?
 */
public class AdventOfCode2023Day8Part1 {

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

        return calculateNumberOfStepsForStartLocation(directions, mapDirections, "AAA", "ZZZ");
    }

    public static int calculateNumberOfStepsForStartLocation(
        char[] directions,
        Map<String,MapDirection> mapDirections,
        String startLocation,
        String finishLocationSuffix
    ) {
        int numberOfSteps = 0;
        int currentIteration = 0;
        boolean found = false;
        String currentLocation = startLocation;

        while (!found) {
            for (char direction : directions) {
                numberOfSteps++;

                MapDirection mapDirection = mapDirections.get(currentLocation);
                currentLocation = mapDirection.getLocationByDirection(direction);

                if (currentLocation.endsWith(finishLocationSuffix)) {
                    System.out.println("Found on step: " + numberOfSteps);
                    found = true;
                    break;
                }
            }
        
            if (!found && (++currentIteration >= 100)) {
                System.err.println("It's taking too long, current location: " + currentLocation);
                break;
            }
        }

        return numberOfSteps;
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
