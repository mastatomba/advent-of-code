package nl.schoutens.aoc2025.day8;

import java.util.ArrayList;
import java.util.List;

import nl.schoutens.datatype.NumberRange;

public class Circuit implements Comparable<Circuit> {
    private List<JunctionBox> junctionBoxes;

    public Circuit(JunctionBox box1, JunctionBox box2) {
        this.junctionBoxes = new ArrayList<>();
        this.junctionBoxes.add(box1);
        this.junctionBoxes.add(box2);
    }

    public void addJunctionBox(JunctionBox box) {
        this.junctionBoxes.add(box);
    }

    public List<JunctionBox> getJunctionBoxes() {
        return junctionBoxes;
    }

    public boolean hasJunctionBox(JunctionBox box) {
        return this.junctionBoxes.contains(box);
    }

    public boolean canMerge(Circuit other) {
        for (var box: other.junctionBoxes) {
            if (this.hasJunctionBox(box)) {
                return true;
            }
        }
        return false;
    }

    public void merge(Circuit other) {
        for (var box: other.junctionBoxes) {
            if (!this.hasJunctionBox(box)) {
                this.addJunctionBox(box);
            }
        }
    }

    @Override
    public int compareTo(Circuit other) {
        return Integer.compare(junctionBoxes.size(), other.junctionBoxes.size());
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "junctionBoxes=" + junctionBoxes +
                '}';
    }
}
