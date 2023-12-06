package nl.schoutens.adventofcode23.day6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Question: Determine the number of ways you could beat the record in each race. What do you get if you multiply these numbers together?
 */
public class AdventOfCode2023Day6Part1 {

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
     * Time:      7  15   30
     * Distance:  9  40  200
     */
    private static List<BoatRace> getStoryInput() {
        List<BoatRace> races = new ArrayList<>();
        races.add(new BoatRace(7, 9));
        races.add(new BoatRace(15, 40));
        races.add(new BoatRace(30, 200));
        return races;
    }

    /*
     * Time:        61     67     75     71
     * Distance:   430   1036   1307   1150
     */
    private static List<BoatRace> getAssignmentInput() {
        List<BoatRace> races = new ArrayList<>();
        races.add(new BoatRace(61, 430));
        races.add(new BoatRace(67, 1036));
        races.add(new BoatRace(75, 1307));
        races.add(new BoatRace(71, 1150));
        return races;
    }
}
