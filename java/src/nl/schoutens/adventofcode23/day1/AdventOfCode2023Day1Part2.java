package nl.schoutens.adventofcode23.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AdventOfCode2023Day1Part2 {

    public static void main(String[] args) throws IOException {
        // Result should be 54078
        String fileName = "resources/input/2023/day1/input.txt";
        int sumOfCalibrationValues = calculateSumOfCalibrationValues(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of all calibration values: " + sumOfCalibrationValues);
    }

    private static int calculateSumOfCalibrationValues(String fileName, Charset cs) throws IOException {
        int sumOfCalibrationValues = 0;
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while((line = br.readLine()) != null){
            String lineWithAddedDigits = line;
            String textDigits[] = { "one","two","three","four","five","six","seven","eight","nine" };
            for (int i = 0; i < textDigits.length; i++) {
                int digit = i+1;
                String textDigit = textDigits[i];

                lineWithAddedDigits = lineWithAddedDigits.replace(textDigit, textDigit+digit+textDigit);
            }

            String numberOnly = lineWithAddedDigits.replaceAll("[^0-9]", "");
            // first digit
            String calibrationValue = Character.toString(numberOnly.charAt(0));
            // last digit
            calibrationValue += Character.toString(numberOnly.charAt(numberOnly.length() -1));

            System.out.println("\t" + line + " | " + lineWithAddedDigits + " | " + numberOnly + " | " + calibrationValue);

            sumOfCalibrationValues += Integer.parseInt(calibrationValue);
        }
        br.close();
        return sumOfCalibrationValues;
    }
}
