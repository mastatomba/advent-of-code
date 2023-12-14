package nl.schoutens.adventofcode23.day13;

public class SummarizeResult {
    private boolean isHorizontal;
    private int lineIndex;

    public SummarizeResult(boolean isHorizontal, int lineIndex) {
        this.isHorizontal = isHorizontal;
        this.lineIndex = lineIndex;
    }

    public int calculateResult() {
        int result = this.lineIndex + 1;
        if (this.isHorizontal) {
            result *= 100;
        }
        return result;
    }

    public boolean equals(SummarizeResult summarizeResult) {
        return this.isHorizontal == summarizeResult.isHorizontal && this.lineIndex == summarizeResult.lineIndex;
    }

    @Override
    public String toString() {
        return (this.isHorizontal ? "HORIZONTAL" : "VERTICAL") + " (" + this.lineIndex + "): " + this.calculateResult();
    }
}
