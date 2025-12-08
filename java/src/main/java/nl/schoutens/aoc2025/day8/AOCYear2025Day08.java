package nl.schoutens.aoc2025.day8;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AOCYear2025Day08 {

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day8/input.txt");

            System.out.println("Part 1: " + part1(lines, 1000));
            System.out.println("Part 2: " + part2(lines, 4667));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<String> lines, int circuitCount) {
        var boxes = parseInput(lines);
        var circuits = new ArrayList<Circuit>();
        var minDistance = 0.0;
        for (int i = 0; i < circuitCount; i++) {
            var newCircuit = findClosest(boxes, minDistance);

            circuits.add(newCircuit);

            minDistance = newCircuit.getJunctionBoxes().get(0).calculateDistance(newCircuit.getJunctionBoxes().get(1));
        }

        var mergedCircuits = mergeCircuits(circuits);
        Collections.sort(mergedCircuits, Collections.reverseOrder());

        var count = mergedCircuits.get(0).getJunctionBoxes().size();
        for (int i = 1; i < 3; i++) {
            count *= mergedCircuits.get(i).getJunctionBoxes().size();
        }

        return count;
    }

    private static int part2(List<String> lines, int circuitCount) {
        var boxes = parseInput(lines);
        var circuits = new ArrayList<Circuit>();
        var minDistance = 0.0;
        for (int i = 0; i < circuitCount; i++) {
            var newCircuit = findClosest(boxes, minDistance);
            if (circuitCount - i <=5) {
                System.out.println(newCircuit);
                // Circuit{junctionBoxes=[JunctionBox{x=82474, y=15334, z=6651}, JunctionBox{x=91704, y=5837, z=10407}]}
            }

            circuits.add(newCircuit);

            minDistance = newCircuit.getJunctionBoxes().get(0).calculateDistance(newCircuit.getJunctionBoxes().get(1));
        }

        var mergedCircuits = mergeCircuits(circuits);
        Collections.sort(mergedCircuits, Collections.reverseOrder());
        // for (var circuit: mergedCircuits) {
        //     System.out.println("Final circuit: " + circuit);
        // }
        System.out.println("Total merged circuits: " + mergedCircuits.size());

        var count = mergedCircuits.get(0).getJunctionBoxes().size();

        return count;
    }

    private static Circuit findClosest(List<JunctionBox> boxes, double minDistance) {
        var distance = Double.MAX_VALUE;
        Circuit closestCircuit = null;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                var box1 = boxes.get(i);
                var box2 = boxes.get(j);
                var currentDistance = box1.calculateDistance(box2);

                if (currentDistance < distance && currentDistance > minDistance) {
                    distance = currentDistance;
                    closestCircuit = new Circuit(box1, box2);
                }
            }
        }
        return closestCircuit;
    }

    private static List<Circuit> mergeCircuits(List<Circuit> circuits) {
        var mergedCircuits = new ArrayList<Circuit>();
        var mergedIndices = new ArrayList<Integer>();
        for (int i = 0; i < circuits.size(); i++) {
            var circuit1 = circuits.get(i);
            boolean merged = false;
            do {
                merged = false;
                for (int j = i + 1; j < circuits.size(); j++) {
                    if (mergedIndices.contains(j)) {
                        continue;
                    }
                    var circuit2 = circuits.get(j);
                    if (circuit1.canMerge(circuit2)) {
                        circuit1.merge(circuit2);
                        mergedIndices.add(j);
                        merged = true;
                    }
                }
            } while (merged);
            
            if (!mergedIndices.contains(i)) {
                mergedCircuits.add(circuit1);
            }
        }
        return mergedCircuits;
    }

    private static List<JunctionBox> parseInput(List<String> lines) {
        return lines.stream()
                .map(JunctionBox::fromString)
                .toList();
    }
}
