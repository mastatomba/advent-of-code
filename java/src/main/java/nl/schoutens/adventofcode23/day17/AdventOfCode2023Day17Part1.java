package nl.schoutens.adventofcode23.day17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import nl.schoutens.datatype.StringGrid;

/**
 * Question: Directing the crucible from the lava pool to the machine parts factory, 
 * but not moving more than three consecutive blocks in the same direction, what is the least heat loss it can incur?
 */
public class AdventOfCode2023Day17Part1 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day17/input_test.txt";
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
