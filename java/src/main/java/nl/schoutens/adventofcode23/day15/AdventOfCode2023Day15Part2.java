package nl.schoutens.adventofcode23.day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Question: With the help of an over-enthusiastic reindeer in a hard hat, follow the initialization sequence. 
 * What is the focusing power of the resulting lens configuration?
 * 
 * Story input:
 * rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
 */
public class AdventOfCode2023Day15Part2 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day15/input_story.txt";
        long sumOfHashAlgorithms = calculateSumOfHashAlgorithms(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of HASH algorithms: " + sumOfHashAlgorithms);
    }

    private static long calculateSumOfHashAlgorithms(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        br.close();

        long sumOfHashAlgorithms = 0;
        String[] steps = line.split(",");
        for (String step : steps) {
            sumOfHashAlgorithms += runHashAlgorithm(step);
        }

        return sumOfHashAlgorithms;
    }

    private static int runHashAlgorithm(String step) {
        // System.out.println(step);

        int result = 0;
        for (char character: step.toCharArray()) {
            result += (int)character;
            result *= 17;
            result = result % 256;
        }

        // System.out.println("\t"+result);

        return result;
    }
}
