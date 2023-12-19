package nl.schoutens.adventofcode23.day16;

public enum BeamDirection {
    NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

    final int rowOffset;
    final int colOffset;

    BeamDirection(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }
}
