package nl.schoutens.adventofcode23.day19;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Rule> getAcceptedRules() {
        List<Rule> acceptedRules = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.getDestination().equals("A")) {
                acceptedRules.add(rule);
            }
        }
        return acceptedRules;
    }

    public long calculateNumberOfCombinations(Map<String,Workflow> allWorkflows, Rule acceptedRule) {
        // System.out.println("= Workflow.calculateNumberOfCombinations()");

        RatingBound ratingBound = new RatingBound();
        calculateNumberOfCombinations(allWorkflows, acceptedRule, ratingBound);
        return ratingBound.calculateNumberOfCombinations();
    }

    private void calculateNumberOfCombinations(Map<String,Workflow> allWorkflows, Rule sourceRule, RatingBound ratingBound) {
        // System.out.println("\t" + name + ": " + sourceRule);

        for (Rule rule : rules) {
            if (rule.getRuleNumber() == sourceRule.getRuleNumber()) {
                rule.applyBounds(ratingBound, false);

                if (!name.equals("in")) {
                    for (Workflow sourceWorkflow : allWorkflows.values()) {
                        Rule newSourceRule = sourceWorkflow.getRuleByDestination(name);
                        if (newSourceRule != null) {
                            sourceWorkflow.calculateNumberOfCombinations(allWorkflows, newSourceRule, ratingBound);
                        }
                    }
                }
                // rule found, so break out of rules loop
                break;
            } else {
                rule.applyBounds(ratingBound, true);
            }
        }
    }

    private Rule getRuleByDestination(String destination) {
        for (Rule rule : rules) {
            if (rule.getDestination().equals(destination)) {
                return rule;
            }
        }
        return null;
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

        String[] rules = input.substring(i + 1, input.length() - 1).split(",");
        int ruleNumber = 0;
        for (String rule : rules) {

            // System.out.println("\t" + rule);

            workflow.addRule(Rule.createRuleByInput(rule, ++ruleNumber));
        }

        return workflow;
    }
}
