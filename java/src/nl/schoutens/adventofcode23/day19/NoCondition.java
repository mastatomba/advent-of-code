package nl.schoutens.adventofcode23.day19;

public class NoCondition extends Condition {
    public NoCondition() {
        super("!");
    }

    @Override
    public boolean matches(Part part) {
        return true;
    }
}
