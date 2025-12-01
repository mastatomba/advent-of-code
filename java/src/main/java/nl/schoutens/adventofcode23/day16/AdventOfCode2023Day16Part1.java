package nl.schoutens.adventofcode23.day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import nl.schoutens.datatype.StringGrid;

/**
 * Question: The light isn't energizing enough tiles to produce lava; to debug the contraption, you need to start by analyzing the current situation. 
 * With the beam starting in the top-left heading right, how many tiles end up being energized?
 * // grid size: 110 x 110 = 12.100 tiles
 * // incorrect answer given: 8110 (too high)
 */
public class AdventOfCode2023Day16Part1 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day16/input_story.txt";
        long numberOfEnergizedTiles = calculateNumberOfEnergizedTiles(fileName, StandardCharsets.UTF_8);
        System.out.println("Number of energized tiles: " + numberOfEnergizedTiles);
    }

    private static long calculateNumberOfEnergizedTiles(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringGrid stringGrid = new StringGrid();

        while((line = br.readLine()) != null) {
            stringGrid.addRow(line);
        }
        br.close();

        System.out.println("Grid size: " + stringGrid.getRowSize() + "*" + stringGrid.getColumnSize());

        BeamContraption beamContraption = new BeamContraption(stringGrid);
        beamContraption.energize();
        return beamContraption.getNumberOfEnergizedTiles();
    }
}
