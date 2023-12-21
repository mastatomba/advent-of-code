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
    }
}
