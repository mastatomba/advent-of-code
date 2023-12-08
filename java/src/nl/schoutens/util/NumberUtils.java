package nl.schoutens.util;

public class NumberUtils {
    /**
     * Find the GCD (Greatest Common Divisor) of two numbers.
     * GCD is the highest number that completely divides two or more numbers.
     */
    public static long findGcd(long x, long y) {
        if (x == 0) {
            return y;
        }
        return findGcd(y % x, x);
    }

    /**
     * Calculate the LCM (Least Common Multiple) of two numbers.
     * LCM is the least positive number that can be divided by both the numbers, without leaving the remainder.
     */
    public static long calculateLcm(long x, long y) {
        return (x / findGcd(x, y)) * y;
    }
}
