package nl.schoutens.adventofcode23.day19;

public class Rule {
    private Condition condition;
    private String destination;

    public Rule(Condition condition, String destination) {
        this.condition = condition;
        this.destination = destination;
    }

    public boolean matches(Part part) {
        return condition.matches(part);
    }

    public String getDestination() {
        return destination;
    }

    /**
     * Create a Rule instance by input string
     * @param input the input string, for example "a<2006:qkq", "m>2090:A" or "rfg"
     */
    public static Rule createRuleByInput(String input) {
        if (input.contains(":")) {
            String[] splitted = input.split(":");
            return new Rule(new Condition(splitted[0]), splitted[1]);
        } else {
            return new Rule(new NoCondition(), input);
        }
    }
}
