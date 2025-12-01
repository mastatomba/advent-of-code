package nl.schoutens.adventofcode23.day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AdventOfCode2023Day2Part2 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day2/input.txt";
        int sumOfPowerOfTheSets = calculateSumOfPowerOfTheSets(fileName, StandardCharsets.UTF_8);
        System.out.println("The sum of the power of the sets: " + sumOfPowerOfTheSets);
    }

    private static int calculateSumOfPowerOfTheSets(String fileName, Charset cs) throws IOException {
        int sumOfPowerOfTheSets = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while((line = br.readLine()) != null){
            System.out.println(line);
            /*
             * Split the line on ':', for example the following line:
             * "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
             *   line1: "Game 1"
             *   line2: " 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
             */
            String[] lineSplittedOnColon = line.split(":");

            int numberOfRedCubes = getMinimumCubeCount(lineSplittedOnColon[1], "red");
            int numberOfGreenCubes = getMinimumCubeCount(lineSplittedOnColon[1], "green");
            int numberOfBlueCubes = getMinimumCubeCount(lineSplittedOnColon[1], "blue");

            int powerOfTheSet = numberOfRedCubes * numberOfGreenCubes * numberOfBlueCubes;

            System.out.println("\t" + powerOfTheSet + " (" + numberOfRedCubes + " * " + numberOfGreenCubes + " * " + numberOfBlueCubes + ")");

            sumOfPowerOfTheSets += powerOfTheSet;
        }
        br.close();
        return sumOfPowerOfTheSets;
    }

    private static int getMinimumCubeCount(String line, String color) {
        Pattern pattern = Pattern.compile("( \\d+ " + color + ")");
        Matcher matcher = pattern.matcher(line);

        int minimumCubeCount = 0;
        while(matcher.find()) {
            int cubeCount = Integer.parseInt(matcher.group(1).replaceAll("[^0-9]", ""));
            if (cubeCount > minimumCubeCount) {
                minimumCubeCount = cubeCount;
            }
        }

        return minimumCubeCount;
    }
}
