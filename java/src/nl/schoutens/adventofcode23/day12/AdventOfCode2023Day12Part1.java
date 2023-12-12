package nl.schoutens.adventofcode23.day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import nl.schoutens.util.StringUtils;

/**
 * Question: For each row, count all of the different arrangements of operational and broken springs that meet the given criteria. What is the sum of those counts?
 */
public class AdventOfCode2023Day12Part1 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day12/input.txt";
        int numberOfPossibleArrangements = calculateNumberOfPossibleArrangements(fileName, StandardCharsets.UTF_8);
        System.out.println("Number of possible arrangements: " + numberOfPossibleArrangements);
    }

    private static int calculateNumberOfPossibleArrangements(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        List<SpringRow> springRows = new ArrayList<>();
        while((line = br.readLine()) != null){
            springRows.add(createSpringRow(line));
        }
        br.close();

        int numberOfPossibleArrangements = 0;
        for (SpringRow springRow : springRows) {
            numberOfPossibleArrangements += springRow.getNumberOfPossibleArrangements();
        }

        return numberOfPossibleArrangements;
    }

    /**
     * Example input: ???.### 1,1,3
     */
    private static SpringRow createSpringRow(String line) {
        String[] splittedLine = line.split(" ");
        return new SpringRow(splittedLine[0], StringUtils.extractNumbersFromString(splittedLine[1]));
    }
}
