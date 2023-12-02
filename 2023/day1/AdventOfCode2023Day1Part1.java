package nl.schoutens;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AdventOfCodeDay1SumOfAllCalibrationValues {

    public static void main(String[] args) throws IOException {
        String fileName = "input.txt";
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
            String numberOnly= line.replaceAll("[^0-9]", "");
            // first digit
            String calibrationValue = Character.toString(numberOnly.charAt(0));
            // last digit
            calibrationValue += Character.toString(numberOnly.charAt(numberOnly.length() -1));

            sumOfCalibrationValues += Integer.parseInt(calibrationValue);
        }
        br.close();
        return sumOfCalibrationValues;
    }
}
