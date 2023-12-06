package nl.schoutens.adventofcode23.day6;

public class BoatRace {
    private int timeAllowed;
    private long recordDistance;

    public BoatRace(
        int timeAllowed,
        long recordDistance
    ) {
        this.timeAllowed = timeAllowed;
        this.recordDistance = recordDistance;
    }

    public int getNumberOfPossibleWins() {
        int numberOfWins = 0;

        for (int i = 1; i<this.timeAllowed; i++) {
            // cast to long otherwise we walk into int limitation of 2147483442
            long distanceTravelled = (long)(this.timeAllowed - i) * (long)i;
            // System.out.println("\t"+i + ": "+distanceTravelled);
            if (distanceTravelled > this.recordDistance) {
                numberOfWins++;
            }
        }

        return numberOfWins;
    }
}
