import java.util.ArrayList;
import java.util.List;

public class Player {
    // initialize a public static list of players
    public static List<Player> Players = new ArrayList<>();
    // total playerCount, used for assigning an accurate index to a player instance
    public static int playerCount = 0;

    // initialize set of instance variables, self-described by their name
    private String name;
    private List<Integer> cards;
    private int points;
    // boolean variables to hold bust and hold status of player
    private boolean bust;
    private boolean hold;
    // what number the player was created as (starting at 0), also used as index for player in Players list
    private int index;

    /**
     * Constructor for name and startingCards
     * @param name name assigned to new player
     * @param startingCards card integer list for player to start with
     */
    public Player(String name, List<Integer> startingCards){
        // initialize parameters to instance variables
        this.name = name;
        this.cards = startingCards;
        this.bust = false;
        this.hold = false;
        this.index = playerCount;
        playerCount++;
        // add this new player to public static player list Players
        Players.add(this);
    }
    /**
     * Constructor for name
     * @param name name assigned to new player
     */
    public Player(String name){
        // initialize parameters to instance variables
        this.name = name;
        this.bust = false;
        this.hold = false;
        // set index to current player count then increase it
        this.index = playerCount;
        playerCount++;
        // initialize cards to an empty list
        this.cards = new ArrayList<>(List.of());
        // add this new player to public static player list Players
        Players.add(this);
    }

    /**
     * @return String representation of the player object
     */
    @Override
    public String toString() {
        // format instance variables into a string and return it
        return "Player{name='" + name + "', cards=" + cards + "}";
    }

    /**
     * @return String representation of the cards the player has
     */
    public String toStringCards() {
        // initialize temporary string
        String cardString = "";
        // run through cards
        for (int i = 0; i < cards.size(); i++){
            // if more than the first loop
            // used so that the commas in between cards are as expected
            if (i > 0) {
                // add a comma and space
                cardString += ", ";
            }
            // switch case with current card
            switch (cards.get(i)){
                // add string representation of int card to cardString
                case -9:
                    cardString += "x2";
                    break;
                case -8:
                    cardString += "+10";
                    break;
                case -7:
                    cardString += "+8";
                    break;
                case -6:
                    cardString += "+6";
                    break;
                case -5:
                    cardString += "+4";
                    break;
                case -4:
                    cardString += "+2";
                    break;
                case -3:
                    cardString += "Flip Three";
                    break;
                case -2:
                    cardString += "Second Chance";
                    break;
                case -1:
                    cardString += "Freeze";
                    break;
                    // if card is not negative (doesn't require formatting)
                default:
                    // add card value
                    cardString += cards.get(i);
                    break;
            }
        }
        // return temporary variable
        return cardString;
    }

    /**
     * @return name of player instance
     */
    public String getName() {
        return name;
    }

    /**
     * @param card card to add to list of cards player owns
     */
    public void addCard(int card) {
        // add parameter card to end of card list
        cards.add(card);
        // sort cards based on number
        cards.sort(null);
        // recalculate points and initialize points to it
        points = this.calculatePoints();
    }

    /**
     * @return points of player instance
     */
    public int getPoints(){
        // recalculate points
        points = this.calculatePoints();
        // return points to method
        return points;
    }

    /**
     * @return list of cards represented by integers
     */
    public List<Integer> getCards(){
        return cards;
    }

    /**
     * @return boolean state of whether the player has busted or not
     */
    public boolean isBust() {
        return bust;
    }

    /**
     * @return boolean state of whether the player has held or not
     */
    public boolean isHold(){
        return hold;
    }

    /**
     * @return whether all the players are folded (busted or held)
     */
    public static boolean allFold(){
        // run through all players
        for (int i = 0; i < Players.size(); i++){
            // if current player is active
            if (!(Players.get(i).bust || Players.get(i).hold)){
                // return false
                return false;
            }
        }
        // if no players were found non-busted/non-held,
        return true;
    }

    /**
     * @return points in hand of player
     */
    private int calculatePoints(){
        // set temporary return variable
        int pointsInHand = 0;
        // run through list of cards
        for (int i = 0; i < cards.size(); i++) {
            // start switch case with the current card of the deck
            switch (cards.get(i)) {
                // add value of each card to pointsInDeck int
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
            // if card is a non-negative number card,
            if (cards.get(i) >= 0) {
                // add that cards value
                pointsInHand += cards.get(i);
            }
        }
        // if a card is -9 (x2)
        if (Main.countElements(cards, -9) == 1) {
            // double the instance variable
            pointsInHand *= 2;
        }
        return pointsInHand;
    }

    /**
     * busts the player and clears points
     */
    public void bust(){
        bust = true;
        points = 0;
    }

    /**
     * holds the player
     */
    public void hold(){
        hold = true;
    }
}
