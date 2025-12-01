package nl.schoutens.adventofcode23.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Input examples:
 * ???.### 1,1,3
 * .??..??...?##. 1,1,3
 * ?#?#?#?#?#?#?#? 1,3,1,6
 * ????.#...#... 4,1,1
 * ????.######..#####. 1,6,5
 * ?###???????? 3,2,1
 */
public class SpringRow {
    private String conditionRecords;
    private int[] damagedSpringGroups;

    public SpringRow(String conditionRecords, int[] damagedSpringGroups) {
        this.conditionRecords = conditionRecords;
        this.damagedSpringGroups = damagedSpringGroups;
    }

    public int getNumberOfPossibleArrangements() {
        List<String> arrangements = this.getAllArangements();
        int count = 0;
        Pattern pattern = Pattern.compile("\\#+");

        for (String arrangement : arrangements) {
            // System.out.print("\tTesting arrangement '" + arrangement + "': ");
            
            Matcher matcher = pattern.matcher(arrangement);
            int damagedSpringGroupsIndex = 0;
            int numberOfMatches = 0;
            boolean isPossible = true;

            while (matcher.find()) {
                if (damagedSpringGroupsIndex >= this.damagedSpringGroups.length) {
                    isPossible = false;
                    break;
                }
                if (matcher.group().length() != this.damagedSpringGroups[damagedSpringGroupsIndex++]) {
                    isPossible = false;
                    break;
                }
                numberOfMatches++;
            }
            if (isPossible && numberOfMatches==this.damagedSpringGroups.length) {
                count++;
                // System.out.println("OK");
            } else {
                // System.out.println("NOT POSSIBLE");
            }
        }

        System.out.println("Condition record '" + this.conditionRecords + "' (" + Arrays.toString(this.damagedSpringGroups) + "): "+count);

        return count;
    }

    private List<String> getAllArangements() {
        List<String> arrangements = new ArrayList<>();
        arrangements.add(this.conditionRecords);

        boolean done = false;
        int iteration = 0;
        while (!done) {
            if (++iteration>100) {
                System.err.println("MAX iterations reached!!");
                break;
            }
            List<String> newArrangements = new ArrayList<>();
            for (String arrangement : arrangements) {
                if (arrangement.contains("?")) {
                    newArrangements.add(arrangement.replaceFirst("\\?", "#"));
                    newArrangements.add(arrangement.replaceFirst("\\?", "."));
                } else {
                    newArrangements.add(arrangement);
                }
            }
            if (arrangements.size() == newArrangements.size()) {
                done = true;
            } else {
                arrangements = newArrangements;
            }
        }

        return arrangements;
    }
}
