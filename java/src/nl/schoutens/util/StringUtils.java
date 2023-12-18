package nl.schoutens.util;

import java.util.Arrays;

public class StringUtils {
    public static int[] extractNumbersFromString(String str) {
        // Replacing every non-digit number with a space(" ")
        str = str.replaceAll("[^0-9]", " ");
 
        // Replace all the consecutive white spaces with a single space
        str = str.replaceAll(" +", " ");

        return Arrays.stream(str.trim().split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] extractLongNumbersFromString(String str) {
        // Replacing every non-digit number with a space(" ")
        str = str.replaceAll("[^0-9]", " ");
 
        // Replace all the consecutive white spaces with a single space
        str = str.replaceAll(" +", " ");

        return Arrays.stream(str.trim().split(" ")).mapToLong(Long::parseLong).toArray();
    }

    public static int countCharacterInString(String str, char character) {
        return (int)str.chars().filter(ch -> ch == character).count();
    }
}
