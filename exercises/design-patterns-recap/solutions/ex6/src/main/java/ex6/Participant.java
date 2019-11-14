package ex6;

public interface Participant {

    public void add(Participant participant);

    public int getCollectedCoins();

    public int getNumberOfPlayers();

    public void setCollectedCoins(int collectedCoins);

    public void getStatistics();
}
