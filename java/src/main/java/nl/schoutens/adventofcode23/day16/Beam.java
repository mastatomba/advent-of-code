package nl.schoutens.adventofcode23.day16;

import nl.schoutens.datatype.StringGrid;

public class Beam {
    private int beamNumber;
    private int rowIndex;
    private int columnIndex;
    private BeamDirection direction;

    public Beam(
        int beamNumber,
        int rowIndex,
        int columnIndex,
        BeamDirection direction
    ) {
        this.beamNumber = beamNumber;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.direction = direction;
    }

    public int getBeamNumber() {
        return beamNumber;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public BeamDirection getDirection() {
        return this.direction;
    }

    public boolean isInBounds(int rowSize, int columnSize) {
        return (
            this.rowIndex >= 0 && this.rowIndex < rowSize && 
            this.columnIndex >= 0 && this.columnIndex < columnSize
        );
    }

    @Override
    public String toString() {
        return this.beamNumber + ": " + this.rowIndex + "," + this.columnIndex + " ("+this.direction+")";
    }
}
