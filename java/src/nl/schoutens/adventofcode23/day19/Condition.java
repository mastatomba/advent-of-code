package nl.schoutens.adventofcode23.day19;

/**
 * condition consist out of [part_category][operator][value], for example:
 * a<2006
 * m>2090
 * s<537
 */
public class Condition {
    private String condition;

    public Condition(String condition) {
        this.condition = condition;
    }
    
    public boolean matches(Part part) {
        char part_category = getPartCategory();
        char operator = getOperator();
        int value = getValue();

        // System.out.println(condition + ": " + part_category + " " + operator + " " + value);

        switch (operator) {
            case '>': return part.getPartCategoryValue(part_category) > value;
            case '<': return part.getPartCategoryValue(part_category) < value;       
            default:
                System.err.println("Unsupported operator " + operator);
                break;
        }
        return false;
    }

    /**
     * @return part category, possible values are: 'x', 'm', 'a' and 's'.
     */
    private char getPartCategory() {
        return condition.toCharArray()[0];
    }

    /**
     * @return operator, possible values are: '<' and '>'.
     */
    private char getOperator() {
        return condition.toCharArray()[1];
    }

    private int getValue() {
        return Integer.parseInt(condition.substring(2));
    }

    @Override
    public String toString() {
        return condition;
    }
}
