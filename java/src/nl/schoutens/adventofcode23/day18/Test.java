package nl.schoutens.adventofcode23.day18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String str = "              #..........................................#...... ... ... . ....  .. . ......                                         #......................................................#                                                                                            ";
        Pattern pattern = Pattern.compile("\\#[ .]* [ .]*\\#");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            System.out.println(matcher.group());

            if (matcher.group().contains(" ") && matcher.group().contains(".")) {
                System.out.println("\tYes");
            } else {
                System.out.println("\tNo");
            }
        }
    }
}
