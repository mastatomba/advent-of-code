package nl.schoutens.adventofcode23.day7;

import java.util.Collection;
import java.util.Map;

public class JokerRuleCamelCardHand extends CamelCardHand {
    public JokerRuleCamelCardHand(
        String cardsAsString,
        int bidAmount
    ) {
        super(cardsAsString, bidAmount);
    }

    protected void determineHandType(Map<Character,Integer> cardCountMap) {
        if (cardCountMap.containsKey('J')) {
            int jokerCount = cardCountMap.remove('J');

            Collection<Integer> cardCounts = cardCountMap.values();
            if (jokerCount == 5 || jokerCount == 4) {
                this.handType = CamelCardHand.TYPE_FIVE_OF_A_KIND;
            } else if (jokerCount == 3) {
                if (cardCounts.contains(2)) {
                    this.handType = CamelCardHand.TYPE_FIVE_OF_A_KIND;
                } else {
                    this.handType = CamelCardHand.TYPE_FOUR_OF_A_KIND;
                }
            } else if (jokerCount == 2) {
                if (cardCounts.contains(3)) {
                    this.handType = CamelCardHand.TYPE_FIVE_OF_A_KIND;
                } else if (cardCounts.contains(2)) {
                    this.handType = CamelCardHand.TYPE_FOUR_OF_A_KIND;
                } else {
                    this.handType = CamelCardHand.TYPE_THREE_OF_A_KIND;
                }
            } else {// jokerCount=1
                if (cardCounts.contains(4)) {
                    this.handType = CamelCardHand.TYPE_FIVE_OF_A_KIND;
                } else if (cardCounts.contains(3)) {
                    this.handType = CamelCardHand.TYPE_FOUR_OF_A_KIND;
                } else if (cardCounts.contains(2)) {
                    int pairs = 0;
                    for (Integer cardCount: cardCounts) {
                        if (cardCount == 2) {
                            pairs++;
                        }
                    }
                    if (pairs == 2) {
                        this.handType = CamelCardHand.TYPE_FULL_HOUSE;
                    } else {
                        this.handType = CamelCardHand.TYPE_THREE_OF_A_KIND;
                    }
                } else {
                    this.handType = CamelCardHand.TYPE_ONE_PAIR;
                }
            }
        } else {
            super.determineHandType(cardCountMap);
        }
    }

    @Override
    protected int getJokerCardStrength() {
        return 1; // joker is even lower than 2 with joker rule
    }
}
