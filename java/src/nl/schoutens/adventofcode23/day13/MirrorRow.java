package nl.schoutens.adventofcode23.day13;

/**
 * Input example 1:
 *   #.##..##.
 * 
 * Input example 2:
 *   #####.##.
 */
public class MirrorRow {
    private String patternString;

    public MirrorRow(String patternString) {
        this.patternString = patternString;
    }

    public boolean equals(MirrorRow mirrorRow) {
        return this.patternString.equals(mirrorRow.patternString);
    }

    public char patternAt(int patternIndex) {
        return this.patternString.charAt(patternIndex);
    }

    @Override
    public String toString() {
        return this.patternString;
    }
}
