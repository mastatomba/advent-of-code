package nl.schoutens.adventofcode23.day18;

public class DigPlanRow {
    private char direction;
    private int numberOfMeters;
    private String color;

    public DigPlanRow(
        char direction,
        int numberOfMeters,
        String color
    ) {
        this.direction = direction;
        this.numberOfMeters = numberOfMeters;
        this.color = color;
    }

    public char getDirection() {
        return direction;
    }

    public int getNumberOfMeters() {
        return numberOfMeters;
    }

    public String getColor() {
        return color;
    }

    public int getRowOffset() {
        if (this.direction == 'U') {
            return this.numberOfMeters * -1;
        }
        if (this.direction == 'D') {
            return this.numberOfMeters;
        }
        return 0;
    }

    public int getColumnOffset() {
        if (this.direction == 'L') {
            return this.numberOfMeters * -1;
        }
        if (this.direction == 'R') {
            return this.numberOfMeters;
        }
        return 0;
    }
}
