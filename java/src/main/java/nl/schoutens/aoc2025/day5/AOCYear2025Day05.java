package nl.schoutens.aoc2025.day5;

import nl.schoutens.util.FileUtils;
import static nl.schoutens.aoc2025.AOCYear2025Constants.INPUT_BASE_PATH;

import java.util.List;
import java.util.ArrayList;

import nl.schoutens.datatype.NumberRange;

public class AOCYear2025Day05 {

    public static void main(String[] args) {
        try {
            var lines = FileUtils.fileToList(INPUT_BASE_PATH + "day5/input.txt");
            var ranges = new ArrayList<NumberRange>();
            var ingredientIds = new ArrayList<Long>();
            var processRanges = true;

            for (var line : lines) {
                if (processRanges) {
                    if (line.isBlank()) {
                        processRanges = false;
                    } else {
                        var parts = line.split("-");
                        ranges.add(new NumberRange(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
                    }
                } else {
                    ingredientIds.add(Long.parseLong(line));
                }
            }

            System.out.println("Part 1: " + part1(ranges, ingredientIds));
            System.out.println("Part 2: " + part2(ranges));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<NumberRange> ranges, List<Long> ingredientIds) {
        var count = 0;
        for (var id : ingredientIds) {
            for (var range : ranges) {
                if (range.contains(id)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private static long part2(List<NumberRange> ranges) {
        var mergedRanges = NumberRange.mergeRanges(ranges);

        long count = 0;
        for (var range : mergedRanges) {
            count += (range.end() - range.start() + 1);
        }

        return count;
    }
}
