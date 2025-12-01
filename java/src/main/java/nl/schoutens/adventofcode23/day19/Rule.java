package nl.schoutens.adventofcode23.day19;

public class Rule {
    private int ruleNumber;
    private Condition condition;
    private String destination;

    public Rule(int ruleNumber, Condition condition, String destination) {
        this.ruleNumber = ruleNumber;
        this.condition = condition;
        this.destination = destination;
    }

    public boolean matches(Part part) {
        return condition.matches(part);
    }

    public String getDestination() {
        return destination;
    }

    public int getRuleNumber() {
        return ruleNumber;
    }

    public void applyBounds(RatingBound ratingBound, boolean inverse) {
        condition.applyBounds(ratingBound, inverse);
    }

    @Override
    public String toString() {
        return "Rule[ruleNumber=" + ruleNumber + ",condition=" + condition + ",destination=" + destination + "]";
    }

    /**
     * Create a Rule instance by input string
     * @param input the input string, for example "a<2006:qkq", "m>2090:A" or "rfg"
     */
    public static Rule createRuleByInput(String input, int ruleNumber) {
        // System.out.println(input);

        if (input.contains(":")) {
            String[] splitted = input.split(":");

            // System.out.println("\t" + splitted[0] + ", " + splitted[1]);

            return new Rule(ruleNumber, new Condition(splitted[0]), splitted[1]);
        } else {
            return new Rule(ruleNumber, new NoCondition(), input);
        }
    }
}
