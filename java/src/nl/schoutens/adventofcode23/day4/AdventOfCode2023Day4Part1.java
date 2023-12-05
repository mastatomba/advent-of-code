package nl.schoutens.adventofcode23.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

/*
 * Question: What is the sum of all of the scratchcard points?
 */
public class AdventOfCode2023Day4Part1 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day4/input.txt";
        int sumOfScratchCardPoints = calculateSumOfScratchCardPoints(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of all of the scratchcard points: " + sumOfScratchCardPoints);
    }

    private static int calculateSumOfScratchCardPoints(String fileName, Charset cs) throws IOException {
        int sumOfScratchCardPoints = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while((line = br.readLine()) != null){
            sumOfScratchCardPoints += calculateScratchCardPoints(line);
        }
        br.close();

        return sumOfScratchCardPoints;
    }

    /*
     * Example input: 'Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53'
     */
    private static int calculateScratchCardPoints(String scratchCardLine) {
        /*
         * Results in:
         * 'Card 1'
         * ' 41 48 83 86 17 | 83 86  6 31 17  9 48 53'
         */
        String[] scratchCardLineSplitOnColon = scratchCardLine.split(":");
        /*
         * Results in:
         * ' 41 48 83 86 17 '
         * ' 83 86  6 31 17  9 48 53'
         */
        String[] scratchCardLineSplitOnPipe = scratchCardLineSplitOnColon[1].split("\\|");

        Integer[] winningNumbers = convertToNumberArray(scratchCardLineSplitOnPipe[0]);
        Integer[] allNumbers = convertToNumberArray(scratchCardLineSplitOnPipe[1]);

        HashSet<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(winningNumbers));
        set.retainAll(Arrays.asList(allNumbers));

        int scratchCardPoints = getScratchCardPoints(set.size());
        return scratchCardPoints;
    }

    /*
     * Example input: ' 83 86  6 31 17  9 48 53'
     */
    private static Integer[] convertToNumberArray(String numbers) {
        // use trim to remove spaces on beginning/end, replace duplicate space in between
        String [] str = numbers.trim().replaceAll("  ", " ").split(" ");
        int size = str.length;
        Integer[] arr = new Integer[size];
        for(int i=0; i<size; i++) {
            arr[i] = Integer.parseInt(str[i]);
        }
        return arr;
    }

    private static int getScratchCardPoints(int numberOfWinningNumbers) {
        if (numberOfWinningNumbers > 1) {
            int points = 1;
            for (int i=0; i<numberOfWinningNumbers-1; i++) {
                points *= 2;
            }
            return points;
        }
        if (numberOfWinningNumbers == 1) {
            return 1;
        }

        return 0;
    }
}
