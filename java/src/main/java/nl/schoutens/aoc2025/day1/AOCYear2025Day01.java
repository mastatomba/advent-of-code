package nl.schoutens.aoc2025.day1;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

public class AOCYear2025Day01 {
    public static void main(String[] args) {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private static int part1() {
        var count = 0;
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day1/input.txt");
            var position = 50;

            for (var line : lines) {
                var rotation = Rotation.fromString(line);
                position = rotation.rotate(position);

                if (position == 0) {
                    count++;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private static int part2() {
        var count = 0;
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day1/input.txt");
            var position = 50;

            for (var line : lines) {
                var rotation = Rotation.fromString(line);

                count += rotation.calculateZeroCount(position);

                position = rotation.rotate(position);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
