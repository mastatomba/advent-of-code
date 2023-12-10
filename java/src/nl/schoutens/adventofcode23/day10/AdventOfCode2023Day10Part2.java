package nl.schoutens.adventofcode23.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.schoutens.util.NumberUtils;

/**
 * Question: Analyze your OASIS report and extrapolate the next value for each history. What is the sum of these extrapolated values?
 */
public class AdventOfCode2023Day10Part2 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day9/input.txt";
        long sumOfExtrapolatedValues = calculateSumOfExtrapolatedValues(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of extrapolated values: " + sumOfExtrapolatedValues);
    }

    private static long calculateSumOfExtrapolatedValues(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        long sumOfExtrapolatedValues = 0;

        while((line = br.readLine()) != null) {
            sumOfExtrapolatedValues += AdventOfCode2023Day10Part1.calculateNextValue(getInputNumbers(line));
        }
        br.close();

        return sumOfExtrapolatedValues;
    }

    private static int[] getInputNumbers(String line) {
        String[] numbers = line.split(" ");
        Collections.reverse(Arrays.asList(numbers));
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
    }
}
