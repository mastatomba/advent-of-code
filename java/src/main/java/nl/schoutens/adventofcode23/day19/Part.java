package nl.schoutens.adventofcode23.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Each part is rated in each of four categories:
 * x: Extremely cool looking
 * m: Musical (it makes a noise when you hit it)
 * a: Aerodynamic
 * s: Shiny
 */
public class Part {
    private int cat_x;
    private int cat_m;
    private int cat_a;
    private int cat_s;

    public Part(int cat_x, int cat_m, int cat_a, int cat_s) {
        this.cat_x = cat_x;
        this.cat_m = cat_m;
        this.cat_a = cat_a;
        this.cat_s = cat_s;
    }

    public int getPartCategoryValue(char category) {
        switch (category) {
            case 'x': return cat_x;
            case 'm': return cat_m;
            case 'a': return cat_a;
            case 's': return cat_s;        
            default:
                System.err.println("Unsupported category " + category);
                break;
        }
        return 0;
    }

    public int getRating() {
        return cat_x + cat_m + cat_a + cat_s;
    }

    @Override
    public String toString() {
        return "Part[x=" + cat_x + ",m=" + cat_m + ",a=" + cat_a + ",s=" + cat_s + ",rating="+getRating()+"]";
    }

    /**
     * Create a Part instance by input string
     * @param input the input string, for example "{x=787,m=2655,a=1222,s=2876}"
     */
    public static Part createPartByInput(String input) {
        return new Part(
            getPartCategoryValueFromInput(input, 'x'),
            getPartCategoryValueFromInput(input, 'm'),
            getPartCategoryValueFromInput(input, 'a'),
            getPartCategoryValueFromInput(input, 's')
        );
    }

    public static long calculateNumberOfCombinations(
        int min_cat_x,
        int max_cat_x,
        int min_cat_m,
        int max_cat_m,
        int min_cat_a,
        int max_cat_a,
        int min_cat_s,
        int max_cat_s
    ) {
        long numberOfCombinations = 1;
        numberOfCombinations *= (long)(max_cat_x - min_cat_x + 1);
        numberOfCombinations *= (long)(max_cat_m - min_cat_m + 1);
        numberOfCombinations *= (long)(max_cat_a - min_cat_a + 1);
        numberOfCombinations *= (long)(max_cat_s - min_cat_s + 1);

        System.out.println(numberOfCombinations);

        return numberOfCombinations;
    }

    private static int getPartCategoryValueFromInput(String input, char category) {
        Pattern pattern = Pattern.compile(category + "=\\d+");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String match = matcher.group();
            // System.out.println("Part category match: " + match);
            return Integer.parseInt(match.substring(2));
        }
        return 0;
    }
}