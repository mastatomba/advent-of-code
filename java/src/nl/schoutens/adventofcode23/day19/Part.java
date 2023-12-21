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

    @Override
    public String toString() {
        return "x=" + cat_x + ",m=" + cat_m + ",a=" + cat_a + ",s=" + cat_s;
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