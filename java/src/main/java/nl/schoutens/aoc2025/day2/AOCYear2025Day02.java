package nl.schoutens.aoc2025.day2;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

public class AOCYear2025Day02 {
    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static long part1() {
        long sum = 0;
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day2/input.txt");
            var ranges = lines.get(0).split(",");

            for (var range : ranges) {
                var idRange = IDRange.fromString(range);
                var invalidIDs = idRange.getInvalidIDs();
                for (var id : invalidIDs) {
                    sum += id;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    private static long part2() {
        long sum = 0;
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day2/input.txt");
            var ranges = lines.get(0).split(",");

            for (var range : ranges) {
                var idRange = IDRange.fromString(range);
                var invalidIDs = idRange.getPart2InvalidIDs();
                for (var id : invalidIDs) {
                    sum += id;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}
