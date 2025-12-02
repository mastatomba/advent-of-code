package nl.schoutens.aoc2025.day2;

import java.util.ArrayList;
import java.util.List;

import nl.schoutens.util.StringUtils;

public class IDRange {
    private long start;
    private long end;

    public IDRange(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public List<Long> getInvalidIDs() {
        var invalidIDs = new ArrayList<Long>();
        if (start > end) {
            throw new IllegalArgumentException("Start of range must be less than or equal to end: " + start + " > " + end);
        }
        for (long i = start; i <= end; i++) {
            if (isInvalidID(i)) {
                invalidIDs.add(i);
            }
        }
        return invalidIDs;
    }

    public List<Long> getPart2InvalidIDs() {
        var invalidIDs = new ArrayList<Long>();
        if (start > end) {
            throw new IllegalArgumentException("Start of range must be less than or equal to end: " + start + " > " + end);
        }
        for (long id = start; id <= end; id++) {
            String idString = String.valueOf(id);
            for (int i = 2; i <= idString.length(); i++) {
                if (StringUtils.isRepeatedExactly(idString, i)) {
                    invalidIDs.add(id);
                    break;
                }
            }
        }
        return invalidIDs;
    }

    private boolean isInvalidID(long id) {
        String idString = String.valueOf(id);

        if (idString.length() % 2 != 0) {
            return false;
        }

        var mid = idString.length() / 2;
        return idString.substring(0, mid).equals(idString.substring(mid));
    }

    public static IDRange fromString(String rangeStr) {
        var parts = rangeStr.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + rangeStr);
        }
        return new IDRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
}
