package nl.schoutens.adventofcode23.day6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Question: How many ways can you beat the record in this one much longer race?
 */
public class AdventOfCode2023Day6Part2 {

    public static void main(String[] args) throws IOException {
        // List<BoatRace> races = getStoryInput();
        List<BoatRace> races = getAssignmentInput();

        int result = 1;
        for (BoatRace boatRace : races) {
            int numberOfWins = boatRace.getNumberOfPossibleWins();
            System.out.println("Number of possible wins: "+ numberOfWins);
            result *= numberOfWins;
        }

        System.out.println("Result: "+ result);
    }

    /*
     * Time:      71530
     * Distance:  940200
     */
    private static List<BoatRace> getStoryInput() {
        List<BoatRace> races = new ArrayList<>();
        races.add(new BoatRace(71530, 940200));
        return races;
    }

    /*
     * Time:       61677571
     * Distance:   430103613071150
     */
    private static List<BoatRace> getAssignmentInput() {
        List<BoatRace> races = new ArrayList<>();
        races.add(new BoatRace(61677571, Long.parseLong("430103613071150")));
        return races;
    }
}
