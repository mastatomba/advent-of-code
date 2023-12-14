package nl.schoutens.adventofcode23.day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Question: Find the line of reflection in each of the patterns in your notes. What number do you get after summarizing all of your notes?
 */
public class AdventOfCode2023Day13Part2 {
    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day13/input.txt";
        // submitted: 27841
        int summarizeSum = calculateSummarizeSum(fileName, StandardCharsets.UTF_8);
        System.out.println("Summarize sum: " + summarizeSum);
    }

    private static int calculateSummarizeSum(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        List<MirrorCollection> mirrorCollections = new ArrayList<>();
        MirrorCollection mirrorCollection = new MirrorCollection();

        while((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                mirrorCollections.add(mirrorCollection);
                mirrorCollection = new MirrorCollection();
            } else {
                mirrorCollection.add(new MirrorRow(line));
            }
        }
        mirrorCollections.add(mirrorCollection);
        br.close();

        int summarizeSum = 0;
        for(int i=0; i<mirrorCollections.size(); i++) {
            System.out.println("Summarize collection " + (i+1));

            SummarizeResult result = mirrorCollections.get(i).fixSmudgeAndSummarize();
            System.out.println("\t" + result);

            summarizeSum += result.calculateResult();
        }

        return summarizeSum;
    }
}
