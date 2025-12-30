import java.util.ArrayList;
import java.util.List;

public class Player {
    public static List<Player> Players = new ArrayList<>();

    private String name;
    private List<Integer> cards;
    private boolean bust;
    private int points;
    private boolean hold;

    public Player(String name, List<Integer> startingCards){
        this.name = name;
        this.cards = startingCards;
        Players.add(this);
        this.bust = false;
        this.hold = false;
    }
    @Override
    public String toString() {
        return "Player{name='" + name + "', cards=" + cards + "}";
    }
    public String toStringCards() {
        String cardString = "";
        for (int i = 0; i < this.cards.size()-1; i++){
            cardString += this.cards.get(i);
            cardString += ", ";
        }
        cardString += this.cards.get(this.cards.size()-1);
        return cardString;
    }
    public String getName() {
        return name;
    }
    public void addCard(int card) {
        this.cards.add(card);
        this.cards.sort(null);
        this.calculatePoints();
    }
    public int getPoints(){
        return points;
    }
    public List<Integer> getCards(){
        return cards;
    }

    public boolean isBust() {
        return bust;
    }
    public boolean isHold(){
        return hold;
    }
    public static boolean allFold(){
        boolean allFold = false;
        for (int i = 0; i < Players.size(); i++){
            if (Players.get(i).bust){
                allFold = true;
            }
            if (Players.get(i).hold){
                allFold = true;
            }
        }
        return allFold;
    }
    private int calculatePoints(){
        int pointsInHand = 0;
        for (int i = 0; i < cards.size(); i++) {
            switch (cards.get(i)) {
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
            if (cards.get(i) >= 0) {
                pointsInHand += cards.get(i);
            }
        }
        if (Main.countElements(cards, -9) == 1) {
            pointsInHand *= 2;
        }
        return pointsInHand;
    }
    public void bust(){
        this.bust = true;
        points = 0;
    }
    public void hold(){
        this.hold = true;
    }
}
