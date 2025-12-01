package nl.schoutens.adventofcode23.day19;

public class TestPart {
    public static void main(String[] args) {
        Part part = Part.createPartByInput("{x=787,m=2655,a=1222,s=2876}");
        System.out.println(part);

        System.out.println(part.getPartCategoryValue('x'));
        System.out.println(part.getPartCategoryValue('m'));
        System.out.println(part.getPartCategoryValue('a'));
        System.out.println(part.getPartCategoryValue('s'));

        System.out.println(part.getPartCategoryValue('?'));

        String[] conditions = new String[]{"x>300", "m<2000", "a>1222", "s<2500", "x=787"};
        for (String condition : conditions) {
            System.out.println(condition);

            Condition c = new Condition(condition);
            if (c.matches(part)) {
                System.out.println("\tYes");
            } else {
                System.out.println("\tNo");
            }
        }

        long number = 0;

        number += Part.calculateNumberOfCombinations(1, 4000, 2091, 4000, 2006, 4000, 1, 1350);
        number += Part.calculateNumberOfCombinations(1, 4000, 1, 838, 1, 1716, 1351, 2770);
        number += Part.calculateNumberOfCombinations(1, 4000, 1549, 4000, 1, 4000, 2771, 3448);
        number += Part.calculateNumberOfCombinations(1, 4000, 1, 1548, 1, 4000, 2771, 3448);
        number += Part.calculateNumberOfCombinations(1, 2440, 1, 2090, 2006, 4000, 537, 1350);
        number += Part.calculateNumberOfCombinations(1, 4000, 1, 4000, 1, 4000, 3449, 4000);
        number += Part.calculateNumberOfCombinations(1, 1415, 1, 4000, 1, 2005, 1, 1350);
        number += Part.calculateNumberOfCombinations(2663, 4000, 1, 4000, 1, 2005, 1, 1350);
        number += Part.calculateNumberOfCombinations(1, 4000, 839, 1800, 1, 4000, 1351, 2770);

        System.out.println("Total: "+number);
    }
}
