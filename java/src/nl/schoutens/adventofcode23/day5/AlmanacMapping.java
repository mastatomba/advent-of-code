package nl.schoutens.adventofcode23.day5;

public class AlmanacMapping {
    private long destinationStart;
    private long sourceStart;
    private long rangeLength;

    public AlmanacMapping(
        long destinationStart,
        long sourceStart,
        long rangeLength
    ) {
        this.destinationStart = destinationStart;
        this.sourceStart = sourceStart;
        this.rangeLength = rangeLength;
    }

    public boolean isSourceInRange(long source) {
        return 
            source >= this.sourceStart && 
            source < (this.sourceStart + this.rangeLength)
        ;
    }

    public long getDestination(long source) {
        return source + (this.destinationStart - this.sourceStart);
    }
}
