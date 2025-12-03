package nl.schoutens.aoc2025.day3;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.List;

public class AOCYear2025Day03 {
    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day3/input.txt");
            
            System.out.println("Part 1: " + part1(lines));
            System.out.println("Part 2: " + part2(lines));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long part1(List<String> lines) {
        long sum = 0;
            
        for (var line : lines) {
            var bank = new BatteryBank(line);
            var voltage = bank.calculateMaxVoltage();

            sum += voltage;
        }

        return sum;
    }

    private static long part2(List<String> lines) {
        long sum = 0;
            
        for (var line : lines) {
            var bank = new BatteryBank(line);
            var voltage = bank.calculateMaxVoltage(12);

            sum += voltage;
        }

        return sum;
    }
}
