package nl.schoutens.adventofcode23.day19;

public class RatingBound {
    public int min_cat_x = 1;
    public int max_cat_x = 4000;
    public int min_cat_m = 1;
    public int max_cat_m = 4000;
    public int min_cat_a = 1;
    public int max_cat_a = 4000;
    public int min_cat_s = 1;
    public int max_cat_s = 4000;

    @Override
    public String toString() {
        String props = "cat_x="+min_cat_x+","+max_cat_x;
        props += ",cat_m="+min_cat_m+","+max_cat_m;
        props += ",cat_a="+min_cat_a+","+max_cat_a;
        props += ",cat_s="+min_cat_s+","+max_cat_s;
        return "RatingBound["+props+"]";
    }

    public long calculateNumberOfCombinations() {
        long numberOfCombinations = 1;
        numberOfCombinations *= (long)(max_cat_x - min_cat_x + 1);
        numberOfCombinations *= (long)(max_cat_m - min_cat_m + 1);
        numberOfCombinations *= (long)(max_cat_a - min_cat_a + 1);
        numberOfCombinations *= (long)(max_cat_s - min_cat_s + 1);

        // System.out.println(numberOfCombinations);

        return numberOfCombinations;
    }

    public void applyBounds(char part_category, char operator, int value, boolean inverse) {
        // System.out.println("RatingBound.applyBounds("+part_category+","+operator+","+value+","+(inverse?"true":"false")+")");
        
        int minRating = calculateMinRating(operator, value, inverse);
        int maxRating = calculateMaxRating(operator, value, inverse);

        switch (part_category) {
            case 'x':
                applyBoundsOnX(minRating, maxRating);
                break;
            case 'm':
                applyBoundsOnM(minRating, maxRating);
                break;
            case 'a':
                applyBoundsOnA(minRating, maxRating);
                break;
            case 's':
                applyBoundsOnS(minRating, maxRating);
                break;
            default:
                System.err.println("RatingBound.applyBounds(): Unknown part_category "+part_category);
                break;
        }        
    }

    /**
     * x > 2500  (inverse: x < 2501)
     * x < 1500  (inverse: x > 1499)
     */
    private int calculateMinRating(char operator, int value, boolean inverse) {
        if (operator == '>') {
            if (!inverse) {
                return value + 1;
            }
        } else if (operator == '<') {
            if (inverse) {
                return value;
            }
        } else {
            System.err.println("RatingBound.calculateMinRating(): Unknown operator "+operator);
        }
        return 1;
    }

    /**
     * x > 2440  (inverse: x < 2441)
     * x < 1500  (inverse: x > 1499)
     */
    private int calculateMaxRating(char operator, int value, boolean inverse) {
        if (operator == '>') {
            if (inverse) {
                return value;
            }
        } else if (operator == '<') {
            if (!inverse) {
                return value - 1;
            }
        } else {
            System.err.println("RatingBound.calculateMaxRating(): Unknown operator "+operator);
        }
        return 4000;
    }

    private void applyBoundsOnX(int minRating, int maxRating) {
        if (minRating > min_cat_x) {
            min_cat_x = minRating;
        }
        if (maxRating < max_cat_x) {
            max_cat_x = maxRating;
        }
    }

    private void applyBoundsOnM(int minRating, int maxRating) {
        if (minRating > min_cat_m) {
            min_cat_m = minRating;
        }
        if (maxRating < max_cat_m) {
            max_cat_m = maxRating;
        }
    }

    private void applyBoundsOnA(int minRating, int maxRating) {
        if (minRating > min_cat_a) {
            min_cat_a = minRating;
        }
        if (maxRating < max_cat_a) {
            max_cat_a = maxRating;
        }
    }

    private void applyBoundsOnS(int minRating, int maxRating) {
        if (minRating > min_cat_s) {
            min_cat_s = minRating;
        }
        if (maxRating < max_cat_s) {
            max_cat_s = maxRating;
        }
    }
}
