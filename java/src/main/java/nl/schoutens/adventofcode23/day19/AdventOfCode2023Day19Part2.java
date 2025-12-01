package nl.schoutens.adventofcode23.day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question: Consider only your list of workflows; How many distinct combinations of ratings will be accepted by the Elves' workflows?
 * Story input should return 167409079868000 (167 409 079 868 000).
 */
public class AdventOfCode2023Day19Part2 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day19/input.txt";
        long numberOfCombinations = calculateNumberOfCombinations(fileName, StandardCharsets.UTF_8);
        System.out.println("Number of rating combinations: " + numberOfCombinations);
    }

    private static long calculateNumberOfCombinations(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        Map<String,Workflow> workflows = new HashMap<>();
        long numberOfCombinations = 0;

        while((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            Workflow workflow = Workflow.createWorkflowByInput(line);
            workflows.put(workflow.getName(), workflow);
        }
        br.close();

        for (Workflow workflow: workflows.values()) {
            List<Rule> acceptedRules = workflow.getAcceptedRules();
            for (Rule acceptedRule : acceptedRules) {
               numberOfCombinations += workflow.calculateNumberOfCombinations(workflows, acceptedRule);
            }
        }

        return numberOfCombinations;
    }
}
