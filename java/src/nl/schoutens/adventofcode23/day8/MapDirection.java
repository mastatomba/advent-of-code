package nl.schoutens.adventofcode23.day8;

public class MapDirection {
    private String leftInstruction;
    private String rightInstruction;

    public MapDirection(
        String leftInstruction,
        String rightInstruction
    ) {
        this.leftInstruction = leftInstruction;
        this.rightInstruction = rightInstruction;
    }

    public String getLocationByDirection(char direction) {
        return direction == 'L' ? this.leftInstruction : this.rightInstruction;
    }

    @Override
    public String toString() {
        return this.leftInstruction + ", " + this.rightInstruction;
    }
}
