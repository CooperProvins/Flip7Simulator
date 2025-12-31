import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // initialize Scanner
        Scanner input = new Scanner(System.in);

        // initialize players, also stored in public static list of players in Player class
        Player me = new Player("Cooper");
        Player player2 = new Player("Evan", new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,11,12)));

        // see Player class for allFold implementation and documentation
        while (!Player.allFold()){
            // iterate through players through public static list of players in Player class
            for (int i = 0; i < Player.Players.size(); i++){
                // initialize temporary currentPlayer variable to make calling currentPlayer easy
                Player currentPlayer = Player.Players.get(i);
                // if player has not busted or held, continue. Otherwise, don't even start the turn
                if (!(currentPlayer.isBust()||currentPlayer.isHold())){
                    // print out the player whose turn it is
                    System.out.println("\n"+currentPlayer.getName() + "'s turn:");
                    // ask for boolean input for continue playing
                    System.out.print("Would you like to hit? ");
                    // initialize it to a temporary boolean variable
                    boolean continuePlaying = input.nextBoolean();
                    // if player does not want to continue playing,
                    if (!continuePlaying){
                        // hold
                        // this will make the player's turn not trigger on the next loop
                        currentPlayer.hold();
                    }
                    // if player wants to continue playing, ask for card
                    else {
                        // asking player what card they got
                        System.out.print("What card did you get? ");
                        // initializing int variable to card input
                        int newCard = input.nextInt();
                        // if player does not own the current card,
                        if (countElements(currentPlayer.getCards(), newCard) == 0) {
                            // add card to their hand
                            currentPlayer.addCard(newCard);
                            // print out formatted card list
                            System.out.println("Your Cards: " + currentPlayer.toStringCards());
                            // print out total points
                            System.out.println("Your Points: " + currentPlayer.getPoints());
                        }
                        // if player owns the new card,
                        else {
                            // notify player
                            System.out.println("You already have a " + newCard + "! You busted.");
                            // make player bust
                            currentPlayer.bust();
                        }
                    }
                }
            }
        }
    }

    /**
     * Gives the stats of the next possible card pull by current player from list players
     * @param players List of players playing, likely given by public static List of players Player.Players
     * @param currentPlayer integer giving index of current player playing
     */
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
        // initialize deck list of integers
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

        // initializes temporary cards variable grabbing the cards from the player given at the index currentPlayer in Player list players
        List<Integer> cards = players.get(currentPlayer).getCards();

        // runs through the list of players,
        for (int i = 0; i < players.size();i++){
            // and removes the cards from the starting deck
            removeElements(players.get(i).getCards(),deck);
        }

        // initialize badCards int to store number of bad cards in deck
        int badCards = 0;
        // runs through list of cards
        for (int i = 0; i < cards.size(); i++) {
            // if the card is a number card,
            if (cards.get(i) >= 0) {
                // add the number of that card found in the deck to the badCards variable
                badCards += countElements(deck, cards.get(i));
            }
        }

        // calculate the chance of pulling those bad cards from the deck
        double badCardChance = (double) (badCards) / deck.size();
        // format that into a rounded percentage
        double badCardPercentage = Math.round(badCardChance * 100 * 100) / 100.0;

        // initialize local variable to store the points in the players hand
        // used in addition with the x2 card in counting possible point returns
        int pointsInHand = players.get(currentPlayer).getPoints();

        // initialize specialCards int to store number of special cards in deck
        int specialCards = 0;
        // count number of Flip Three cards in deck and add it to the specialCards int
        specialCards += countElements(deck, -3);
        // count number of Second Chance in deck and add it to the specialCards int
        specialCards += countElements(deck, -2);
        // count number of Freeze cards in deck and add it to the specialCards int
        specialCards += countElements(deck, -1);

        // calculate the chance of pulling those special cards from the deck
        double specialCardChance = (double) (specialCards) / deck.size();
        // format that into a rounded percentage
        double specialCardPercentage = Math.round(specialCardChance * 100 * 100) / 100.0;

        // initialize pointsInDeck int to store total number of points in the deck
        int pointsInDeck = 0;
        // run through number cards
        for (int i = 0; i <= 12; i++) {
            // if that number card does not exist in hand,
            if (countElements(cards, i) == 0) {
                // add the total number of points those number cards hold to pointsInDeck int
                pointsInDeck += countElements(deck, i) * i;
            }
        }

        // run through the cards in the deck,
        for (int i = 0; i < deck.size(); i++) {
            // start switch case with the current card of the deck
            switch (deck.get(i)) {
                // add value of each card to pointsInDeck int
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
                 // if card is a x2, add the pointsInHand value to the pointsInDeck
                // because with a x2, you gain the number of points in your hand
                case -9:
                    pointsInDeck += pointsInHand;
                    break;
            }
        }

        // initialize goodCards int to store number of good cards in deck
        int goodCards = deck.size()-badCards-specialCards;

        // calculate the chance of pulling those special cards from the deck
        double goodCardChance = (double) (goodCards) / deck.size();
        // format that into a rounded percentage
        double goodCardPercentage = Math.round(goodCardChance * 100 * 100) / 100.0;

        // calculate the averagePointReturn based on the total number of pointsInDeck divided by the good cards in the deck
        double averagePointReturn = (double)(pointsInDeck)/goodCards;
        // format that into a rounded number
        double roundedAveragePointReturn = Math.round(averagePointReturn * 100)/ 100.0;

        // notify players of the important percentages and possible point return
        System.out.println("There is a " + badCardPercentage + "% chance you pull a bad card");
        System.out.println("There is a " + specialCardPercentage + "% chance you pull a special card");
        System.out.println("There is a " + goodCardPercentage + "% chance you pull a good number card");
        System.out.println("The average point value of a successful returned card is " + roundedAveragePointReturn + ".");
    }

    /**
     * removes all the elements from cards from the deck
     * @param cards List of integer cards held in hand
     * @param deck List of integer cards held in deck
     */
    public static void removeElements(List<Integer> cards, List<Integer> deck){
        // runs through the cards in cards
        for (int i = 0; i < cards.size(); i++){
            // removes the card being run through from the total deck
            deck.remove(Integer.valueOf(cards.get(i)));
        }
    }

    /**
     * @param list list of integers
     * @param target integer to find within the list
     * @return number of targets in the list
     */
    public static int countElements(List<Integer> list, int target){
        // initializes local int count
        int count = 0;
        // runs through the list of integers
        for (int i = 0; i < list.size(); i++) {
            // if the current index of list is equal to the target
            if (Integer.valueOf(list.get(i)) == target) {
                // increase count value
                count++;
            }
        }
        // return count variable
        return count;
    }
}