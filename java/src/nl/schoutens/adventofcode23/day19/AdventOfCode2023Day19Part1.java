package nl.schoutens.adventofcode23.day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question: Sort through all of the parts you've been given; what do you get if you add 
 * together all of the rating numbers for all of the parts that ultimately get accepted?
 */
public class AdventOfCode2023Day19Part1 {
    public static void main(String[] args) throws Exception {
        String fileName = "resources/input/2023/day19/input.txt";
        long ratingNumberTotal = calculateRatingNumberTotal(fileName, StandardCharsets.UTF_8);
        System.out.println("Sum of rating numbers: " + ratingNumberTotal);
    }

    private static long calculateRatingNumberTotal(String fileName, Charset cs) throws Exception {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        Map<String,Workflow> workflows = new HashMap<>();
        List<Part> parts = new ArrayList<>();
        boolean addWorkflows = true;
        boolean addParts = false;
        long ratingNumberTotal = 0;

        while((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                addWorkflows = false;
                addParts = true;
            } else if (addWorkflows) {
                Workflow workflow = Workflow.createWorkflowByInput(line);
                workflows.put(workflow.getName(), workflow);
            } else if (addParts) {
                parts.add(Part.createPartByInput(line));
            }
        }
        br.close();

        for (Part part : parts) {
            // System.out.println(part);

            Workflow workflow = workflows.get("in");
            String destination = workflow.getDestination(part);
            int workflowCount = 1;

            // System.out.println("\t"+(workflowCount++)+"." + destination);

            while (!destination.equals("A") && !destination.equals("R")) {
                workflow = workflows.get(destination);
                destination = workflow.getDestination(part);

                // System.out.println("\t"+(workflowCount++)+"." + destination);
            }
            if (destination.equals("A")) {
                ratingNumberTotal += part.getRating();
            }
        }

        return ratingNumberTotal;
    }
}
