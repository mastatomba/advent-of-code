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

    public int getColumnSize() {
        return this.patternString.length();
    }

    public void flipPattern(int patternIndex) {
        char currentPattern = this.patternString.charAt(patternIndex);
        char newPattern = '#';
        if (currentPattern == '#') {
            newPattern = '.';
        }
        this.patternString = this.patternString.substring(0, patternIndex) + newPattern + this.patternString.substring(patternIndex + 1);
    }

    public MirrorRow createCopy() {
        return new MirrorRow(this.patternString);
    }

    @Override
    public String toString() {
        return this.patternString;
    }
}
