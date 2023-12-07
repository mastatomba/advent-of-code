package nl.schoutens.adventofcode23.day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Question: Find the rank of every hand in your set. What are the total winnings?
 */
public class AdventOfCode2023Day7Part1 {

    public static void main(String[] args) throws IOException {
        String fileName = "resources/input/2023/day7/input.txt";
        long totalWinnings = calculateTotalWinnings(fileName, StandardCharsets.UTF_8);
        System.out.println("Total winnings: " + totalWinnings);
    }

    private static long calculateTotalWinnings(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        List<CamelCardHand> hands = new ArrayList<>();

        while((line = br.readLine()) != null){
            hands.add(createCamelCardHand(line));
        }
        br.close();

        Collections.sort(hands, new Comparator<CamelCardHand>() {
            public int compare(CamelCardHand hand1, CamelCardHand hand2) {
                return Long.valueOf(hand1.calculateHandStrength()).compareTo(Long.valueOf(hand2.calculateHandStrength()));
            }
        });

        long totalWinnings = 0;
        int rank = 1;

        for (CamelCardHand camelCardHand : hands) {
            int handWinnings = camelCardHand.getBidAmount() * rank;
            totalWinnings += handWinnings;

            // System.out.println(camelCardHand);
            // System.out.println("\t" + rank + " * " + camelCardHand.getBidAmount() + " = " + handWinnings + " (" + totalWinnings + ")");

            rank++;
        }

        return totalWinnings;
    }

    /*
     * Input example: 32T3K 765
     */
    private static CamelCardHand createCamelCardHand(String line) {
        String[] splitted = line.trim().split(" ");
        return new CamelCardHand(splitted[0], Integer.parseInt(splitted[1]));
    }
}
