package ex6;

public class Player implements Participant {
    private String name;
    private int collectedCoins;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void add(Participant participant) {
        throw new UnsupportedOperationException("Cannot add participant to player.");
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    @Override
    public int getNumberOfPlayers() {
        return 1;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public void getStatistics() {
        System.out.print(String.format("Player %s has collected %d coins", name, collectedCoins));
    }
}
