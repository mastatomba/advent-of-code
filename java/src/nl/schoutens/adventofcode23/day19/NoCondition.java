package nl.schoutens.adventofcode23.day19;

public class NoCondition extends Condition {
    public NoCondition() {
        super("!");
    }

    @Override
    public boolean matches(Part part) {
        return true;
    }

    @Override
    public void applyBounds(RatingBound ratingBound, boolean inverse) {
        // do nothing with bounds as there is no condition
    }

    @Override
    public String toString() {
        return "NoCondition[]";
    }
}
