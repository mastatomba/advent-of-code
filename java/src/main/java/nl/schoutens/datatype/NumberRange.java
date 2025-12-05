package nl.schoutens.datatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record NumberRange(long start, long end) implements Comparable<NumberRange> {

    public boolean contains(long number) {
        return number >= start && number <= end;
    }

    @Override
    public int compareTo(NumberRange other) {
        int cmp = Long.compare(this.start, other.start);
        return cmp != 0 ? cmp : Long.compare(this.end, other.end);
    }

    public static List<NumberRange> mergeRanges(List<NumberRange> ranges) {
        List<NumberRange> sorted = new ArrayList<>(ranges);
        Collections.sort(sorted);

        List<NumberRange> merged = new ArrayList<>();
        NumberRange current = sorted.get(0);

        for (int i = 1; i < sorted.size(); i++) {
            NumberRange next = sorted.get(i);

            if (next.start() <= current.end()) {
                current = new NumberRange(current.start(), Math.max(current.end(), next.end()));
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);

        return merged;
    }
}
