package nl.schoutens.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Question: What is the sum of all of the gear ratios in your engine schematic?
 */
public class AdventOfCode2023Day3Part2 {

    public static void main(String[] args) throws IOException {
        String fileName = "input.txt";
        int sumOfApplicableGearRatios = calculateSumOfApplicableGearRatios(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of all of the gear ratios in your engine schematic: " + sumOfApplicableGearRatios);
    }

    private static int calculateSumOfApplicableGearRatios(String fileName, Charset cs) throws IOException {
        int sumOfApplicableGearRatios = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        List<String> engineSchematicList = new ArrayList<>();
        while((line = br.readLine()) != null){
            engineSchematicList.add(line);
        }
        br.close();

        for (int i = 0; i < engineSchematicList.size(); i++) {
            String engineSchematicListRow = engineSchematicList.get(i);
            if (engineSchematicListRow.contains("*")) {

                Pattern pattern = Pattern.compile("\\*");
                Matcher matcher = pattern.matcher(engineSchematicListRow);

                while (matcher.find()) {
                    List<Integer> partNumbers = new ArrayList<>();
                    // check same row
                    appendAdjacentPartNumbers(partNumbers, engineSchematicListRow, (matcher.start()), (matcher.end()));
                    // check row above
                    if (i > 0) {
                        appendAdjacentPartNumbers(partNumbers, engineSchematicList.get(i-1), (matcher.start()), (matcher.end()));
                    }
                    // check row below
                    if (i < (engineSchematicList.size()-1)) {
                        appendAdjacentPartNumbers(partNumbers, engineSchematicList.get(i+1), (matcher.start()), (matcher.end()));
                    }

                    if (partNumbers.size() >= 2) {
                        int gearRatio = partNumbers.get(0).intValue();
                        for (int j = 1; j < partNumbers.size(); j++) {
                            gearRatio *= partNumbers.get(j).intValue();
                        }
                        sumOfApplicableGearRatios += gearRatio;
                    }
                }
            }
        }

        return sumOfApplicableGearRatios;
    }

    private static void appendAdjacentPartNumbers(List<Integer> partNumbers, String engineSchematicListRow, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex > engineSchematicListRow.length() - 1) {
            endIndex = engineSchematicListRow.length() - 1;
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(engineSchematicListRow);

        while (matcher.find()) {
            if ((matcher.start() <= endIndex) && (beginIndex <= matcher.end())) {
                partNumbers.add(Integer.parseInt(matcher.group()));
            }
        }
    }
}
