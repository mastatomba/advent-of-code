package nl.schoutens.adventofcode23.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/*
 * Question: What is the total number of scratchcards?
 */
public class AdventOfCode2023Day4Part2 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day4/input.txt";
        int totalNumberOfScratchCards = calculateTotalNumberOfScratchCards(fileName, StandardCharsets.UTF_8);
        System.out.println("Total number of scratchcards: " + totalNumberOfScratchCards);
    }

    private static int calculateTotalNumberOfScratchCards(String fileName, Charset cs) throws IOException {
        int totalNumberOfScratchCards = 0;
        int currentGameNumber = 1;
        HashMap<Integer, Integer> copiedScratchCards = new HashMap<Integer, Integer>();
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while((line = br.readLine()) != null){
            totalNumberOfScratchCards += appendScratchCards(copiedScratchCards, currentGameNumber++, line);
        }
        br.close();

        return totalNumberOfScratchCards;
    }

    /*
     * Example input: 'Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53'
     */
    private static int appendScratchCards(HashMap<Integer, Integer> copiedScratchCards, int currentGameNumber, String scratchCardLine) {
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

        int numberOfScratchCards = 1;
        if (copiedScratchCards.containsKey(currentGameNumber)) {
            numberOfScratchCards += copiedScratchCards.get(currentGameNumber);
        }

        for (int j=0; j<numberOfScratchCards; j++) {
            for (int i=0; i<set.size(); i++) {
                int gameNumber = currentGameNumber + i + 1;
                int count = 1;
                if (copiedScratchCards.containsKey(gameNumber)) {
                    count = copiedScratchCards.get(gameNumber) + 1;
                }

                copiedScratchCards.put(gameNumber, count);
            }
        }

        return numberOfScratchCards;
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
}
