import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Player me = new Player("Cooper", new ArrayList<>(List.of()));
        Player player2 = new Player("Evan", new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,11,12)));

        while (!Player.allFold()){
            for (int i = 0; i < Player.Players.size(); i++){
                Player currentPlayer = Player.Players.get(i);
                if (!(currentPlayer.isBust()||currentPlayer.isHold())){
                    System.out.println("\n"+currentPlayer.getName() + "'s turn:");
                    System.out.print("Would you like to continue playing? ");
                    boolean continuePlaying = input.nextBoolean();
                    if (!continuePlaying){
                        currentPlayer.hold();
                    }
                    else {
                        System.out.print("What card did you get? ");
                        int newCard = input.nextInt();
                        if (countElements(currentPlayer.getCards(), newCard) == 0) {
                            currentPlayer.addCard(newCard);
                            System.out.println("Your Cards: " + currentPlayer.toStringCards());
                            System.out.println("Your Points: " + currentPlayer.getPoints());
                        } else {
                            System.out.println("You already have a " + newCard + "! You busted.");
                            currentPlayer.bust();
                        }
                    }
                }
            }
        }
    }
    public static void printStats(List<Player> players, int currentPlayer) {
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
                -3, -3, -3,
                -2, -2, -2,
                -1, -1, -1,
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
        List<Integer> cards = players.get(currentPlayer).getCards();

        for (int i = 0; i < players.size();i++){
            removeElements(players.get(i).getCards(),deck);
        }

        int badCards = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) >= 0) {
                badCards += countElements(deck, cards.get(i));

            }
        }

        double badCardChance = (double) (badCards) / deck.size();
        double badCardPercentage = Math.round(badCardChance * 100 * 100) / 100.0;

        int pointsInHand = players.get(currentPlayer).getPoints();

        int specialCards = 0;
        specialCards += countElements(deck, -3);
        specialCards += countElements(deck, -2);
        specialCards += countElements(deck, -1);

        double specialCardChance = (double) (specialCards) / deck.size();
        double specialCardPercentage = Math.round(specialCardChance * 100 * 100) / 100.0;

        int pointsInDeck = 0;
        for (int i = 0; i <= 12; i++) {
            if (countElements(cards, i) == 0) {
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

        double goodCardChance = (double) (goodCards) / deck.size();
        double goodCardPercentage = Math.round(goodCardChance * 100 * 100) / 100.0;

        double averagePointReturn = (double)(pointsInDeck)/goodCards;
        double roundedAveragePointReturn = Math.round(averagePointReturn*100)/100;

        System.out.println("There is a " + badCardPercentage + "% chance you pull a bad card");
        System.out.println("There is a " + specialCardPercentage + "% chance you pull a special card");
        System.out.println("There is a " + goodCardPercentage + "% chance you pull a good number card");
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