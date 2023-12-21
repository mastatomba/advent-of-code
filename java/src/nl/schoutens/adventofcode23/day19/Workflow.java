package nl.schoutens.adventofcode23.day19;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private String name;
    private List<Rule> rules;

    public Workflow(String name) {
        this.name = name;
        this.rules = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public String getDestination(Part part) {
        String destination = "R";
        for (Rule rule : rules) {
            destination = rule.getDestination();
            if (rule.matches(part)) {
                return destination;
            }
        }
        // return last destination
        return destination;
    }

    /**
     * Create a Workflow instance by input string
     * @param input the input string, for example "px{a<2006:qkq,m>2090:A,rfg}"
     */
    public static Workflow createWorkflowByInput(String input) {
        int i = input.indexOf("{");
        String name = input.substring(0, i);

        // System.out.println(name);

        Workflow workflow = new Workflow(name);

        String[] rules = input.substring(i, input.length()-1).split(",");
        for (String rule : rules) {
            workflow.addRule(Rule.createRuleByInput(rule));
        }

        return workflow;
    }
}
