package nl.schoutens.aoc2025.day1;

public class Rotation {
    private boolean clockwise;
    private int degrees;

    public Rotation(boolean clockwise, int degrees) {
        this.clockwise = clockwise;
        this.degrees = degrees;
    }

    public int rotate(int position) {
        return clockwise ? rotateClockwise(position) : rotateCounterClockwise(position);
    }

    private int rotateClockwise(int position) {
        return (position + degrees) % 100;
    }

    private int rotateCounterClockwise(int position) {
        return ((position - degrees) % 100 + 100) % 100;
    }

    public int calculateZeroCount(int position) {
        var newPos = clockwise ? position + degrees : position - degrees;

        if (newPos > 0 && newPos < 100) {
            return 0;
        }

        if (newPos >= 100) {
            return newPos / 100;
        }

        var count = Math.abs(Math.floorDiv(newPos, 100));

        if (newPos % 100 == 0) {
            count += 1;
        }
        if (position == 0) {
            count -= 1;
        }

        return count;
    }

    public static Rotation fromString(String rotationString) {
        var clockwise = rotationString.charAt(0) == 'R';
        var degrees = Integer.parseInt(rotationString.substring(1));
        return new Rotation(clockwise, degrees);
    }
}
