package ex6;

public class Player {
    private String name;
    private int collectedCoins;

    public Player(String name) {
        this.name = name;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public void getStatistics() {
        System.out.print(String.format("Player %s has collected %d coins", name, collectedCoins));
    }
}
