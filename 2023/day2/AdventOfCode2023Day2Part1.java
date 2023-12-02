package nl.schoutens.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AdventOfCode2023Day2Part1 {

    public static void main(String[] args) throws IOException {
        String fileName = "input.txt";
        int sumOfPossibleGameIds = calculateSumOfPossibleGameIds(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of all possible game IDs: " + sumOfPossibleGameIds);
    }

    private static int calculateSumOfPossibleGameIds(String fileName, Charset cs) throws IOException {
        int sumOfPossibleGameIds = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while((line = br.readLine()) != null){
            /*
             * Split the line on ':', for example the following line:
             * "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
             *   line1: "Game 1"
             *   line2: " 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
             */
            String[] lineSplittedOnColon = line.split(":");

            /*
             * Split the line on ';' to get the game subsets:
             *   line1: " 3 blue, 4 red"
             *   line2: " 1 red, 2 green, 6 blue"
             *   line3: " 2 green"
             */
            String[] gameSubsets = lineSplittedOnColon[1].split(";");

            boolean isGamePossible = true;
            for (String gameSubset: gameSubsets) {
                /*
                 * Determine which games would have been possible if the bag had been loaded with only 
                 * 12 red cubes, 13 green cubes, and 14 blue cubes
                 */
                if (!isGameConfigurationPossible(gameSubset, 12, 13, 14)) {
                    System.out.println("IMPOSSIBLE: " + gameSubset);
                    isGamePossible = false;
                    break;
                }
            }

            if (isGamePossible) {
                sumOfPossibleGameIds += Integer.parseInt(lineSplittedOnColon[0].replaceAll("[^0-9]", ""));
            }
        }
        br.close();
        return sumOfPossibleGameIds;
    }

    private static boolean isGameConfigurationPossible(
        String gameSubset,
        int numberOfRedCubes,
        int numberOfGreenCubes,
        int numberOfBlueCubes
    ) {
        /*
         * Split the game subset on ',' to get the cube counts:
         *   line1: " 3 blue"
         *   line2: " 4 red"
         */
        String[] cubeCounts = gameSubset.split(",");

        for (String cubeCount: cubeCounts) {
            if (cubeCount.contains("red")) {
                if (Integer.parseInt(cubeCount.replaceAll("[^0-9]", "")) > numberOfRedCubes) {
                    return false;
                }
            } else if (cubeCount.contains("green")) {
                if (Integer.parseInt(cubeCount.replaceAll("[^0-9]", "")) > numberOfGreenCubes) {
                    return false;
                }
            } else if (cubeCount.contains("blue")) {
                if (Integer.parseInt(cubeCount.replaceAll("[^0-9]", "")) > numberOfBlueCubes) {
                    return false;
                }
            } else {
                System.err.println(cubeCount);
            }
        }

        return true;
    }
}
