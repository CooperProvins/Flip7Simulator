import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        -1 = Freeze
        -2 = Second Chance
        -3 = Flip Three
        -4 = +2
        -5 = +4
        -6 = +6
        -7 = +8
        -8 = +10
        -9 = x2
        */
        List<Integer> deck = new ArrayList<>(List.of(
                -9,
                -8,
                -7,
                -6,
                -5,
                -4,
                -3,-3,-3,
                -2,-2,-2,
                -1,-1,-1,
                0,
                1,
                2, 2,
                3, 3, 3,
                4, 4, 4, 4,
                5, 5, 5, 5, 5,
                6, 6, 6, 6, 6, 6,
                7, 7, 7, 7, 7, 7, 7,
                8, 8, 8, 8, 8, 8, 8, 8,
                9, 9, 9, 9, 9, 9, 9, 9, 9,
                10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12
        ));
        List<Integer> cards = new ArrayList<>(List.of(10,6));

        removeElements(cards,deck);

        int badCards = 0;
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i) >= 0){
                badCards += countElements(deck, cards.get(i));

            }
        }

        double badCardChance = (double)(badCards)/deck.size();
        double badCardPercentage = Math.round(badCardChance*100*100)/100.0;

        int pointsInHand = 0;
        for (int i = 0; i < cards.size(); i++){
            switch (cards.get(i)){
                case -4:
                    pointsInHand += 2;
                    break;
                case -5:
                    pointsInHand += 4;
                    break;
                case -6:
                    pointsInHand += 6;
                    break;
                case -7:
                    pointsInHand += 8;
                    break;
                case -8:
                    pointsInHand += 10;
                    break;
            }
            if (cards.get(i)>=0){
                pointsInHand += cards.get(i);
            }
        }
        if (countElements(cards,-9)==1){
            pointsInHand *= 2;
        }

        int specialCards = 0;
        specialCards += countElements(deck,-3);
        specialCards += countElements(deck,-2);
        specialCards += countElements(deck,-1);

        double specialCardChance = (double)(specialCards)/deck.size();
        double specialCardPercentage = Math.round(specialCardChance*100*100)/100.0;

        int pointsInDeck = 0;
        for (int i = 0; i <= 12; i++){
            if (countElements(cards,i) == 0) {
                pointsInDeck += countElements(deck, i) * i;
            }
        }
        for (int i = 0; i < deck.size(); i++) {
            switch (deck.get(i)) {
                case -4:
                    pointsInDeck += 2;
                    break;
                case -5:
                    pointsInDeck += 4;
                    break;
                case -6:
                    pointsInDeck += 6;
                    break;
                case -7:
                    pointsInDeck += 8;
                    break;
                case -8:
                    pointsInDeck += 10;
                    break;
                case -9:
                    pointsInDeck += pointsInHand;
                    break;
            }
        }

        int goodCards = deck.size()-badCards-specialCards;

        double averagePointReturn = (double)(pointsInDeck)/goodCards;
        double roundedAveragePointReturn = Math.round(averagePointReturn*100)/100;

        System.out.println("There is a " + badCardPercentage + "% chance you pull a bad card");
        System.out.println("There is a " + specialCardPercentage + "% chance you pull a special card");
        System.out.println("The average point value of a successful returned card is " + roundedAveragePointReturn + ".");
    }

    public static void removeElements(List<Integer> cards, List<Integer> deck){
        for (int i = 0; i < cards.size(); i++){
            deck.remove(Integer.valueOf(cards.get(i)));
            //System.out.println("\tRemoved " + cards.get(i) + " from deck, deck size: " + (deck.size()+1) + " --> " + deck.size());
        }
    }
    public static int countElements(List<Integer> list, int target){
        int count = 0;
        for (int j = 0; j < list.size(); j++) {
            if (Integer.valueOf(list.get(j)) == target) {
                //System.out.println("\t" + target + " found, " + count + " --> " + (count+1));
                count++;
            }
        }
        return count;
    }
}