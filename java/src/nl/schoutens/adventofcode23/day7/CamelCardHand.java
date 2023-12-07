package nl.schoutens.adventofcode23.day7;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CamelCardHand {
    protected static final String TYPE_FIVE_OF_A_KIND = "Five of a kind";
    protected static final String TYPE_FOUR_OF_A_KIND = "Four of a kind";
    protected static final String TYPE_FULL_HOUSE = "Full house";
    protected static final String TYPE_THREE_OF_A_KIND = "Three of a kind";
    protected static final String TYPE_TWO_PAIR = "Two pair";
    protected static final String TYPE_ONE_PAIR = "One pair";
    protected static final String TYPE_HIGH_CARD = "High card";

    protected String cardsAsString;
    private int bidAmount;
    protected String handType;

    public CamelCardHand(
        String cardsAsString,
        int bidAmount
    ) {
        this.cardsAsString = cardsAsString;
        this.bidAmount = bidAmount;
        this.determineHandType(this.createCardCountMap());
    }

    public long calculateHandStrength() {
        return this.getHandTypeStrength() + this.calculateCardsStrength();
    }

    public int getBidAmount() {
        return this.bidAmount;
    }

    @Override
    public String toString() {
        return this.cardsAsString + " | " + this.bidAmount + " | " + this.handType + " | " + this.calculateHandStrength();
    }

    protected void determineHandType(Map<Character,Integer> cardCountMap) {
        Collection<Integer> cardCounts = cardCountMap.values();
        if (cardCounts.contains(5)) {
            this.handType = CamelCardHand.TYPE_FIVE_OF_A_KIND;
        } else if (cardCounts.contains(4)) {
            this.handType = CamelCardHand.TYPE_FOUR_OF_A_KIND;
        } else if (cardCounts.contains(3)) {
            if (cardCounts.contains(2)) {
                this.handType = CamelCardHand.TYPE_FULL_HOUSE;
            } else {
                this.handType = CamelCardHand.TYPE_THREE_OF_A_KIND;
            }
        } else if (cardCounts.contains(2)) {
            int pairs = 0;
            for (Integer cardCount: cardCounts) {
                if (cardCount == 2) {
                    pairs++;
                }
            }
            if (pairs == 2) {
                this.handType = CamelCardHand.TYPE_TWO_PAIR;
            } else {
                this.handType = CamelCardHand.TYPE_ONE_PAIR;
            }
        } else {
            this.handType = CamelCardHand.TYPE_HIGH_CARD;
        }
    }

    protected Map<Character,Integer> createCardCountMap() {
        char[] cards = this.cardsAsString.toCharArray();
        Map<Character,Integer> cardCountMap = new HashMap<>();

        for (int i = 0; i < cards.length; i++) {
            Character currentCard = cards[i];
            int count = 0;
            if (cardCountMap.containsKey(currentCard)) {
                count = cardCountMap.get(currentCard);
            }
            cardCountMap.put(currentCard, count+1);
        }

        // System.out.println("Map for " + this.cardsAsString + ":");
        // cardCountMap.forEach((key, value) -> System.out.println("\t" + key + " " + value));

        return cardCountMap;
    }

    private long getHandTypeStrength() {
        if (this.handType.equals(CamelCardHand.TYPE_FIVE_OF_A_KIND)) {
            return Long.parseLong("70000000000");
        }
        if (this.handType.equals(CamelCardHand.TYPE_FOUR_OF_A_KIND)) {
            return Long.parseLong("60000000000");
        }
        if (this.handType.equals(CamelCardHand.TYPE_FULL_HOUSE)) {
            return Long.parseLong("50000000000");
        }
        if (this.handType.equals(CamelCardHand.TYPE_THREE_OF_A_KIND)) {
            return Long.parseLong("40000000000");
        }
        if (this.handType.equals(CamelCardHand.TYPE_TWO_PAIR)) {
            return Long.parseLong("30000000000");
        }
        if (this.handType.equals(CamelCardHand.TYPE_ONE_PAIR)) {
            return Long.parseLong("20000000000");
        }
        return Long.parseLong("10000000000");
    }

    private int calculateCardsStrength() {
        char[] cards = this.cardsAsString.toCharArray();
        int multiplier = cards.length - 1;
        int totalCardStrength = 0;

        // System.out.println(this.cardsAsString + " strength of cards:");

        for (int i = 0; i < cards.length; i++) {
            int cardStrength = this.getIndividualCardStrength(cards[i]);
            for (int j = 0; j < multiplier; j++) {
                cardStrength *= 100;
            }

            // System.out.println("\t" + cards[i] + ": "+cardStrength);

            totalCardStrength += cardStrength;
            multiplier --;
        }
        return totalCardStrength;
    }

    /*
     * Possible values: `A`, `K`, `Q`, `J`, `T`, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`
     */
    private int getIndividualCardStrength(char cardLabel) {
        switch (cardLabel) {
            case 'A':
                return 14;
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                return this.getJokerCardStrength();
            case 'T':
                return 10;
            default:
                return Character.getNumericValue(cardLabel); // should be a number, just return the value as int
        }
    }

    protected int getJokerCardStrength() {
        return 11;
    }
}
