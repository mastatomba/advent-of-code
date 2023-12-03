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
 * Question: What is the sum of all of the part numbers in the engine schematic?
 */
public class AdventOfCode2023Day3Part1 {

    public static void main(String[] args) throws IOException {
        String fileName = "input.txt";
        int sumOfApplicableEnginePartNumbers = calculateSumOfApplicableEnginePartNumbers(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of all of the part numbers in the engine schematic: " + sumOfApplicableEnginePartNumbers);
    }

    private static int calculateSumOfApplicableEnginePartNumbers(String fileName, Charset cs) throws IOException {
        int sumOfApplicableEnginePartNumbers = 0;
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

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(engineSchematicListRow);

            while (matcher.find()) {
                boolean numberIsAdjacentToSymbol = false;
                // check same row
                if (isNumberAdjacentToSymbol(engineSchematicListRow, (matcher.start()-1), (matcher.end()+1))) {
                    numberIsAdjacentToSymbol = true;
                }
                // check row above
                if (!numberIsAdjacentToSymbol && i > 0) {
                    if (isNumberAdjacentToSymbol(engineSchematicList.get(i-1), (matcher.start()-1), (matcher.end()+1))) {
                        numberIsAdjacentToSymbol = true;
                    }
                }
                // check row below
                if (!numberIsAdjacentToSymbol && i < (engineSchematicList.size()-1)) {
                    if (isNumberAdjacentToSymbol(engineSchematicList.get(i+1), (matcher.start()-1), (matcher.end()+1))) {
                        numberIsAdjacentToSymbol = true;
                    }
                }

                if (numberIsAdjacentToSymbol) {
                    sumOfApplicableEnginePartNumbers += Integer.parseInt(matcher.group());
                }
            }
        }

        return sumOfApplicableEnginePartNumbers;
    }

    private static boolean isNumberAdjacentToSymbol(String engineSchematicListRow, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex > engineSchematicListRow.length() - 1) {
            endIndex = engineSchematicListRow.length() - 1;
        }

        String checkForSymbol = engineSchematicListRow.substring(beginIndex, endIndex);
        // delete all numbers
        checkForSymbol = checkForSymbol.replaceAll("\\d+", "");
        // delete all . characters
        checkForSymbol = checkForSymbol.replaceAll("\\.", "");

        return checkForSymbol.length() > 0;
    }
}
