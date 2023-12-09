package nl.schoutens.adventofcode23.day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.schoutens.util.NumberUtils;

/**
 * Question: Analyze your OASIS report and extrapolate the next value for each history. What is the sum of these extrapolated values?
 */
public class AdventOfCode2023Day9Part1 {
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
            sumOfExtrapolatedValues += calculateNextValue(getInputNumbers(line));
        }
        br.close();

        return sumOfExtrapolatedValues;
    }

    private static int[] getInputNumbers(String line) {
        String[] numbers = line.split(" ");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
    }

    public static int calculateNextValue(int[] inputNumbers) {
        System.out.println("calculateNextValue()");
        System.out.println(Arrays.toString(inputNumbers));

        List<Integer> increaseNumbers = new ArrayList<>();
        int currentNumber = inputNumbers[0];
        boolean allZeroes = true;
        for (int i=1; i<inputNumbers.length; i++) {
            int increaseNumber = inputNumbers[i] - currentNumber;
            if (allZeroes && increaseNumber!=0) {
                allZeroes = false;
            }
            increaseNumbers.add(increaseNumber);
            currentNumber = inputNumbers[i];

        }

        if (allZeroes) {
            return currentNumber;
        }
        return currentNumber + calculateNextValue(NumberUtils.convertToIntArray(increaseNumbers));
    }
}
