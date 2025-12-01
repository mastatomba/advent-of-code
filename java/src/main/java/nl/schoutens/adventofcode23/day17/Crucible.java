package nl.schoutens.adventofcode23.day17;

import java.util.ArrayList;
import java.util.List;

public class Crucible {
    private int rowIndex;
    private int columnIndex;
    private List<String> visited;
    private int sumOfHeatLoss = 0;

    public Crucible(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.visited = new ArrayList<>();
        this.visited.add(rowIndex + "_" + columnIndex);
    }

    public boolean move(int rowIndex, int columnIndex, int heatLoss) {
        if (this.visited.contains(rowIndex + "_" + columnIndex)) {
            return false;
        }
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.visited.add(rowIndex + "_" + columnIndex);
        this.sumOfHeatLoss += heatLoss;
    }

}
