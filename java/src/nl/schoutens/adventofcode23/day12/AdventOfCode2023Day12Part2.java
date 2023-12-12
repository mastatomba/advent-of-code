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
 * Question: Unfold your condition records; what is the new sum of possible arrangement counts?
 */
public class AdventOfCode2023Day12Part2 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day12/input_story.txt";
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
        String conditionRecords = splittedLine[0];
        String damagedSpringGroups = splittedLine[1];
        for (int i=0; i<4; i++) {
            conditionRecords += "?" + splittedLine[0];
            damagedSpringGroups += "," + splittedLine[1];
        }
        return new SpringRow(conditionRecords, StringUtils.extractNumbersFromString(damagedSpringGroups));
    }
}
